package com.example.freshonline.config;
import com.example.freshonline.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class GlobalMvcConfig implements WebMvcConfigurer {

    // handler cors problem
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("*")
                .maxAge(3600);
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pics/goods_pics/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"pics"
                        +System.getProperty("file.separator")+"goods_pics"+System.getProperty("file.separator")
        );
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login","/register","/home","/");
    }
}
