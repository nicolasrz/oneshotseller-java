package com.nruiz.oneshot.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyConfiguration extends WebMvcConfigurerAdapter{

    @Value("${front.url}")
    private String frontUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins(this.frontUrl)
            .allowedMethods("GET", "POST")
            .allowedHeaders("*");
    }


}
