package com.example.freshonline.config;

import com.example.freshonline.security.CustomMethodSecurityExpressionHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.DenyAllPermissionEvaluator;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class GloabalSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity security) throws Exception
    {
        security.httpBasic().disable()
                .csrf().disable();
    }
}

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecureConfig extends GlobalMethodSecurityConfiguration {


    private final ApplicationContext applicationContext;
    public SecureConfig(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        final PermissionEvaluator permissionEvaluator = new DenyAllPermissionEvaluator();
        final CustomMethodSecurityExpressionHandler expressionHandler = new CustomMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(permissionEvaluator);
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }


}



