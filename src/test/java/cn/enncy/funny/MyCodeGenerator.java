package cn.enncy.funny;


import cn.enncy.funny.controller.service.ServiceController;
import cn.enncy.funny.pojo.BaseEntity;
import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;

/**
 * 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
 * <br/>Created in 10:45 2021/8/16
 *
 * @author: enncy
 */

@SpringBootTest
public class MyCodeGenerator {

    @Autowired
    DruidDataSource dataSource;

    // 作者名
    private static final String author = "enncy";
    // 模块名
    private static final String module = "test";
    // 包名
    private static final String packageName = "cn.enncy";
    // 要生成的表名
    private static final String[] tableNames = {"user"};

    @Test
    public  void generate() {

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor(author);
        gc.setOpen(false);
        gc.setFileOverride(false); // 是否覆盖
        gc.setServiceName("%sService"); // 去掉 Service 的I前缀
        gc.setIdType(IdType.ASSIGN_ID);
        gc.setDateType(DateType.ONLY_DATE);
        gc.setSwagger2(true);// 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(dataSource.getUrl());
        dsc.setDriverName(dataSource.getDriverClassName());
        dsc.setUsername(dataSource.getUsername());
        dsc.setPassword(dataSource.getPassword());
        mpg.setDataSource(dsc);


        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(module);
        pc.setParent(packageName);
        mpg.setPackageInfo(pc);

        // 自动填充配置

        TableFill createTime = new TableFill("create_time", FieldFill.INSERT);
        TableFill updateTime = new TableFill("update_time", FieldFill.INSERT_UPDATE);


        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude(tableNames)
                .setNaming(NamingStrategy.underline_to_camel) // 下划线转驼峰
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setEntityLombokModel(true)     // 开启lombok
                .setLogicDeleteFieldName("deleted") // 逻辑删除
                .setSuperEntityClass(BaseEntity.class)  // 实体父类
                .setSuperControllerClass(ServiceController.class)  // 控制器父类
                .setRestControllerStyle(true)   // 设置 restful 风格
                .setTableFillList(Arrays.asList(createTime, updateTime)) // 自动填充
                .setVersionFieldName("version");    // 乐观锁

        mpg.setStrategy(strategy);
        mpg.execute();
    }

}
