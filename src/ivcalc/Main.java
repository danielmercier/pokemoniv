package ivcalc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ivcalc.pokemon.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Traduction.load(Pokemon.class.getResource("trad.csv"));
        Species.load(Pokemon.class.getResource("species.csv"));
        CpMultiplier.load(Pokemon.class.getResource("cpmultiplierbylevel.csv"));
        LevelsByStardust.load(Pokemon.class.getResource("levelsbydust.csv"));

        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream("pokemoniv.fxml"));
        Controller controller = loader.getController();
        controller.init();
        primaryStage.setTitle("IV Calculator");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
