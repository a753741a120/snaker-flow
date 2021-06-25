package com.snaker.framework.security.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.snaker.framework.orm.Page;
import com.snaker.framework.orm.PropertyFilter;
import com.snaker.framework.security.dao.AuthorityDao;
import com.snaker.framework.security.entity.Authority;
import com.snaker.framework.security.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限管理类
 * @author yuqs
 * @since 0.1
 */
@Component
@Service
public class AuthorityManager extends ServiceImpl<AuthorityDao, Authority> {
	//注入权限持久化对象
	@Autowired
	private AuthorityDao authorityDao;
	//注入角色管理对象
	@Autowired
	private RoleManager roleManager;

	/**
	 * 保存权限实体
	 * @param entity
	 */
	public boolean save(Authority entity) {
		return this.save(entity);
	}

	/**
	 * 根据主键ID删除对应的实体
	 * @param id
	 */
	public void delete(Long id) {
		List<Role> roles = roleManager.getAll();
		for(Role role : roles) {
			for(Authority auth : role.getAuthorities()) {
				if(auth.getId().longValue() == id.longValue()) {
					role.getAuthorities().remove(auth);
					roleManager.save(role);
					break;
				}
			}
		}
		authorityDao.deleteById(id);
	}

	/**
	 * 根据主键ID获取权限实体
	 * @param id
	 * @return
	 */
	public Authority get(Long id) {
		return authorityDao.selectById(id);
	}

	/**
	 * 根据分页对象、过滤集合参数，分页查询权限列表
	 * @param page
	 * @param filters
	 * @return
	 */
	public PageInfo<Authority> findPage(final Page<Authority> page, final List<PropertyFilter> filters) {
		PageHelper.startPage(page);
		LambdaQueryWrapper<Authority> qw = new LambdaQueryWrapper<>();
		return new PageInfo<>(authorityDao.selectList(qw));
	}

	/**
	 * 获取所有权限记录
	 * @return
	 */
	public List<Authority> getAll() {
		return authorityDao.selectList(new LambdaQueryWrapper<Authority>());
	}
}
