package com.snaker.framework.security.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snaker.framework.security.entity.Authority;
import org.apache.ibatis.annotations.Mapper;

/**
 * 权限持久化类
 * @author yuqs
 * @since 0.1
 */
@Mapper
public interface AuthorityDao extends BaseMapper<Authority> {

}
