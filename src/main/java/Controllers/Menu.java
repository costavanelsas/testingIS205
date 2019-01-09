package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 *
 * @author Simon displayen van alle top-level screens
 */
public class Menu implements Initializable {

    @FXML
    private Button UsersButton;
    @FXML
    private Button ExcelButton;
    @FXML
    private Button StatisticsButton;

    /**
     * decide if user should be able to see statistics and users 
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!Main.getCurrentUser().getRole().equals("Manager")) {
            UsersButton.setVisible(false);
            StatisticsButton.setVisible(false);
        }
    }

    @FXML
    public void GoToLaf(ActionEvent event) throws IOException {
        Main.GoToScreen("LostAndFound.fxml");
    }

    @FXML
    public void GoToStats(ActionEvent event) throws IOException {
        Main.GoToScreen("Statistics.fxml");
    }

    @FXML
    public void GoToUsers(ActionEvent event) throws IOException {
        Main.GoToScreen("Users.fxml");
    }

    @FXML
    public void GoToImport(ActionEvent event) throws IOException {
        Main.GoToScreen("ImportExcel.fxml");
    }
}
