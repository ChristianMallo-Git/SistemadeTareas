package cm.task;

import cm.task.presentation.SystemTaskFx;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskApplication {

	public static void main(String[] args) {
		Application.launch(SystemTaskFx.class, args);
	}

}
