package com.snaker.framework.flow.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snaker.framework.flow.dao.HistoryOrderDao;
import com.snaker.framework.flow.entity.HistoryOrder;
import com.snaker.framework.flow.service.HistoryOrderService;
import org.springframework.stereotype.Service;

/**
 * @基本功能:
 * @program:demo
 * @author:pm
 * @create:2021-06-23 16:19:01
 **/
@Service
public class HistoryOrderServiceImpl extends ServiceImpl<HistoryOrderDao, HistoryOrder> implements HistoryOrderService {
}