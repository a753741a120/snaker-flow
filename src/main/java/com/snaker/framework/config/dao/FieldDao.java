package com.snaker.framework.config.dao;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snaker.framework.config.entity.Field;
import org.apache.ibatis.annotations.Mapper;

/**
 * 表单字段持久化类
 * @author yuqs
 * @since 0.1
 */
@Mapper
public interface FieldDao extends BaseMapper<Field> {
}
