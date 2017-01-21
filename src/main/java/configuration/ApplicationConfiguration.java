package configuration;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Femencha Azombo Fabrice on 1/18/2017.
 */

@Configuration
@ComponentScan("controllers")
public class ApplicationConfiguration {

    @Bean(name = "primaryStage")
    public Stage getPrimaryStage(){
        return new Stage(StageStyle.DECORATED);
    }
}
