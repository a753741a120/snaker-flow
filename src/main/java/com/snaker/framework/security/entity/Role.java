package com.snaker.framework.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色实体类，继承抽象安全实体类
 * @author yuqs
 * @since 0.1
 */
@Data
@TableName("SEC_ROLE")
public class Role extends SecurityEntity
{
	private static final long serialVersionUID = 2041148498753846675L;
	//角色名称
    private String name;
    //角色描述
    private String description;
    //是否选择，该字段不需要持久化，仅仅是方便页面控制选择状态
    private Integer selected;
    //角色拥有的权限列表（多对多关联）
    private List<Authority> authorities = new ArrayList<Authority>();
    //角色所包含的用户列表（多对多关联）
    private List<User> users = new ArrayList<User>();
}
