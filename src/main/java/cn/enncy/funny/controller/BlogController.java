package cn.enncy.funny.controller;


import cn.enncy.funny.annotation.ResponseHandler;
import cn.enncy.funny.annotation.Roles;
import cn.enncy.funny.constant.Role;
import cn.enncy.funny.dto.BaseDto;
import cn.enncy.funny.entity.Blog;
import cn.enncy.funny.exceptions.ServiceException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author enncy
 * @since 2021-08-22
 */
@RestController
@RequestMapping("/blog")
@Api(tags = "文章信息")
@ResponseHandler
public class BlogController extends ServiceController<Blog> {

    public BlogController(IService<Blog> service) {
        super(service);
    }

}

