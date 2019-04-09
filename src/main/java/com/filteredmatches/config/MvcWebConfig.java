package com.filteredmatches.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@Configuration
@ComponentScan({"com.filteredmatches.controller"})
public class MvcWebConfig implements WebMvcConfigurer {

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/views/");
		viewResolver.setContentType("text/html; charset=UTF-8");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		

		// Register resource handler for CSS and JS
		registry.addResourceHandler("/css/**")
				.addResourceLocations("css/");

	}
}
