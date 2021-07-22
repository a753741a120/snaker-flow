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
package com.snaker.framework.flow.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.snaker.framework.config.annotation.MethodLog;
import com.snaker.framework.exception.BizRuntimeException;
import com.snaker.framework.flow.dto.HistoryOrderDTO;
import com.snaker.framework.flow.dto.SurrogateDTO;
import com.snaker.framework.flow.service.SnakerEngineFacets;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.snaker.engine.access.Page;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.Surrogate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 委托授权
 * @author yuqs
 * @since 0.1
 */
@Api(tags = "委托授权接口")
@Slf4j
@RestController
@RequestMapping(value = "/snaker/surrogate")
public class SurrogateController {
	@Autowired
	private SnakerEngineFacets facets;

	/**
	 * 委托授权列表
	 * @param page
	 * @return
	 */
	@MethodLog(desc = "委托授权列表",master = "委托授权管理")
	@ApiOperation(value = "委托授权列表",notes = "")
	@ApiResponse(response = Surrogate.class,code = 0,message = "code=0时返回")
	@GetMapping(value = "list")
	public R<Page<Surrogate>> list(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {
		Page<Surrogate> page = new Page<>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		facets.searchSurrogate(page, new QueryFilter());
		return R.ok(page);
	}

	/**
	 * 获取委托对象
	 * @param id
	 * @return
	 */
	@MethodLog(desc = "获取委托对象",master = "委托授权管理")
	@ApiOperation(value = "获取委托对象",notes = "")
	@ApiResponse(response = SurrogateDTO.class,code = 0,message = "code=0时返回")
	@GetMapping(value = "get/{id}")
	public SurrogateDTO get(@PathVariable("id") String id) {
		SurrogateDTO dto = new SurrogateDTO();
		//委托代理对象
		Surrogate surrogate = facets.getSurrogate(id);
		//获取委托人
		List<String> processNames = facets.getAllProcessNames();
		dto.setSurrogate(surrogate);
		dto.setProcessNames(processNames);
		return dto;
	}

	/**
	 * 修改
	 * @param surrogate bean
	 * @param id id
	 * @return
	 */
	@MethodLog(desc = "修改",master = "委托授权管理")
	@ApiOperation(value = "修改",notes = "")
	@ApiResponse(response = Surrogate.class,code = 0,message = "code=0时返回")
	@PutMapping(value = "update/{id}")
	public boolean update(Surrogate surrogate,@PathVariable("id")Integer id) {
		try {
			surrogate.setId(String.valueOf(id));
			surrogate.setSdate(surrogate.getSdate() + " 00:00:00");
			surrogate.setEdate(surrogate.getEdate() + " 23:59:59");
			facets.addSurrogate(surrogate);
			return true;
		}catch (BizRuntimeException e){
			e.printStackTrace();
			log.error(e.getErrMsg(),"保存或更新失败!");
			return false;
		}
	}

	/**
	 * 删除
	 * @param id id
	 * @return boolean
	 */
	@MethodLog(desc = "删除",master = "委托授权管理")
	@ApiOperation(value = "删除",notes = "")
	@ApiResponse(response = Surrogate.class,code = 0,message = "code=0时返回")
	@DeleteMapping(value = "delete/{id}")
	public boolean delete(@PathVariable("id") String id) {
		try {
			facets.deleteSurrogate(id);
			return true;
		}catch (BizRuntimeException e){
			e.printStackTrace();
			log.error(e.getErrMsg(),"删除失败!");
			return false;
		}
	}
}
