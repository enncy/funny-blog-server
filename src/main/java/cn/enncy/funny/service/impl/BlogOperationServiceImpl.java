package cn.enncy.funny.service.impl;

import cn.enncy.funny.entity.BlogOperation;
import cn.enncy.funny.mapper.BlogOperationMapper;
import cn.enncy.funny.service.BlogOperationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户操作文章信息表 服务实现类
 * </p>
 *
 * @author enncy
 * @since 2021-08-22
 */
@Service
public class BlogOperationServiceImpl extends ServiceImpl<BlogOperationMapper, BlogOperation> implements BlogOperationService {

}
