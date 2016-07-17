package lv.ctco.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
//@ComponentScan("org.springframework.security.samples.mvc")
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {



    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/profile").setViewName("profile");
        registry.addViewController("/addSession").setViewName("addSession");
        registry.addViewController("/sessionDetails").setViewName("sessionDetails");
        registry.addViewController("/registration").setViewName("registration");
//        registry.addViewController("/adduser").setViewName("adduser");
//        registry.addRedirectViewController("/adduser","login");




//        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}