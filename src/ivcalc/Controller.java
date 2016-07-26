package ivcalc;

import ivcalc.userdata.Data;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ivcalc.pokemon.Derive;
import ivcalc.pokemon.Species;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.DecimalFormat;

public class Controller {
    @FXML private ComboBox<Species> speciesComboBox;
    @FXML private TableView<Data> dataTableView;
    @FXML private TableColumn<Data, String> pokemonCol;
    @FXML private TableColumn<Data, String> cpCol;
    @FXML private TableColumn<Data, String> hpCol;
    @FXML private TableColumn<Data, String> dustCol;
    @FXML private TableColumn<Data, Double> percentCol;
    @FXML private TextField hpTextField;
    @FXML private TextField cpTextField;
    @FXML private TextField dustTextField;

    private DecimalFormat percentFormater = new DecimalFormat("#.0#");

    public void init(){
        speciesComboBox.getItems().addAll(Species.allSpecies);

        FxUtil.autoCompleteComboBoxPlus(speciesComboBox, (typedText, itemToCompare) ->
                itemToCompare.toString().toLowerCase().startsWith(typedText.toLowerCase()));

        pokemonCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        cpCol.setCellValueFactory(new PropertyValueFactory<>("cp"));
        hpCol.setCellValueFactory(new PropertyValueFactory<>("hp"));
        dustCol.setCellValueFactory(new PropertyValueFactory<>("dust"));
        percentCol.setCellValueFactory(new PropertyValueFactory<>("percent"));
        percentCol.setCellFactory(column -> new TableCell<Data, Double>(){
            @Override
            protected void updateItem(Double percent, boolean empty){
                super.updateItem(percent, empty);

                if(percent == null || empty){
                    setText(null);
                    setStyle("");
                } else {
                    setText(percentFormater.format(percent) + "%");
                }
            }
        });

        dataTableView.getItems().addAll(Data.load());

        ContextMenu menu = new ContextMenu();
        MenuItem item = new MenuItem("Supprimer");
        item.setOnAction(value -> {
            dataTableView.getItems().remove(dataTableView.getSelectionModel().getSelectedItem());
            handleSave();
        });
        menu.getItems().add(item);
        dataTableView.setContextMenu(menu);
    }

    @FXML
    private void handleOk(){
        Integer hp = Integer.parseInt(hpTextField.getText());
        Integer cp = Integer.parseInt(cpTextField.getText());
        Integer dust = Integer.parseInt(dustTextField.getText());
        Species species = FxUtil.getComboBoxValue(speciesComboBox);
        try {
            Derive derive = new Derive(species,
                    hp,
                    cp,
                    dust);

            double d = derive.percent() * 100;

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Force");
            double ancientPercent = -1;
            Data ancientData = null;
            for (Data data : dataTableView.getItems()) {
                if (data.getName().equals(species.toString())) {
                    ancientPercent = data.getPercent();
                    ancientData = data;
                    break;
                }
            }
            String header = "Pourcentage du nouveau pokemon : " + percentFormater.format(d) + "%";
            if (ancientPercent != -1) {
                header += "\nPourcentage de l'ancien pokemon : " + percentFormater.format(ancientPercent) + "%";
            }
            alert.setHeaderText(header);

            double finalAncientPercent = ancientPercent;
            Data finalAncientData = ancientData;
            alert.showAndWait().ifPresent(button -> {
                if (button == ButtonType.OK && finalAncientPercent < d) {
                    dataTableView.getItems().remove(finalAncientData);
                    dataTableView.getItems().add(new Data(species.toString(), cp, hp, dust, d));
                    handleSave();
                }
            });
        } catch (Error e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    private void handleSave(){
        Data.save(dataTableView.getItems());
    }
}
