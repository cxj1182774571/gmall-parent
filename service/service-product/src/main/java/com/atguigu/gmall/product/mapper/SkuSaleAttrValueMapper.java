package com.atguigu.gmall.product.mapper;

import com.atguigu.gmall.model.product.SkuSaleAttrValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author Chengxj
 * @create 2020-03-24 18:04
 */
@Mapper
public interface SkuSaleAttrValueMapper extends BaseMapper<SkuSaleAttrValue> {
    int insert(SkuSaleAttrValue skuSaleAttrValue);

    List<Map> getSaleAttrValuesBySpu(Long spuId);
}
