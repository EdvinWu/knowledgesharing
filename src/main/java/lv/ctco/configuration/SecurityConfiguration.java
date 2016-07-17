package lv.ctco.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("qwer").authenticated().and().formLogin()
                .loginPage("/login")
                .permitAll().and()
                .httpBasic().and()
                .logout().logoutSuccessUrl("/login?logut").and()
                .csrf().disable().logout().permitAll();
        httpSecurity.headers().frameOptions().disable();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource)
//                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery(
                        "select LOGIN, PASSWORD, 1 from persons where LOGIN=?")
                .authoritiesByUsernameQuery(
                        "select LOGIN, user_role " +
                                "from persons p " +
                                "INNER JOIN user_roles ur ON ur.id = p.id " +
                                "where p.LOGIN=?");
    }

//        auth.jdbcAuthentication().dataSource(dataSource)
//                .passwordEncoder(passwordEncoder())
//                .usersByUsernameQuery(
//                        "SELECT userLogin, password, 1 FROM persons WHERE userLogin=?")
//                .authoritiesByUsernameQuery(
//                        "SELECT userLogin,user_role " +
//                                "FROM persons p " +
//                                "INNER JOIN user_roles ur ON ur.personfk = p.id " +
//                                "WHERE p.userLogin=?");
//    }
}