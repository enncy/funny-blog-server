package cn.enncy.funny.service.impl;

import cn.enncy.funny.entity.User;
import cn.enncy.funny.mapper.UserMapper;
import cn.enncy.funny.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author enncy
 * @since 2021-08-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
