/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab4;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author student
 */
public class FXMLController implements Initializable {
    @FXML private TextField pathId;
    @FXML private ImageView imageView = new ImageView();
    @FXML private ListView<String> listId = new ListView<>();
    private MySQL sql = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        Lab4.items.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change obj) {
                listId.setItems(obj.getList());
            }
        });        
        sql = new MySQL(Lab4.items);
    }   
    
    
    
    @FXML
    protected void btnFindClick(ActionEvent event) {
        String path = pathId.getText();
        File file = new File(path);
        
        if(file.length() > 0 && sql.putPhoto(file.getName(), path) == 1) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Succeed");
//            alert.setHeaderText("File inserted succesfuly");
            alert.setContentText("File inserted succesfuly");
            alert.showAndWait();
            Lab4.items.clear();
            sql.getPhotos();
        }
        else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("File not found");
            alert.setContentText("File not exist or path is wrong!");
            alert.showAndWait();
        }
            

//        int size = (int) file.length();
//        
//        try {
//            BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file), size);
//            imageView.setImage(new Image(fis));
//        } catch (FileNotFoundException ex) {
//            System.err.println(ex.getMessage());
//        }
        
    }
    
    @FXML
    protected void btnFindInDBClick(ActionEvent event) {
        Lab4.items.clear();
        sql.getPhotos();
    }
    
    @FXML
    protected void btnSelectImageClick(ActionEvent event) {
//        System.out.println(listId.getSelectionModel().getSelectedItems());
        Image img  = sql.getPhoto(listId.getSelectionModel().getSelectedItems().get(0));
        if(img != null) {
            imageView.setImage(img);
        }
    }
    
    @FXML
    protected void btnDeleteImageClick(ActionEvent event) {
        if(sql.DeletePhoto(listId.getSelectionModel().getSelectedItems().get(0)) == 1){
            Lab4.items.clear();
            sql.getPhotos();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Something goes wrong!");
            alert.setContentText("Cannot remove selected file");
            alert.showAndWait();
        }
    }
    
}
