package com.zht.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.zht.atcrowdfunding.beans.User;

public class TestSpring {

	public static void main(String[] args) {
		ApplicationContext context = 
				new AnnotationConfigApplicationContext("com.zht.test");
		User user = (User)context.getBean("user");
		System.out.println(user);
	}
}
