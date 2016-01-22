package com.apnabusiness.ayuculture.configuration;

import javax.servlet.Filter;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AyuCultureInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	  
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { AyuCultureConfiguration.class };
    }
   
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }
   
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
     
    @Override
    protected Filter[] getServletFilters() {
        Filter [] singleton = { new CORSFilter() };
        return singleton;
    }
  
}
