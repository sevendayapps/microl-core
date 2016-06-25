package com.microl.core.cache;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;


public class CacheProperties{
	
	private final Properties properties;
	
	private CacheProperties(CachePropertyBuilder builder) {
		properties = builder.getProperties();
	}
	
	public String getProperty(String key) {
		return properties.getProperty(key);
	}
	
	public static class CachePropertyBuilder {
		
		private Properties properties;
		
		public CachePropertyBuilder() {
			properties = new Properties();
			
			addProperty("default.lru.removal.count", "100");
			addProperty("default.refresh.time", "3600000");
			addProperty("default.lru.removal.time", "15000");
			addProperty("interval.but.full.lru.removal.count", "100");
		}
		
		public void addProperty(String key, String value) {
			properties.put(key, value);
		}
		
		public String getProperty(String key) {
			return properties.getProperty(key);
		}
		
		public Properties getProperties() {
			return properties;
		}
		
		public Boolean validate() {
			Set<Object> keySet = properties.keySet();
			StandardCacheProperties standardProps = new StandardCacheProperties();
			for (Object keyObject : keySet) {
				String key = (String) keyObject;
				if(!standardProps.isExisted(key)) {
					System.out.println("The key " + key + "is not valid.");
					return Boolean.FALSE;
				} 
				
				String value = properties.getProperty(key);
				String standardType = standardProps.get(key);
				// check the type of value
				if(!checkStandardType(value, standardType)) {
					System.out.println("The value " + value + " doesn't have a valid type.");
					return Boolean.FALSE;
				}
			}
			
			return Boolean.TRUE;
		}
		
		public Boolean checkStandardType(String value, String standardType) {
			if(standardType.toLowerCase().equals("long") ||
					standardType.toLowerCase().equals("double") ||
					standardType.toLowerCase().equals("integer") ) {
				
				if(!StringUtils.isNumeric(value)) {
					return Boolean.FALSE;
				}
			} else if(standardType.toLowerCase().equals("boolean")) {
				
				if(!value.toLowerCase().equals("true") && 
						!value.toLowerCase().equals("false")) {
					return Boolean.FALSE;
				}
			}
			
			return Boolean.TRUE;
		}
		
		public CacheProperties build() {
			if(validate()) {
				return new CacheProperties(this);
			}
			
			return null;
		}
		
		private class StandardCacheProperties {
			Properties props = new Properties();
			
			public StandardCacheProperties() {
				// load the standard cache properties
				InputStream inputStream = StandardCacheProperties.class.getClassLoader()
											.getResourceAsStream("conf/standard-cache-properties.properties");
				
				if(inputStream != null) {
					try {
						props.load(inputStream);
					} catch(IOException ioe) {
						System.out.println("IOException at load standard-cache-properties.");
					} finally {
						System.out.println("Standard Cache Properties Size is " + props.size());
					}
				}
			}

			public String get(String key) {
				return props.getProperty(key);
			}
			
			public Boolean isExisted(String key) {
				return props.containsKey(key);
			}
		}
	}
}
