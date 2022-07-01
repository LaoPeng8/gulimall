package org.pjj.gulimall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.pjj.common.constant.WareConstant;
import org.pjj.common.exception.BizCodeEnum;
import org.pjj.common.exception.GulimallException;
import org.pjj.gulimall.ware.entity.PurchaseDetailEntity;
import org.pjj.gulimall.ware.entity.vo.MergeVo;
import org.pjj.gulimall.ware.service.PurchaseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.pjj.common.utils.PageUtils;
import org.pjj.common.utils.Query;

import org.pjj.gulimall.ware.dao.PurchaseDao;
import org.pjj.gulimall.ware.entity.PurchaseEntity;
import org.pjj.gulimall.ware.service.PurchaseService;
import org.springframework.transaction.annotation.Transactional;


@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {

    @Autowired
    private PurchaseDetailService purchaseDetailService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageUnreceive(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>().eq("status", "0").or().eq("status", "1")
        );

        return new PageUtils(page);
    }

    @Override
    public void assignPerson(PurchaseEntity purchaseEntity) {
        UpdateWrapper<PurchaseEntity> purchaseEntityUpdateWrapper = new UpdateWrapper<>();
        purchaseEntityUpdateWrapper.set("assignee_id", purchaseEntity.getAssigneeId());
        purchaseEntityUpdateWrapper.set("assignee_name", purchaseEntity.getAssigneeName());
        purchaseEntityUpdateWrapper.set("phone", purchaseEntity.getPhone());
        purchaseEntityUpdateWrapper.set("status", 1);//更新采购单状态
        purchaseEntityUpdateWrapper.eq("id", purchaseEntity.getId());

        this.update(purchaseEntityUpdateWrapper);
    }

    @Transactional
    @Override
    public void mergePurchase(MergeVo mergeVo) {
        // 判断 只要传入的需要合并的采购需求集合中 有一个采购需求以被合并 则抛出异常
        List<Long> PurchaseDetailIds = mergeVo.getItems();
        Collection<PurchaseDetailEntity> purchaseDetailEntities = purchaseDetailService.listByIds(mergeVo.getItems());
        boolean flag = purchaseDetailEntities.stream()
                .anyMatch((detailEntitie) -> {// anyMatch() 集合中只要有一个元素满足条件即为true
                    // 如果 detailEntitie.getPurchaseId() ！= null 说明 该采购需求已经被分配采购单了
                    if (detailEntitie.getPurchaseId() != null ||
                            // 如果 detailEntitie.getStatus() != CREATE 或 ASSIGNED 说明该采购需求状态处于不能被合并的状态
                            (detailEntitie.getStatus() != WareConstant.PurchaseDetailStatusEnum.CREATED.getCode() ||
                                    detailEntitie.getStatus() != WareConstant.PurchaseDetailStatusEnum.ASSIGNED.getCode())) {
                        return true;
                    } else {
                        return false;
                    }
                });
        if(flag) {
            throw new GulimallException(BizCodeEnum.VAILD_EXCEPTION);
        }

        Long purchaseId = mergeVo.getPurchaseId();
        if(purchaseId == null) {// 如果没有传入 采购单id 则说明需要我们自己新建一个采购单
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.CREATED.getCode());
            purchaseEntity.setCreateTime(new Date());
            purchaseEntity.setUpdateTime(new Date());

            this.save(purchaseEntity);
            purchaseId = purchaseEntity.getId();
        }

        //合并
        List<Long> items = mergeVo.getItems();
        Long finalPurchaseId = purchaseId;
         List<PurchaseDetailEntity> collect = items.stream().map((item) -> {
            PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
            purchaseDetailEntity.setId(item);
            purchaseDetailEntity.setPurchaseId(finalPurchaseId);
            purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.ASSIGNED.getCode());

            return purchaseDetailEntity;
        }).collect(Collectors.toList());
        purchaseDetailService.updateBatchById(collect);



    }

    @Override
    public void received(List<Long> ids) {
        // 1. 确认当前采购单是新建或者已分配状态
        List<PurchaseEntity> purchaseEntityList = ids.stream().map((id) -> {
            PurchaseEntity byId = this.getById(id);
            return byId;
        }).filter((purchaseEntity) -> {//过滤出采购单状态是 新建或已分配 的采购单
            if (purchaseEntity.getStatus() == WareConstant.PurchaseStatusEnum.CREATED.getCode() ||
                    purchaseEntity.getStatus() == WareConstant.PurchaseStatusEnum.ASSIGNED.getCode()) {
                return true;
            } else {
                return false;
            }
        }).map((purchaseEntity) -> {
            // 改变采购单状态
            purchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.RECEIVE.getCode());
            purchaseEntity.setUpdateTime(new Date());
            return purchaseEntity;
        }).collect(Collectors.toList());
        // 2. 改变采购单状态 (保存)
        this.updateBatchById(purchaseEntityList);

        // 3. 改变采购单下的采购需求的状态
        purchaseEntityList.forEach((purchaseEntity) -> {
            List<PurchaseDetailEntity> detailEntities = purchaseDetailService.list(new QueryWrapper<PurchaseDetailEntity>().eq("purchase_id", purchaseEntity.getId()));
            List<PurchaseDetailEntity> collect = detailEntities.stream().map((detailEntity) -> {
                PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
                purchaseDetailEntity.setId(detailEntity.getId());
                purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.BUYING.getCode());
                return purchaseDetailEntity;
            }).collect(Collectors.toList());
            purchaseDetailService.updateBatchById(collect);
        });

    }

}