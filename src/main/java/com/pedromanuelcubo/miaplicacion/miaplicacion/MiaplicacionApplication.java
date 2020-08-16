package com.pedromanuelcubo.miaplicacion.miaplicacion;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;

@SpringBootApplication
public class MiaplicacionApplication extends Application {

	
	private ConfigurableApplicationContext context;
	
	@Override 
	public void init() throws Exception {
		
		
		org.springframework.context.ApplicationContextInitializer<GenericApplicationContext> initializer=
				ac -> {
					ac.registerBean(Application.class, ()->MiaplicacionApplication.this);
					ac.registerBean(Parameters.class, ()->getParameters());
					ac.registerBean(HostServices.class, ()->getHostServices());
					
				};
		 
		this.context=new SpringApplicationBuilder().sources(BootifulJavaFxApplication.class).initializers()
				.run(getParameters().getRaw().toArray(new String[0]));
	}
	
	
	//cuando la aplicacion esta preparada
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.context.publishEvent(new StageReadyEvent(primaryStage));
		
	}


	@Override
	public void stop() throws Exception {
		this.context.close();
		Platform.exit();
	}
}

class StageReadyEvent extends ApplicationEvent {
	
	public Stage getStage() {
		return Stage.class.cast(getSource());
	}

	public StageReadyEvent(Stage source) {
		super(source);
		// TODO Auto-generated constructor stub
	}
	
}



