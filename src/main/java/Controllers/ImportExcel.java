package Controllers;

import Models.Customer;
import Models.Luggage;
import java.io.File;
import static java.lang.Integer.parseInt;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.java.Controllers.ExcelReader;

public class ImportExcel {

    private Button chooseFile;
    private Window stage;
    private String fileName;
    private final String descriptionFilter = "Excel ";
    private final String extentions = "*.xlsx";
    private ExcelReader excelReader;
    private final String error = "Bestand selecteren niet gelukt probeer opnieuw...";
    private List<List> mList = new ArrayList<>();

    @FXML
    private Label status;

    @FXML
    private void ChooseFileAction(ActionEvent event) {
        //getting string for the ExcelReader
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter((descriptionFilter + extentions), extentions)
        );

        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {

            String fileAsString = selectedFile.toString();
            excelReader = new ExcelReader(fileAsString);
            excelReader.setStartRow(5);
//            getting new string
            List<String> nextRow = excelReader.getNextRow();
            while (nextRow != null) {
                mList.add(nextRow);
                nextRow = excelReader.getNextRow();
            }

            status.setText("Bestand geupload: " + fileAsString);
        } else {
            status.setText(error);
        }
    }

    @FXML
    private void SubmitAction(ActionEvent event) throws SQLException {
        // DB Connection = new DB();

        for (int i = 0; mList.size() > i; i++) {
            for (int j = 0; j < mList.get(i).size(); j++) {
                //split a string when ther is a ", "
                String[] values = mList.get(i).get(12).toString().split(", ");
                Luggage luggage = new Luggage();
                Customer customer = new Customer();
                //set data for customer
                customer.setName(values[0]);
                customer.setCity(values[1]);
                customer.saveCustomer();
                //set data for for luggage
                luggage.setLabelNumber(mList.get(i).get(6).toString());
                luggage.setType(mList.get(i).get(3).toString());
                luggage.setBrand(mList.get(i).get(5).toString());
                luggage.setLocation(mList.get(i).get(7).toString());
                luggage.setFoundDate(mList.get(i).get(1).toString());
                luggage.setSpecialFeatures(mList.get(i).get(13).toString());
                luggage.setFlightId(mList.get(i).get(4).toString());
                luggage.setOwner(values[0]);
                luggage.setCustomerId(parseInt(customer.getLastId()));
                luggage.saveLuggage();
            }
        }
    }
}
