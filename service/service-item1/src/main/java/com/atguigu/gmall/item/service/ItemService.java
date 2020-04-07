package com.atguigu.gmall.item.service;

import java.util.Map;

/**
 * @author Chengxj
 * @create 2020-03-28 23:24
 */
public interface ItemService {

    /**
     * 获取sku详情信息
     * @param skuId
     * @return
     */
    Map<String, Object> getBySkuId(Long skuId);
}
