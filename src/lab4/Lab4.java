package lab4;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Lab4 extends Application {
    
    public static ObservableList<String> items = FXCollections.observableArrayList();
    
    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        try {        
            root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(Lab4.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Simple Image Saver");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
