package com.snaker.framework.security.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snaker.framework.security.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户持久化类
 * @author yuqs
 * @since 0.1
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

    @Select(value = {
            "select a.name from sec_user u " +
                    " left outer join sec_role_user ru on u.id=ru.user_id " +
                    " left outer join sec_role r on ru.role_id=r.id " +
                    " left outer join sec_role_authority ra on r.id = ra.role_id " +
                    " left outer join sec_authority a on ra.authority_id = a.id " +
                    " where u.id=#{userId}"
    })
    List<String> createSQLQuery(@Param("userId") Long userId);


    @Select(value = {
            "select r.name from sec_user u \" +\n" +
                    " left outer join sec_role_user ru on u.id=ru.user_id " +
                    " left outer join sec_role r on ru.role_id=r.id " +
                    " where u.id=#{userId} "
    })
    List<String> getRolesName(@Param("userId") Long userId);
}
