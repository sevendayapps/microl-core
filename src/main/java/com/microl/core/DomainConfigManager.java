package com.microl.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class DomainConfigManager {
	
	private Properties props = new Properties();
	
	private Timer timer;
	private static final Integer DEFAULT_INTERVAL = 1*60*60*1000;
	
	
	public DomainConfigManager(String resource, Integer triggerInterval) {
		getResources(resource);
		timer = new Timer("domainAutoConfig");
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				getResources(resource);
			}
		}, triggerInterval != null ? triggerInterval : DEFAULT_INTERVAL);
	}
	
	public void get(String key) {
		props.get(key);
	}
	
	public void getResources(String resource) {
		InputStream inputStream = DomainConfigManager.class.getClassLoader()
									.getResourceAsStream(resource);
		if(inputStream != null) {
			try {
				props.load(inputStream);
			} catch(IOException ioe) {
				System.out.println("IOException when load appconfig.");
			} finally {
				System.out.println("Config size : " + props.size());
			}
		}
	}
}
