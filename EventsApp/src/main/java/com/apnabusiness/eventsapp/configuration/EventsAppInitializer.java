package com.apnabusiness.eventsapp.configuration;

import javax.servlet.Filter;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class EventsAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	private static final Logger logger = Logger.getLogger(EventsAppInitializer.class);
	
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { EventsAppConfiguration.class };
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
