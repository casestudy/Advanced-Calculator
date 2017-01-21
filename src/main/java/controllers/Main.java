package controllers;

import configuration.ApplicationConfiguration;
import configuration.SpringFXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        Stage primaryStage = applicationContext.getBean(Stage.class);

        SpringFXMLLoader<Parent, BasicCalculatorController> springFXMLLoader = SpringFXMLLoader.create()
                .applicationContext(applicationContext)
                .location(getClass().getResource("/views/basic/basic.fxml"))
                .build();

        Parent root = springFXMLLoader.load();

        BasicCalculatorController basicCalculatorController = springFXMLLoader.getController() ;

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
