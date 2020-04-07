package com.atguigu.gmall.product.mapper;

import com.atguigu.gmall.model.product.SkuAttrValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Chengxj
 * @create 2020-03-24 18:04
 */
@Mapper
public interface SkuAttrValueMapper extends BaseMapper<SkuAttrValue> {
    int insert(SkuAttrValue skuAttrValue);
}
