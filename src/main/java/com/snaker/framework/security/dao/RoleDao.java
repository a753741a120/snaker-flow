package com.snaker.framework.security.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snaker.framework.security.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色持久化类
 * @author yuqs
 * @since 0.1
 */
@Mapper
public interface RoleDao extends BaseMapper<Role> {

}
