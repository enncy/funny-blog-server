package cn.enncy.funny.service.impl;

import cn.enncy.funny.entity.Blog;
import cn.enncy.funny.mapper.BlogMapper;
import cn.enncy.funny.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author enncy
 * @since 2021-08-22
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
