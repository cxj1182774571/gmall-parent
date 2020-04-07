package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.product.BaseTrademark;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * @author Chengxj
 * @create 2020-03-17 23:07
 */
public interface BaseTrademarkService extends IService<BaseTrademark> {
    /** 品牌分页查询 */
    IPage<BaseTrademark> selectPage(Page<BaseTrademark> pageParam);

    List<BaseTrademark> getTrademarkList();
}
