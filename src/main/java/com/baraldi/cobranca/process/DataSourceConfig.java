package com.baraldi.cobranca.process;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;

@Configuration
public class DataSourceConfig {

	/*
	@Bean
    public DataSource datasource() {
		
       return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost/cobranca")
                .username("root")
                .password("")
                .build();
    }
    */

}
