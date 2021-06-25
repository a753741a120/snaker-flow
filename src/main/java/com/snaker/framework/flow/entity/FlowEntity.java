/* Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.snaker.framework.flow.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 流程模块实体抽象类
 * @author yuqs
 * @since 0.1
 */
@Data
public abstract class FlowEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2538960674566954856L;
	//主键标识ID
	protected Long id;
	//流程实例ID
	protected String orderId;
	//任务ID
	protected String taskId;
}
