package com.atguigu.gmall.product.mapper;

import com.atguigu.gmall.model.product.SkuInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Chengxj
 * @create 2020-03-24 18:03
 */
@Mapper
public interface SkuInfoMapper extends BaseMapper<SkuInfo> {
    int insert(SkuInfo skuInfo);
}
