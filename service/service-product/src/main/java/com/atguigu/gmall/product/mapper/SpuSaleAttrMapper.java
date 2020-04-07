package com.atguigu.gmall.product.mapper;

import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Chengxj
 * @create 2020-03-18 16:01
 */
public interface SpuSaleAttrMapper extends BaseMapper<SpuSaleAttr> {
    //传递多个参数需要添加注解 @PathVariable
    List<SpuSaleAttr> selectSpuSaleAttrList(Long spuId);

    // 根据spuId，skuId 查询销售属性集合  锁定属性  多表查询
    List<SpuSaleAttr> selectSpuSaleAttrListCheckBySku(@Param(value = "skuId") Long skuId,
                                                      @Param(value = "spuId") Long spuId);
}
