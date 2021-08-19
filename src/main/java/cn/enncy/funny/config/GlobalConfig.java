package cn.enncy.funny.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * //TODO
 * <br/>Created in 17:20 2021/8/16
 *
 * @author: enncy
 */

@Configuration
@EnableOpenApi
public class GlobalConfig  implements WebMvcConfigurer, ErrorPageRegistrar {


    /**
     * 配置连接池
     *
     * @return: javax.sql.DataSource
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource dataSource() {
        return new DruidDataSource();
    }


    /**
     * mybatis 配置
     *
     * @return: com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 最新版 mybatis 分页器
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 乐观锁
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

    /**
     * swagger 配置
     *
     * @return: springfox.documentation.spring.web.plugins.Docket
     */
    @Bean
    public Docket createRestApi() {

        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("SpringBoot-Swagger3")
                .description("author : enncy")
                .license("Apache-2.0")
                .version("0.0.1")
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                //创建
                .build();
    }

    /**
     * 配置消息转换器
     *
     * @return: void
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        // 设置默认编码
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        FastJsonConfig config = new FastJsonConfig();
        config.setFeatures(Feature.IgnoreAutoType,Feature.TrimStringFieldValue);
        converter.setFastJsonConfig(config);
        // 设置类型
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        // 清除所有转换器
        converters.clear();
        converters.add(converter);
    }


    /**
     *
     * 配置错误页面
     * @param registry ErrorPageRegistry
     * @return: void
     */
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        registry.addErrorPages(Arrays.stream(HttpErrorStateConverter.values())
                .map(c->c.status)
                .map(s->new ErrorPage(s, "/error?status=" + s.value()))
                .toArray(ErrorPage[]::new));
    }
}
