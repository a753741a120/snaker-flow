package com.snaker.framework.config.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageInfo;
import com.snaker.framework.config.annotation.MethodLog;
import com.snaker.framework.config.dto.DictionaryDTO;
import com.snaker.framework.config.entity.Dictionary;
import com.snaker.framework.config.entity.DictionaryItem;
import com.snaker.framework.config.service.DictionaryManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 配置字典管理Controller
 * @author pm
 * @since 0.1
 */
@Api(tags = "配置字典管理接口")
@RestController
@RequestMapping(value = "/config/dictionary")
public class DictionaryController {
	/**
	 * 注入配置字典管理对象
	 */
	@Resource
	private DictionaryManager dictionaryManager;

	/**
	 * 分页查询配置，返回配置字典列表视图
	 * @return
	 */
	@MethodLog(desc = "字典列表",master = "配置字典管理")
	@ApiOperation(value = "字典列表",notes = "")
	@ApiResponse(response = Dictionary.class,code = 0,message = "code=0时返回")
	@GetMapping("/list")
	public R<PageInfo<Dictionary>> list(@RequestParam("pageNum")Integer pageNo,
										@RequestParam("pageSize")Integer pageSize) {
		PageInfo<Dictionary> page = dictionaryManager.findPage(pageNo, pageSize);
		return R.ok(page);
	}

	/**
	 * 新建配置字典时，返回配置字典编辑视图
	 * @return
	 */
	@MethodLog(desc = "获取单个配置字典信息",master = "配置字典管理")
	@ApiOperation(value = "获取单个配置字典信息",notes = "")
	@ApiResponse(response = Dictionary.class,code = 0,message = "code=0时返回")
	@GetMapping("/get/{id}")
	public R<Dictionary> get(@PathVariable("id")Integer id){
		return R.ok(dictionaryManager.getById(id));
	}

	/**
	 * 保存配置字典
	 * @param dictionary
	 * @return
	 */
	@MethodLog(desc = "保存",master = "配置字典管理")
	@ApiOperation(value = "保存",notes = "")
	@ApiResponse(response = Dictionary.class,code = 0,message = "code=0时返回")
	@PostMapping("/save")
	public boolean save(@RequestBody @Valid Dictionary dictionary){
		return dictionaryManager.save(dictionary);
	}

	/**
	 * 新增、编辑配置字典页面的提交处理。保存配置字典实体，并返回配置字典列表视图
	 * @param dictionary dictionary
	 * @return
	 */
	@MethodLog(desc = "修改",master = "配置字典管理")
	@ApiOperation(value = "修改",notes = "")
	@ApiResponse(response = DictionaryDTO.class,code = 0,message = "code=0时返回")
	@PutMapping(value = "/update/{id}")
	public boolean update(@PathVariable("id")Integer id,
						  @RequestBody DictionaryDTO dictionary) {
		return dictionaryManager.save(id,dictionary);
	}

	/**
	 * 根据主键ID删除配置字典实体，并返回配置字典列表视图
	 * @param id
	 * @return
	 */
	@MethodLog(desc = "根据主键ID删除配置字典实体",master = "配置字典管理")
	@ApiOperation(value = "删除",notes = "")
	@ApiResponse(response = Dictionary.class,code = 0,message = "code=0时返回")
	@DeleteMapping(value = "/delete/{id}")
	public boolean delete(@PathVariable("id") Long id) {
		return dictionaryManager.removeById(id);
	}

    /**
     * 根据字典名称获取数据
     * @param config
     * @return
     */
	@MethodLog(desc = "根据字典名称获取数据",master = "配置字典管理")
	@ApiOperation(value = "根据字典名称获取数据",notes = "")
	@ApiResponse(response = DictionaryItem.class,code = 0,message = "code=0时返回")
    @GetMapping("/getItemsByName")
    public R<List<DictionaryItem>> getItemsByName(@RequestParam("config") String config) {
		List<DictionaryItem> list = dictionaryManager.getItemsByName(config);
		return R.ok(list);
    }

	/**
	 * 返回字典列表 无分页
	 * @return
	 */
	@MethodLog(desc = "返回字典列表 无分页",master = "配置字典管理")
	@ApiOperation(value = "返回字典列表(无分页)",notes = "")
	@ApiResponse(response = Dictionary.class,code = 0,message = "code=0时返回")
    @GetMapping(value = "/dicts")
    public R<List<Dictionary>> getDicts() {
        return R.ok(dictionaryManager.list());
    }
}
