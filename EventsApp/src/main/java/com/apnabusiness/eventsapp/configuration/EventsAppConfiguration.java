package com.apnabusiness.eventsapp.configuration;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
 
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.apnabusiness.eventsapp")
public class EventsAppConfiguration extends WebMvcConfigurerAdapter{
     
	private static final Logger logger = Logger.getLogger(EventsAppConfiguration.class);
	
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("configuring view resolvers");
    	}
    	
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        registry.viewResolver(viewResolver);
    }
 
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("Adding resource handlers");
    	}
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }
 
}