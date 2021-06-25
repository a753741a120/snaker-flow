package com.snaker.framework.security.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.snaker.framework.orm.Page;
import com.snaker.framework.orm.PropertyFilter;
import com.snaker.framework.security.dao.OrgDao;
import com.snaker.framework.security.entity.Org;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 部门管理类
 * @author yuqs
 * @since 0.1
 */
@Component
@Service
public class OrgManager extends ServiceImpl<OrgDao,Org> {
	//注入部门持久化对象
	@Autowired
	private OrgDao orgDao;

	/**
	 * 保存部门实体
	 * @param entity
	 */
	public boolean save(Org entity) {
		return orgDao.insert(entity) > 0;
	}

	/**
	 * 根据主键ID删除对应的部门
	 * @param id
	 */
	public void delete(Long id) {
		orgDao.deleteById(id);
	}

	/**
	 * 根据主键ID获取部门实体
	 * @param id
	 * @return
	 */
	public Org get(Long id) {
		return orgDao.selectById(id);
	}

	/**
	 * 根据分页对象、过滤集合参数，分页查询部门列表
	 * @param page
	 * @param filters
	 * @return
	 */
	public PageInfo<Org> findPage(final Page<Org> page, final List<PropertyFilter> filters) {
		PageHelper.startPage(page);
		return new PageInfo<Org>(orgDao.selectList(new LambdaQueryWrapper<Org>()));
	}

	/**
	 * 获取所有部门记录
	 * @return
	 */
	public List<Org> getAll() {
		return orgDao.selectList(new LambdaQueryWrapper<>());
	}

	/**
	 * 根据parentId获取所有下级部门
	 * @param parentId
	 * @return
	 */
	public List<Org> getByParent(Long parentId) {
		if(parentId == null || parentId == Org.ROOT_ORG_ID) {
			return getAll();
		}
		LambdaQueryWrapper<Org> qw = new LambdaQueryWrapper<>();
		qw.eq(Org::getParentOrg,parentId);
		return orgDao.selectList(qw);
	}
}
