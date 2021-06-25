package com.snaker.framework.security.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.snaker.framework.orm.Page;
import com.snaker.framework.orm.PropertyFilter;
import com.snaker.framework.security.dao.ResourceDao;
import com.snaker.framework.security.entity.Authority;
import com.snaker.framework.security.entity.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 资源管理类
 * @author yuqs
 * @since 0.1
 */
@Component
public class ResourceManager {
	//注入资源持久化对象
	@Autowired
	private ResourceDao resourceDao;
	//注入权限持久化对象
	@Autowired
	private AuthorityManager authorityManager;

	/**
	 * 保存资源实体
	 * @param entity
	 */
	public boolean save(Resource entity) {
		return resourceDao.insert(entity) > 0;
	}

	/**
	 * 根据主键ID删除资源实体
	 * @param id
	 */
	public void delete(Long id) {
		List<Authority> authorities = authorityManager.getAll();
		for(Authority auth : authorities) {
			for(Resource resource : auth.getResources()) {
				if(resource.getId().longValue() == id.longValue()) {
					auth.getResources().remove(resource);
					authorityManager.save(auth);
					break;
				}
			}
		}
		resourceDao.deleteById(id);
	}

	public void updateByMenuId(Long menuId) {
//		String hql = "from Resource as r where r.menu.id=?";
//		Resource resource = resourceDao.findUnique(hql, menuId);
		//先查询是否有数据
		Resource resource = resourceDao.selectById(menuId);
		if(resource != null) {
			resource.setMenu(null);
			resourceDao.insert(resource);
		}
	}

	/**
	 * 根据主键ID获取资源实体
	 * @param id
	 * @return
	 */
	public Resource get(Long id) {
		return resourceDao.selectById(id);
	}

	/**
	 * 根据分页对象、过滤集合参数，分页查询资源列表
	 * @param page
	 * @param filters
	 * @return
	 */
	public PageInfo<Resource> findPage(final Page<Resource> page, final List<PropertyFilter> filters) {
		PageHelper.startPage(page);
		LambdaQueryWrapper<Resource> qw = new LambdaQueryWrapper<>();
		return new PageInfo<>(resourceDao.selectList(qw));
	}

	/**
	 * 查询所有资源记录
	 * @return
	 */
	public List<Resource> getAll() {
		return resourceDao.selectList(new LambdaQueryWrapper<>());
	}

	/**
	 * 根据用户ID查询该用户具有权限访问的资源与不需要授权的资源
	 * @param userId
	 * @return
	 */
	public List<Resource> getAuthorizedResource(Long userId) {
		List<Resource> query = resourceDao.getAuthorizedResource(userId);
		return query;
	}
}
