package com.snaker.framework.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限实体类，继承抽象安全实体类
 * @author yuqs
 * @since 0.1
 */
@ApiModel("权限实体类")
@Data
@TableName("SEC_AUTHORITY")
public class Authority extends SecurityEntity
{

	/**
	 *
	 */
	private static final long serialVersionUID = -8349705525996917628L;
	/**
	 * 主键
	 */
	@ApiModelProperty("主键")
	private Long id;
	/**
	 * 权限名称
	 */
    @ApiModelProperty("权限名称")
	private String name;
	/**
	 * 权限描述
	 */
    @ApiModelProperty("权限描述")
	private String description;
	/**
	 * 是否选择，该字段不需要持久化，仅仅是方便页面控制选择状态
	 */
    @ApiModelProperty("是否选择 1选择、0未选择")
	private Integer selected;
	/**
	 * 权限管辖的资源列表（多对多关联）
	 */
    @ApiModelProperty("权限管辖的资源列表")
	private List<Resource> resources = new ArrayList<Resource>();
	/**
	 * 权限所属的角色列表（多对多关联）
	 */
    @ApiModelProperty("权限所属的角色列表")
	private List<Role> roles = new ArrayList<Role>();
	/**
	 * 权限包含的用户列表（多对多关联）这里表示：用户既可以指定角色，也可指定单独的权限
	 */
    @ApiModelProperty("权限包含的用户列表")
	private List<User> users = new ArrayList<User>();

    public Authority() {}

	public Authority(Long id) {
		this.id = id;
	}

}
