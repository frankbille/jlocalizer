package org.jlocalizer.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestApplicationContext {

	@Test
	public void smokeTest() {
		String location = "/org/jlocalizer/service/applicationContext-service.xml";

		ApplicationContext ctx = new ClassPathXmlApplicationContext(location);

		assertThat(ctx.getBean("projectService"), is(notNullValue()));
	}

}
