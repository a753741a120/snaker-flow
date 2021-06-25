package com.snaker.framework.security.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snaker.framework.security.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 菜单持久化类
 * @author yuqs
 * @since 0.1
 */
@Mapper
public interface MenuDao extends BaseMapper<Menu> {


    @Select(value = {
            "select * from (\" +\n" +
                    " select m.id,m.name,m.parent_menu,m.description,m.orderby from sec_menu m \" +\n" +//获取Sec_Menu表中定义且未关联资源表Sec_Resource的所有菜单列表
                    " where not exists (select re.id from sec_resource re where re.menu = m.id)\" +\n" +
                    " union all \" +\n" +
                    " select m.id,m.name,m.parent_menu,re.source as description,m.orderby from sec_resource re \" +\n" +//获取Sec_Resource表中已关联且未设置权限的菜单列表
                    " left outer join sec_menu m on re.menu = m.id  \" +\n" +
                    " where re.menu is not null and not exists (select ar.authority_id from sec_authority_resource ar where ar.resource_id = re.id)\" +\n" +
                    " union all " +
                    " select m.id,m.name,m.parent_menu,re.source as description,m.orderby from sec_user u \" +\n" +//获取Sec_Resource表中已关联且设置权限，并根据当前登录账号拥有相应权限的菜单列表\n" +
                    " left outer join sec_role_user ru on u.id=ru.user_id" +
                    " left outer join sec_role r on ru.role_id=r.id " +
                    " left outer join sec_role_authority ra on r.id = ra.role_id " +
                    " left outer join sec_authority a on ra.authority_id = a.id " +
                    " left outer join sec_authority_resource ar on a.id = ar.authority_id " +
                    " left outer join sec_resource re on ar.resource_id = re.id " +
                    " left outer join sec_menu m on re.menu = m.id " +
                    " where u.id=#{userId} and re.menu is not null " +
                    ") tbl order by orderby"
    })
    List<Menu> getAllowedAccessMenu(@Param("userId") Long userId);
}
