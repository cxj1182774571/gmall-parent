package com.atguigu.gmall.item.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.item.service.ItemService;
import com.atguigu.gmall.list.client.ListFeignClient;
import com.atguigu.gmall.model.product.BaseCategoryView;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.product.client.ProductFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Chengxj
 * @create 2020-03-28 23:25
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ProductFeignClient productFeignClient;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    private ListFeignClient listFeignClient;

    @Override
    public Map<String, Object> getBySkuId(Long skuId) {
        Map<String, Object> result = new HashMap<>();

        //优化代码，异步编排  多线程获取
        // 通过skuId 查询skuInfo
        CompletableFuture<SkuInfo> skuCompletableFuture = CompletableFuture.supplyAsync(() -> {
            SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
            // 保存skuInfo
            result.put("skuInfo", skuInfo);
            return skuInfo;
        }, threadPoolExecutor);
        //更新商品incrHotScore
        CompletableFuture<Void> incrHotScoreCompletableFuture = CompletableFuture.runAsync(() -> {
            listFeignClient.incrHotScore(skuId);
        }, threadPoolExecutor);


        // 销售属性-销售属性值回显并锁定
        CompletableFuture<Void> spuSaleAttrCompletableFuture = skuCompletableFuture.thenAcceptAsync(skuInfo -> {
            List<SpuSaleAttr> spuSaleAttrList = productFeignClient.getSpuSaleAttrListCheckBySku(skuInfo.getId(), skuInfo.getSpuId());

            // 保存数据
            result.put("spuSaleAttrList", spuSaleAttrList);
        }, threadPoolExecutor);

        //根据spuId 查询map 集合属性
        // 销售属性-销售属性值回显并锁定
        CompletableFuture<Void> skuValueIdsMapCompletableFuture = skuCompletableFuture.thenAcceptAsync(skuInfo -> {
            Map skuValueIdsMap = productFeignClient.getSkuValueIdsMap(skuInfo.getSpuId());

            String valuesSkuJson = JSON.toJSONString(skuValueIdsMap);
            // 保存valuesSkuJson
            result.put("valuesSkuJson", valuesSkuJson);
        }, threadPoolExecutor);


        //获取商品最新价格
        CompletableFuture<Void> skuPriceCompletableFuture = CompletableFuture.runAsync(() -> {
            BigDecimal skuPrice = productFeignClient.getSkuPrice(skuId);
            result.put("price", skuPrice);
        }, threadPoolExecutor);


        //获取分类信息
        CompletableFuture<Void> categoryViewCompletableFuture = skuCompletableFuture.thenAcceptAsync(skuInfo -> {
            BaseCategoryView categoryView = productFeignClient.getCategoryView(skuInfo.getCategory3Id());

            //分类信息
            result.put("categoryView", categoryView);
        }, threadPoolExecutor);


        CompletableFuture.allOf(skuCompletableFuture,
                spuSaleAttrCompletableFuture,
                skuValueIdsMapCompletableFuture,
                skuPriceCompletableFuture,
                categoryViewCompletableFuture,
                incrHotScoreCompletableFuture).join();


        //单线程获取
       /* // 通过skuId 查询skuInfo
        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
        // 销售属性-销售属性值回显并锁定
        List<SpuSaleAttr> spuSaleAttrList =  productFeignClient.getSpuSaleAttrListCheckBySku(skuInfo.getId(), skuInfo.getSpuId());

        //根据spuId 查询map 集合属性
        Map skuValueIdsMap =  productFeignClient.getSkuValueIdsMap(skuInfo.getSpuId());

        //获取商品最新价格
        BigDecimal skuPrice = productFeignClient.getSkuPrice(skuId);
        //获取商品分类
        BaseCategoryView categoryView = productFeignClient.getCategoryView(skuInfo.getCategory3Id());
//保存商品分类数据
        result.put("categoryView",categoryView);

        // 保存 json字符串
        String valuesSkuJson = JSON.toJSONString(skuValueIdsMap);
        // 获取价格
        result.put("price",skuPrice);
        // 保存valuesSkuJson
        result.put("valuesSkuJson",valuesSkuJson);
        // 保存数据
        result.put("spuSaleAttrList",spuSaleAttrList);
        // 保存skuInfo
        result.put("skuInfo",skuInfo);*/
        return result;
    }
}
