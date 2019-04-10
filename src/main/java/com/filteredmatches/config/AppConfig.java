package com.filteredmatches.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan({"com.filteredmatches.data","com.filteredmatches.service","com.filteredmatches.controller"})
public class AppConfig{
	
}