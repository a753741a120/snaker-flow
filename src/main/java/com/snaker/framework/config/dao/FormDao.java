package com.snaker.framework.config.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snaker.framework.config.entity.Form;
import org.apache.ibatis.annotations.Mapper;

/**
 * 表单持久化类
 * @author yuqs
 * @since 0.1
 */
@Mapper
public interface FormDao extends BaseMapper<Form> {
}
