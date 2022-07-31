package com.example.freshonline.config;
import com.example.freshonline.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


@Configuration
public class GlobalMvcConfig implements WebMvcConfigurer {

    // handler cors problem
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://138.197.147.136:4000", "http://localhost:4000")
                .allowCredentials(true)
                .allowedMethods("*")
                .allowedHeaders("*")
                .maxAge(3600);
    }



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/goods/pictures/**",
                        "/goods/pictures/**/**",
                        "/home",
                        "/",
                        "/toLogin",
                        "/toRegister",
                        "/categoryTree",
                        "/weekly_special",
                        "/random_goods",
                        "/goods"
                );
    }
}
