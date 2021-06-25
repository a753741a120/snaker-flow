package com.snaker.framework.security.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snaker.framework.security.entity.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 资源持久化类
 * @author yuqs
 * @since 0.1
 */
@Mapper
public interface ResourceDao extends BaseMapper<Resource> {

    @Select(value = {
            "select re.id, re.name, re.source, re.menu from sec_user u " +
            " left outer join sec_role_user ru on u.id=ru.user_id " +
                    " left outer join sec_role r on ru.role_id=r.id " +
                    " left outer join sec_role_authority ra on r.id = ra.role_id " +
                    " left outer join sec_authority a on ra.authority_id = a.id " +
                    " left outer join sec_authority_resource ar on a.id = ar.authority_id " +
                    " left outer join sec_resource re on ar.resource_id = re.id " +
                    " where u.id=#{userId} and re.menu is not null " +
                    " union all " +
                    " select re.id, re.name, re.source, re.menu from sec_resource re " +
                    " where re.menu is not null and not exists (select ar.authority_id from sec_authority_resource ar where ar.resource_id = re.id)"
    })
    List<Resource> getAuthorizedResource(@Param("userId") Long userId);
}
