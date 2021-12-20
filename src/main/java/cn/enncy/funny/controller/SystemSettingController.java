package cn.enncy.funny.controller;


import cn.enncy.funny.annotation.ResponseHandler;
import cn.enncy.funny.annotation.Roles;
import cn.enncy.funny.constant.Role;
import cn.enncy.funny.entity.PageEntity;
import cn.enncy.funny.entity.SystemSetting;
import cn.enncy.funny.service.SystemSettingService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * //TODO
 * <br/>Created in 19:20 2021/12/20
 *
 * @author enncy
 */

@RestController
@ResponseHandler
@RequestMapping(value = "/system/setting")
@Api(tags = "系统设置")
@Roles(Role.ROOT)
public class SystemSettingController extends ServiceController<SystemSetting> {

    private final SystemSettingService service;

    public SystemSettingController(SystemSettingService service) {
        super(service);
        this.service = service;
    }

    @GetMapping("/get/by/name")
    @ApiOperation("查找设置")
    public SystemSetting findByName(@RequestParam("name") String name) {
        return service.lambdaQuery().eq(SystemSetting::getName, name).one();
    }




}
