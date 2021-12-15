package cn.enncy.funny.service.impl;


import cn.enncy.funny.entity.User;
import cn.enncy.funny.mapper.UserMapper;
import cn.enncy.funny.service.UserService;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * //TODO
 * <br/>Created in 11:07 2021/8/22
 *
 * @author: enncy
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {


}
