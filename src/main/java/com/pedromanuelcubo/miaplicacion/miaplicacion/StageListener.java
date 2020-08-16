package com.pedromanuelcubo.miaplicacion.miaplicacion;

import java.io.IOException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;


@Component
@RestController
public class StageListener implements ApplicationListener<StageReadyEvent> {

	private final String applicationTitle;
	private final Resource fxml;
	private final ApplicationContext ac;
	
	public StageListener(@Value("${spring.application.ui.title}") String applicationTitle,
			@Value("classpath:/FXMLProyecto01.fxml") Resource resource, ApplicationContext ac) {
		this.applicationTitle=applicationTitle;
		this.fxml=resource;
		this.ac=ac;
	}
	
	
	@RequestMapping("/")
	@Override
	public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
		
		try {
			Stage stage = stageReadyEvent.getStage();
	
			URL url=this.fxml.getURL();
			
			FXMLLoader fxmloader=new FXMLLoader(url);
			
			fxmloader.setControllerFactory(new Callback<Class<?>, Object>() {
				
	
			
			
			
				@Override
				public Object call(Class<?> aClass) {
					return ac.getBean(aClass);
				}
			});
			
			Parent root=fxmloader.load();
			
			Scene scene=new Scene(root,1920,1080);
		
			
			scene.getStylesheets().add("applicationprimera.css");
			stage.setScene(scene);
			
			stage.setTitle(this.applicationTitle);
			stage.show();
			
		}catch (IOException e) {
			e.printStackTrace();
		}

		
	}

}
