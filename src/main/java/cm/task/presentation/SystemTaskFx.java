package cm.task.presentation;

import cm.task.TaskApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public class SystemTaskFx extends Application {

    private ConfigurableApplicationContext applicationContext;

//    public static void main(String[] args) {
//        launch(args);
//    }

    @Override
    public void init(){
        this.applicationContext = new SpringApplicationBuilder(TaskApplication.class).run();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(TaskApplication.class.getResource("/templates/index.fxml"));
        loader.setControllerFactory(applicationContext::getBean); //Esta es la línea más importante ya que vamos obtener toddos los objetos de Spring
                                                                  //y los vamos a cargar dentro de JavaFx
        //Con este objeto loader podemos cargar todos los objetos de Spring
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop(){
        applicationContext.close(); //Que se cierre cualquier conexión que tuvieramos, pr.ej: base de datos
        Platform.exit(); //Cerramos la aplicación de JavaFx
    }
}
