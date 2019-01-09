package Controllers;

import Models.Luggage;
import Models.User;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class LostAndFound implements Initializable {

    @FXML
    private TableView<Luggage> LostAndFoundTableView;

    private ObservableList<Luggage> luggageList = FXCollections.observableArrayList();

    @FXML
    private TableColumn ID;
    @FXML
    private TableColumn owner;
    @FXML
    private TableColumn brand;
    @FXML
    private TableColumn color;
    @FXML
    private TableColumn status;
    @FXML
    private TableColumn lostLocation;
    @FXML
    private TableColumn flightId;
    @FXML
    private TableColumn specialFeatures;

    @FXML
    private TextField OwnerSearchField;
    @FXML
    private TextField ColorSearchField;
    @FXML
    private TextField BrandSearchField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Luggage initLuggage = new Luggage();

        try {
            ResultSet foundLuggage;
            foundLuggage = initLuggage.getLuggage();
            while (foundLuggage.next()) {
                Luggage luggage = new Luggage();
                luggage.setID(foundLuggage.getInt("ID"));
                luggage.setOwner(foundLuggage.getString("owner"));
                luggage.setBrand(foundLuggage.getString("brand"));
                luggage.setColor(foundLuggage.getString("color"));
                luggage.setStatus(foundLuggage.getString("status"));
                luggage.setLostLocation(foundLuggage.getString("lost_location"));
                luggage.setFlightId(foundLuggage.getString("flight_id"));
                luggage.setSpecialFeatures(foundLuggage.getString("special_features"));

                luggageList.add(luggage);
            }
            LostAndFoundTableView.setItems(luggageList);
            for (int cnr = 0; cnr < LostAndFoundTableView.getColumns().size(); cnr++) {
                TableColumn tc = (TableColumn) LostAndFoundTableView.getColumns().get(cnr);
                String propertyName = tc.getId();
                if (propertyName != null && !propertyName.isEmpty()) {
                    tc.setCellValueFactory(new PropertyValueFactory<Luggage, String>(propertyName));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LostAndFound.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void goToNew(ActionEvent event) throws IOException {
        Main.GoToScreen("RegisterLuggage.fxml");
    }

    @FXML
    private void SearchByFields() {
        // define the values of the inputs
        String OwnerString = OwnerSearchField.textProperty().get().toLowerCase();
        String ColorString = ColorSearchField.textProperty().get().toLowerCase();
        String BrandString = BrandSearchField.textProperty().get().toLowerCase();

        // if the string is empty fill with original data
        if (OwnerString.length() == 0 && ColorString.length() == 0 && BrandString.length() == 0) {
            LostAndFoundTableView.setItems(luggageList);
            System.out.println("SET");
        } else {
            //create a new empty list
            ObservableList<Luggage> filteredItems = FXCollections.observableArrayList();

            // loop through rows
            for (int i = 0; i < luggageList.size(); i++) {

                //get the cells to filter on
                String ownerCell = owner.getCellData(luggageList.get(i)).toString().toLowerCase();
                String colorCell = color.getCellData(luggageList.get(i)).toString().toLowerCase();
                String brandCell = brand.getCellData(luggageList.get(i)).toString().toLowerCase();

                if (ownerCell.contains(OwnerString) && colorCell.contains(ColorString) && brandCell.contains(BrandString)) {
                    filteredItems.add(luggageList.get(i));
                }
                LostAndFoundTableView.setItems(filteredItems);
            }
        }
   
    }
    
    @FXML
    private void GoToLuggageDetail(ActionEvent event) throws IOException{
        Luggage selectedLuggage = (Luggage) LostAndFoundTableView.getSelectionModel().getSelectedItem();
        int luggageIdToView = selectedLuggage.getID();
        LuggageDetailController.setLuggageViewId(luggageIdToView);
        Main.GoToScreen("LuggageDetail.fxml");
    }
    
    @FXML
    private void EditLuggage(ActionEvent event) throws IOException{
        Luggage selectedLuggage = (Luggage) LostAndFoundTableView.getSelectionModel().getSelectedItem();
        int luggageIdToEdit = selectedLuggage.getID();
        LostLuggage.setCurrentLuggageId(luggageIdToEdit);
        Main.GoToScreen("LostLuggage.fxml");
    }
}
