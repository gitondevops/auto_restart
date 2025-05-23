package com.mediquity.auto_restart;

import com.mediquity.auto_restart.model.RemoteMonitorProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RemoteMonitorProperties.class)
public class AutoRestartApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoRestartApplication.class, args);
	}

}
