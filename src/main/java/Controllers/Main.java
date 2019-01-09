package Controllers;

import Models.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    private static Stage thePrimaryStage;
    private static Scene theScene;
    private static int StageWidth = 1000;
    private static int StageHeight = 600;
    private static User CurrentUser;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //load the landing-view
        Parent root = FXMLLoader.load(getClass().getResource("../Views/Inloggen.fxml"));
        
        // set the properties of the stage
        primaryStage.setTitle("Corendon Luggage Hero");
        Scene newScene = new Scene(root, StageWidth, StageHeight);
        theScene = newScene;
        primaryStage.setScene(newScene);
        primaryStage.show();
        thePrimaryStage = primaryStage;
    }

    public static void GoToScreen(String name) throws IOException {
        // get the current sizes so the scene can be the same size again
        StageWidth = (int) theScene.getWidth();
        StageHeight = (int) theScene.getHeight();
        
        //load the new scene in
        Parent root = FXMLLoader.load(Main.class.getResource("../Views/" + name));
        
        // store the scene to use its properties later again
        Scene newScene = new Scene(root, StageWidth, StageHeight);
        theScene = newScene;
        
        // set the properties of the stage
        thePrimaryStage.setTitle("Corendon Luggage Hero");
        thePrimaryStage.setScene(newScene);      
        thePrimaryStage.show();
    }

    public static void setCurrentUser(User NewUser) {
        CurrentUser = NewUser;
    }

    public static User getCurrentUser() {
        return CurrentUser;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
