package com.exam.examservice;

import org.apache.catalina.connector.Connector;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author LILEI
 */
@SpringBootApplication
@MapperScan("com.exam.examservice.mapper")
@EnableTransactionManagement
@EnableAutoConfiguration
@Component("com.exam")
@EnableScheduling
public class ExamServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamServiceApplication.class, args);
	}

	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource){
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public Converter<String, Date> converter() {
		return new Converter<String, Date>() {
			@Override
			public Date convert(String source) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				try {
					return dateFormat.parse(source);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return null;
			}
		};
	}


}

