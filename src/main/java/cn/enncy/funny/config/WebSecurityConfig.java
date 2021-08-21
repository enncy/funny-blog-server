package cn.enncy.funny.config;


import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * //TODO
 * <br/>Created in 17:02 2021/8/20
 *
 * @author: enncy
 */

@EnableWebSecurity
@Component
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/swagger-ui/**").hasRole("admin")
                .antMatchers("/**").permitAll();
        http.formLogin();
    }

}
