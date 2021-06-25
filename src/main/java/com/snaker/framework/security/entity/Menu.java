package com.snaker.framework.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单实体类，继承抽象安全实体类
 * @author yuqs
 * @since 0.1
 */
@ApiModel("菜单实体类")
@Data
@TableName("SEC_MENU")
public class Menu extends SecurityEntity {
	/**
	 *
	 */
	private static final long serialVersionUID = 3488405380107404492L;
	@ApiModelProperty("主键")
	private Long id;
	@ApiModelProperty("菜单资源的根菜单标识为0")
	public static final Long ROOT_MENU = 0L;
	@ApiModelProperty("菜单名称")
	private String name;
	@ApiModelProperty("菜单描述")
	private String description;
	@ApiModelProperty("排序字段")
	private Integer orderby;
	@ApiModelProperty("上级菜单")
	private Menu parentMenu;
	/**
	 * 子菜单列表（多对多关联）
	 */
	@ApiModelProperty("子菜单列表")
	private List<Menu> subMenus = new ArrayList<Menu>();

	public Menu(Long id) {
		this.id = id;
	}
}
