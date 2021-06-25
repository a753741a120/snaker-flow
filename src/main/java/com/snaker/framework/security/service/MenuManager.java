package com.snaker.framework.security.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.snaker.framework.orm.Page;
import com.snaker.framework.orm.PropertyFilter;
import com.snaker.framework.security.dao.MenuDao;
import com.snaker.framework.security.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 菜单管理类
 * @author yuqs
 * @since 0.1
 */
@Component
@Service
public class MenuManager extends ServiceImpl<MenuDao, Menu> {
	//注入菜单持久化对象
	@Autowired
	private MenuDao menuDao;
	//注入资源管理对象
	@Autowired
	private ResourceManager resourceManager;

	/**
	 * 保存菜单实体
	 * @param entity
	 */
	public boolean save(Menu entity) {
		return menuDao.insert(entity) > 0;
	}

	/**
	 * 根据主键ID删除菜单实体
	 * @param id
	 */
	public void delete(Long id) {
		resourceManager.updateByMenuId(id);
		menuDao.deleteById(id);
	}

	/**
	 * 根据主键ID获取菜单实体
	 * @param id
	 * @return
	 */
	public Menu get(Long id) {
		return menuDao.selectById(id);
	}

	/**
	 * 根据分页对象、过滤集合参数，分页查询菜单列表
	 * @param page
	 * @param filters
	 * @return
	 */
	public PageInfo<Menu> findPage(final Page<Menu> page, final List<PropertyFilter> filters) {
		PageHelper.startPage(page);
		LambdaQueryWrapper<Menu> qw = new LambdaQueryWrapper<>();
		return new PageInfo<>(menuDao.selectList(qw));
	}

	/**
	 * 获取所有菜单
	 * @return
	 */
	public List<Menu> getAll() {
		return menuDao.selectList(new LambdaQueryWrapper<>());
	}

	/**
	 * 根据用户ID查询该用户允许访问的所有菜单列表
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> getAllowedAccessMenu(Long userId) {
        List<Menu> list = menuDao.getAllowedAccessMenu(userId);
//        SQLQuery query = menuDao.createSQLQuery(sqlBuffer.toString(), userId);
//		query.addEntity(Menu.class);
//		return query.list();
		return list;
	}
}
