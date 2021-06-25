package com.snaker.framework.security.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.snaker.framework.orm.Page;
import com.snaker.framework.orm.PropertyFilter;
import com.snaker.framework.security.dao.UserDao;
import com.snaker.framework.security.entity.User;
import com.snaker.framework.utils.Digests;
import com.snaker.framework.utils.EncodeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户管理类
 * @author yuqs
 * @since 0.1
 */
@Component
@Service
public class UserManager extends ServiceImpl<UserDao,User> {
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;
	//注入用户持久化对象
	@Autowired
	private UserDao userDao;

	/**
	 * 保存、更新用户实体
	 * @param entity
	 */
	public boolean save(User entity) {
		if (StringUtils.isNotBlank(entity.getPlainPassword())) {
			entryptPassword(entity);
		}
		return userDao.insert(entity) > 0;
	}

	/**
	 * 根据主键ID删除对应的用户实体
	 * @param id
	 */
	public void delete(Long id) {
		userDao.deleteById(id);
	}

	/**
	 * 根据主键ID获取用户实体
	 * @param id
	 * @return
	 */
	public User get(Long id) {
		return userDao.selectById(id);
	}

	/**
	 * 根据用户名称，获取用户实体
	 * @param username
	 * @return
	 */
	public User findUserByName(String username) {
		LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
		qw.eq(User::getUsername,username);
		return userDao.selectOne(qw);
	}

	/**
	 * 根据用户名判断是否唯一
	 * @param newUserName
	 * @param oldUserName
	 * @return
	 */
//	public boolean isUserNameUnique(String newUserName, String oldUserName) {
//		return userDao.isPropertyUnique("username", newUserName, oldUserName);
//	}

	/**
	 * 根据分页对象、过滤集合参数，分页查询用户列表
	 * @param page
	 * @param filters
	 * @return
	 */
	public PageInfo<User> findPage(final Page<User> page, final List<PropertyFilter> filters) {
		PageHelper.startPage(page);
		LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
		return new PageInfo<>(userDao.selectList(qw));
	}

	/**
	 * 根据分页对象、所属部门ID号，分页查询用户列表
	 * @param page
	 * @param orgId
	 * @return
	 */
	public PageInfo<User> searchUser(final Page<User> page, Long orgId) {
		PageHelper.startPage(page);
		LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
		qw.eq(User::getOrg,orgId);
		return new PageInfo<User>(userDao.selectList(qw));
	}

	/**
	 * 查询所有记录
	 * @return
	 */
	public List<User> getAll() {
		LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
		return userDao.selectList(qw);
	}

	/**
	 * 根据orgId获取部门用户
	 * @param orgId
	 * @return
	 */
	public List<User> getByOrg(Long orgId) {
		LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
		if(orgId != null || orgId != 0L) {
			qw.eq(User::getOrg,orgId);
		}
		return userDao.selectList(qw);
	}

	/**
	 * 根据用户ID查询该用户所拥有的权限列表
	 * @param userId
	 * @return
	 */
	public List<String> getAuthoritiesName(Long userId) {
		return userDao.createSQLQuery(userId);
	}

	/**
	 * 根据用户ID查询该用户所拥有的角色列表
	 * @param userId
	 * @return
	 */
	public List<String> getRolesName(Long userId) {
		List<String> query = userDao.getRolesName(userId);
		return query;
	}

	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(EncodeUtils.hexEncode(salt));

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
		user.setPassword(EncodeUtils.hexEncode(hashPassword));
	}

}
