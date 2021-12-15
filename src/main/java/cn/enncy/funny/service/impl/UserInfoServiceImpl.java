package cn.enncy.funny.service.impl;

import cn.enncy.funny.entity.UserInfo;
import cn.enncy.funny.mapper.UserInfoMapper;
import cn.enncy.funny.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息详情表 服务实现类
 * </p>
 *
 * @author enncy
 * @since 2021-08-22
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
