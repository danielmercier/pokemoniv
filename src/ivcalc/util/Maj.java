package ivcalc.util;

import ivcalc.pokemon.data.CpMultiplier;
import ivcalc.pokemon.data.LevelsByStardust;
import ivcalc.pokemon.data.Species;
import ivcalc.pokemon.data.Translation;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Maj extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Translation.load();
        Species.load();
        CpMultiplier.load();
        LevelsByStardust.load();

        Util.updateIvCalcData();

        primaryStage.setScene(new Scene(new Pane(), 1, 1));
        primaryStage.show();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mise à jour");
        alert.setHeaderText("Mise à jour terminée");
        alert.showAndWait();

        primaryStage.close();
    }
}
