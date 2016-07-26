package ivcalc;

import ivcalc.calculator.IVCalc;
import ivcalc.pokemon.data.CpMultiplier;
import ivcalc.pokemon.data.LevelsByStardust;
import ivcalc.pokemon.data.Species;
import ivcalc.pokemon.data.Translation;
import ivcalc.userdata.*;
import ivcalc.userdata.Pokemon;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ivcalc.pokemon.*;

import java.io.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Translation.load();
        Species.load();
        CpMultiplier.load();
        LevelsByStardust.load();

        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream("pokemoniv.fxml"));
        Controller controller = loader.getController();
        controller.init();
        primaryStage.setTitle("IV Calculator");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
