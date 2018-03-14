package com.zht.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zht.atcrowdfunding.beans.User;

/**
 * <bean id="user" class="xxx.User">
 * 
 * </bean>
 * @author : ZHT
 * @date : 2018年2月1日
 */
@Configuration
public class BeanConfig {

	@Bean
	public User user() {
		return new User();
	}
}
