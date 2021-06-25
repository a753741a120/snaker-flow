package com.snaker.framework.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户实体类，继承抽象安全实体类
 * @author yuqs
 * @since 0.1
 */
@Data
@TableName("SEC_USER")
public class User extends SecurityEntity
{
	private static final long serialVersionUID = 7446802057673100315L;
	//系统管理员账号类型
	public static final Integer TYPE_ADMIN = 0;
	//普通用户账号类型
	public static final Integer TYPE_GENERAL = 1;
	//登录用户名称
	private String username;
	//密码
	private String password;
	//明文密码
	private String plainPassword;
	//salt
	private String salt;
	//用户姓名
	private String fullname;
	//类型
	private Integer type;
	//电子邮箱
	private String email;
	//联系地址
	private String address;
	//年龄
	private Integer age;
	//性别
	private String sex;
	//是否可用
	private String enabled;
	//所属部门
	private Org org;
	//角色列表（多对多关联）
	private List<Role> roles = new ArrayList<Role>();
	//权限列表（多对多关联）
	private List<Authority> authorities = new ArrayList<Authority>();
}
