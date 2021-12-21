package cn.enncy.funny.controller;


import cn.enncy.funny.annotation.ResponseHandler;
import cn.enncy.funny.annotation.Roles;
import cn.enncy.funny.constant.Role;
import cn.enncy.funny.dto.BaseDto;
import cn.enncy.funny.entity.BlogOperation;
import cn.enncy.funny.exceptions.ServiceException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户操作文章信息表 前端控制器
 * </p>
 *
 * @author enncy
 * @since 2021-08-22
 */
@RestController
@RequestMapping("/operation/blog")
@Api(tags = "文章操作记录")
@ResponseHandler
public class BlogOperationController extends ServiceController<BlogOperation> {

    public BlogOperationController(IService<BlogOperation> service) {
        super(service);
    }

}

