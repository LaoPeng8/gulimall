package org.pjj.gulimall.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.pjj.gulimall.product.entity.BrandEntity;
import org.pjj.gulimall.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class GulimallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Test
    void contextLoads() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("红米");
        brandEntity.setLogo("http://");
        brandEntity.setDescript("不太行...");
        brandEntity.setShowStatus(1);
        brandEntity.setFirstLetter("H");
        brandEntity.setSort(1);

        boolean save = brandService.save(brandEntity);
        if(save) {
            System.out.println("insert ok");
        }
    }

    @Test
    void test02() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setBrandId(14L);
        brandEntity.setName("红米22222");

        boolean save = brandService.updateById(brandEntity);
        if(save) {
            System.out.println("update ok");
        }
    }

    @Test
    void test03() {
        QueryWrapper<BrandEntity> brandEntityQueryWrapper = new QueryWrapper<>();
        brandEntityQueryWrapper.eq("sort", 1);//查询 sort 字段的值为 1 的所有记录 where sort = '1';

        List<BrandEntity> list = brandService.list(brandEntityQueryWrapper);
        for (BrandEntity brandEntity : list) {
            System.out.println(brandEntity);
        }
    }

}
