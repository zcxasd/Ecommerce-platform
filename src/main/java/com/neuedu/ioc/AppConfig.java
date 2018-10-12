package com.neuedu.ioc;

import com.neuedu.pojo.Category;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {
    @Bean(name = "category1")
    @Scope("prototype")
    public Category category(){
     Category category = new Category();
     category.setId(1000);
     return category;
    }
}
