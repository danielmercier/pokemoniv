package ivcalc;

import ivcalc.calculator.IVCalc;
import ivcalc.pokemon.Iv;
import ivcalc.userdata.PokeCollection;
import ivcalc.userdata.Pokemon;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ivcalc.pokemon.data.Species;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.DecimalFormat;
import java.text.Normalizer;

public class Controller {
    @FXML private ComboBox<Species> speciesComboBox;
    @FXML private TableView<Pokemon> dataTableView;
    @FXML private TableColumn<Pokemon, String> pokemonCol;
    @FXML private TableColumn<Pokemon, Integer> cpCol;
    @FXML private TableColumn<Pokemon, Integer> hpCol;
    @FXML private TableColumn<Pokemon, Integer> lvlCol;
    @FXML private TableColumn<Pokemon, Pokemon> percentCol;
    @FXML private TextField hpTextField;
    @FXML private TextField cpTextField;
    @FXML private TextField dustTextField;
    @FXML private RadioButton poweredUp;
    private PokeCollection collection;
    private ObservableList<Pokemon> observableCollection;

    private DecimalFormat percentFormatter = new DecimalFormat("#.0#");

    public void init(){
        speciesComboBox.getItems().addAll(Species.allSpecies);

        FxUtil.autoCompleteComboBoxPlus(speciesComboBox, (typedText, itemToCompare) -> {
            String normalizedTyped = Normalizer.normalize(typedText.toLowerCase(), Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
            String normalizedToCompare = Normalizer.normalize(itemToCompare.getName().toLowerCase(), Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");


            return normalizedToCompare.startsWith(normalizedTyped);
        });

        pokemonCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        cpCol.setCellValueFactory(new PropertyValueFactory<>("cp"));
        hpCol.setCellValueFactory(new PropertyValueFactory<>("hp"));
        lvlCol.setCellValueFactory(new PropertyValueFactory<>("dust"));
        percentCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        percentCol.setCellFactory(column -> new TableCell<Pokemon, Pokemon>(){
            @Override
            protected void updateItem(Pokemon pokemon, boolean empty){
                super.updateItem(pokemon, empty);

                if(pokemon == null || empty){
                    setText(null);
                    setStyle("");
                } else {
                    setText(percentFormatter.format(pokemon.getAvgPerfect() * 100.) + "%");
                }
            }
        });

        collection = new PokeCollection();
        observableCollection = FXCollections.observableList(collection);
        dataTableView.setItems(observableCollection);

        ContextMenu menu = new ContextMenu();
        MenuItem item = new MenuItem("Supprimer");
        item.setOnAction(value -> {
            observableCollection.remove(dataTableView.getSelectionModel().getSelectedItem());
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
            Pokemon pokemon = new Pokemon(species,
                    cp,
                    hp,
                    dust,
                    poweredUp.isSelected());

            double d;
            int multiple_vals = 0;
            if(!pokemon.getPossibleIvs().isEmpty()){
                if(pokemon.getPossibleIvs().size() == 1){
                    Iv ivs = pokemon.getPossibleIvs().get(0);
                    d = ivs.getPerfect() * 100.;
                } else {
                    multiple_vals = pokemon.getPossibleIvs().size();
                    d = pokemon.getAvgPerfect() * 100.;
                }
            } else {
                throw new Error("Aucune valeur possible");
            }


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Force");
            double ancientPercent = -1;
            Pokemon ancientPokemon = null;
            for (Pokemon poke : observableCollection) {
                if (poke.getSpecies().getId() == species.getId()) {
                    ancientPercent = poke.getAvgPerfect() * 100.;
                    ancientPokemon = poke;
                    break;
                }
            }
            String header = "";
            if(multiple_vals > 0){
                header = multiple_vals + " possibilités trouvées\n";
                header += "Nouveau pokémon :";
                header += pokeToString(pokemon);
            } else {
                header += "Nouveau pokémon : " + percentFormatter.format(d) + "%";
            }

            if (ancientPercent != -1) {
                header += "\nAncien pokémon : " + percentFormatter.format(ancientPercent) + "%";
                header += pokeToString(ancientPokemon);
            }

            alert.setHeaderText(header);

            double finalAncientPercent = ancientPercent;
            Pokemon finalAncientData = ancientPokemon;
            alert.showAndWait().ifPresent(button -> {
                if (button == ButtonType.OK && finalAncientPercent < d) {
                    observableCollection.remove(finalAncientData);
                    observableCollection.add(pokemon);
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

    private String pokeToString(Pokemon pokemon){
        String str = "";
        if(pokemon.getPossibleIvs().size() > 1) {
            str += "\n";
            str += "\tMaximum : " + percentFormatter.format(pokemon.getMaxPerfect() * 100.) + "%\n";
            str += "\tMoyenne : " + percentFormatter.format(pokemon.getAvgPerfect() * 100.) + "%\n";
            str += "\tMinimum : " + percentFormatter.format(pokemon.getMinPerfect() * 100.) + "%\n";
        } else {
            str += pokemon.getAvgPerfect() + "\n";
        }

        return str;
    }

    @FXML
    private void handleSave(){
        collection.save();
    }
}
