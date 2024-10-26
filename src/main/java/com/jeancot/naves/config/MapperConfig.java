package com.jeancot.naves.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean("naveMapper")
    public ModelMapper productMapper(){
        return new ModelMapper();
    }
}
