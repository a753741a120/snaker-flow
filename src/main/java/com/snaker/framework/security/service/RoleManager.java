package com.snaker.framework.security.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.snaker.framework.orm.Page;
import com.snaker.framework.orm.PropertyFilter;
import com.snaker.framework.security.dao.RoleDao;
import com.snaker.framework.security.entity.Role;
import com.snaker.framework.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色管理类
 * @author yuqs
 * @since 0.1
 */
@Component
@Service
public class RoleManager extends ServiceImpl<RoleDao,Role> {
	//注入角色持久化对象
	@Autowired
	private RoleDao roleDao;
	//注入用户管理对象
	@Autowired
	private UserManager userManager;

	/**
	 * 保存、更新角色实体
	 * @param entity
	 */
	public boolean save(Role entity) {
		return roleDao.insert(entity) > 0;
	}

	/**
	 * 根据主键ID删除对应的角色
	 * @param id
	 */
	public void delete(Long id) {
		List<User> users = userManager.getAll();
		for(User user : users) {
			for(Role role : user.getRoles()) {
				if(role.getId().longValue() == id.longValue()) {
					user.getRoles().remove(role);
					userManager.save(user);
					break;
				}
			}
		}
		roleDao.deleteById(id);
	}

	/**
	 * 根据主键ID获取角色实体
	 * @param id
	 * @return
	 */
	public Role get(Long id) {
		return roleDao.selectById(id);
	}

	/**
	 * 根据分页对象、过滤集合参数，分页查询角色列表
	 * @param page
	 * @param filters
	 * @return
	 */
	public PageInfo<Role> findPage(final Page<Role> page, final List<PropertyFilter> filters) {
		PageHelper.startPage(page);
		LambdaQueryWrapper<Role> qw = new LambdaQueryWrapper<>();
		return new PageInfo<>(roleDao.selectList(qw));
	}

	/**
	 * 获取所有角色记录
	 * @return
	 */
	public List<Role> getAll() {
		LambdaQueryWrapper<Role> qw = new LambdaQueryWrapper<>();
		return roleDao.selectList(qw);
	}
}
