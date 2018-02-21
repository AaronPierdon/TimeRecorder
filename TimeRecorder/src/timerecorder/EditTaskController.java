/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timerecorder;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Aaron Pierdon
 */
public class EditTaskController {

    public void editTask(int selectedTaskIndex, BorderPane root, DataController dataController){
        
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EditTask.fxml"));
        
        try{
            root.getChildren().clear();
            loader.setController(this);
            VBox editor = loader.load();
            
            root.setCenter(editor);
        }catch(IOException e){
        
        }
        
        
        
    }
        
    
}
