package org.pjj.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.pjj.common.utils.PageUtils;
import org.pjj.gulimall.ware.entity.PurchaseEntity;
import org.pjj.gulimall.ware.entity.vo.MergeVo;
import org.pjj.gulimall.ware.entity.vo.PurchaseDoneVo;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author PengJiaJun
 * @email 2779824672@qq.com
 * @date 2022-05-07 20:47:36
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageUnreceive(Map<String, Object> params);

    void assignPerson(PurchaseEntity purchaseEntity);

    void mergePurchase(MergeVo mergeVo);

    void received(List<Long> ids);

    void done(PurchaseDoneVo doneVo);
}

