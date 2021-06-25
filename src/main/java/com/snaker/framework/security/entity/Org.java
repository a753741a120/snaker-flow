package com.snaker.framework.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门实体类，继承抽象安全实体类
 * @author yuqs
 * @since 0.1
 */
@Data
@TableName("SEC_ORG")
public class Org extends SecurityEntity
{
	private static final long serialVersionUID = 7297765946510001885L;
	//主键
	private Long id;
	//根部门ID号默认为0
	public static final Long ROOT_ORG_ID = 0l;
	//上级部门
    private Long parentOrg;
    //部门名称
    private String name;
    //是否激活状态
    private String active;
    //部门全路径
    private String fullname;
    //部门描述
    private String description;
    //部门类型（扩展使用）
    private String type;
    //部门管辖的所有用户列表（一对多关联）
    private List<User> users = new ArrayList<User>();
    //部门管辖的所有下级部门列表（一对多关联）
    private List<Org> orgs = new ArrayList<Org>();

    public Org(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
