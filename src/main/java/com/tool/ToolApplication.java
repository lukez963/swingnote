package com.tool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import java.awt.BorderLayout;

@SpringBootApplication
public class ToolApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(ToolApplication.class).headless(false).run(args);
		Window window = context.getBean(Window.class);
		window.run();
	}

}
