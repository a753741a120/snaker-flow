package com.snaker.framework.flow.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snaker.framework.flow.entity.HistoryOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * @基本功能:
 * @program:demo
 * @author:pm
 * @create:2021-06-23 16:17:52
 **/
@Mapper
public interface HistoryOrderDao extends BaseMapper<HistoryOrder> {
}