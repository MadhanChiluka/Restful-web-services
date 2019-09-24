package com.madhan.rest.webservices.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	@Autowired
	private MessageSource msgSource;
	
	@GetMapping(path="/helloWorld")
	public String helloWorld() {
		return "Hello World";
	}
	
	@GetMapping("/hello-world-bean") 
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World Bean");
	}
	
	@GetMapping("/hello-world/path-variable/{name}") 
	public HelloWorldBean helloWorldPathVaribale(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World %s", name));
	}
	
	@GetMapping("/hello-world-internationalized") 
	public String helloWorldInternationalization() {
		return msgSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
	}
}
