package com.snaker.framework.security.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 安全实体抽象类
 * @author yuqs
 * @since 0.1
 */
@Data
public abstract class SecurityEntity implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -5288872906807628038L;
	//主键标识ID
	protected Long id;
}
