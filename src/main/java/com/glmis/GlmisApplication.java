package com.glmis;

import com.glmis.JpaRepository.MyRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *@EnableCaching 开启缓存功能
 */
@EnableTransactionManagement
@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableJpaRepositories(
		basePackages = "com.glmis.repository",
		repositoryFactoryBeanClass = MyRepositoryFactoryBean.class)
public class GlmisApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(GlmisApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(GlmisApplication.class, args);
	}

}
