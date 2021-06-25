package com.snaker.framework.config.entity;

import lombok.Data;

import java.io.Serializable;

/**
 *配置实体抽象类
 * @author yuqs
 * @since 0.1
 */
@Data
public abstract class DictionaryEntity implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -5288872906807628038L;
	//主键标识ID
	protected Long id;
}
