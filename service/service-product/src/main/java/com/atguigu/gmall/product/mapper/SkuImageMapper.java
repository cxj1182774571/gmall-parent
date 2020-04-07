package com.atguigu.gmall.product.mapper;

import com.atguigu.gmall.model.product.SkuImage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Chengxj
 * @create 2020-03-24 18:04
 */
@Mapper
public interface SkuImageMapper extends BaseMapper<SkuImage> {
    int insert(SkuImage skuImage);
}
