package com.snaker.framework.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源实体类，继承抽象安全实体类
 * @author yuqs
 * @since 0.1
 */
@Data
@TableName("SEC_RESOURCE")
public class Resource extends SecurityEntity
{
	/**
	 *
	 */
	private static final long serialVersionUID = 5503383469393219319L;
	private Long id;
	//资源名称
	private String name;
	//资源值（此处主要作为url资源，及链接路径）
	private String source;
	//是否选择，该字段不需要持久化，仅仅是方便页面控制选择状态
	private Integer selected;
	//资源所属权限列表（多对多关联）
	private List<Authority> authorities = new ArrayList<Authority>();
	//资源所属菜单
	private Menu menu;

	public Resource(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
