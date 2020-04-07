package com.atguigu.gmall.product.service;


import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.model.product.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jdk.internal.org.objectweb.asm.tree.IincInsnNode;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author Chengxj
 * @create 2020-03-14 15:22
 */

public interface ManageService {
    /**
     * 查询所有的一级分类
     */
    List<BaseCategory1> getCategory1();
    /**
     * 查询所有的二级分类
     */
    List<BaseCategory2> getCategory2(Long category1Id);
    /**
     * 查询所有的三级分类
     */
    List<BaseCategory3> getCategory3(Long category2Id);
    /**
     * 查询三级分类下面的平台属性，传：category1Id，category2Id，category3Id；
     * 取出对应一级分类、二级分类与三级分类对应的平台属性
     */
    List<BaseAttrInfo> getAttrInfoList(Long category1Id,Long category2Id,Long category3Id);

    void saveAttrInfo(BaseAttrInfo baseAttrInfo);

    BaseAttrInfo getAttrInfo(Long attrId);

    /**
     * spu分页查询
     * @param pageParam
     * @param spuInfo
     * @return
     */
    IPage<SpuInfo> selectPage(Page<SpuInfo> pageParam, SpuInfo spuInfo);

    //获取所有的销售属性列表
    List<BaseSaleAttr> getBaseSaleAttrList();

    void saveSpuInfo(SpuInfo spuInfo);

    /**
     * 查询所有spu_image 图片  通过spuId
     */
    List<SpuImage> getSpuImageList(Long spuId);


    List<SpuSaleAttr> getSpuSaleAttrList(Long spuId);

    void saveSkuInfo(SkuInfo skuInfo);

    /**
     * SKU分页列表
     * @param pageParam
     * @return
     */
    IPage<SkuInfo> selectPage(Page<SkuInfo> pageParam);
    /**
     * 商品上架
     * @param skuId
     */
    void onSale(Long skuId);

    /**
     * 商品下架
     * @param skuId
     */
    void cancelSale(Long skuId);

    /**
     * 根据商品id 查询skuInfo 数据
     */
    SkuInfo getSkuInfo(Long skuId);

    /**
     * 通过三级分类id查询分类信息
     * @param category3Id
     * @return
     */
    BaseCategoryView getCategoryViewByCategory3Id(Long category3Id);

    /**
     * 获取sku价格
     * @param skuId
     * @return
     */
    BigDecimal getSkuPrice(Long skuId);

    List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(Long skuId,Long spuId);

    Map getSkuValueIdsMap(Long spuId);

    /**
     * 获取全部分类信息
     * @return
     */
    List<JSONObject> getBaseCategoryList();

    /**
     * 通过品牌Id 来查询数据
     * @param tmId
     * @return
     */
    BaseTrademark getTrademarkByTmId(Long tmId);

    /**
     * 通过skuId 集合来查询数据
     * @param skuId
     * @return
     */
    List<BaseAttrInfo> getAttrList(Long skuId);
}


