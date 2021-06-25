package com.snaker.system.api.service.impl;

import com.snaker.system.api.entity.SecUser;
import com.snaker.system.api.dao.SecUserMapper;
import com.snaker.system.api.service.ISecUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuxzh
 * @since 2018-12-20
 */
@Service
public class SecUserServiceImpl extends ServiceImpl<SecUserMapper, SecUser> implements ISecUserService {

}
