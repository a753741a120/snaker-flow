package com.snaker.framework.security.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snaker.framework.security.entity.Org;
import org.apache.ibatis.annotations.Mapper;

/**
 * 部门持久化类
 * @author yuqs
 * @since 0.1
 */
@Mapper
public interface OrgDao extends BaseMapper<Org> {

}
