/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timerecorder;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import timerecorderdatamodel.TaskRepository;

/**
 *
 * @author Aaron
 */
public class TimeRecorder extends Application {
    
    public static final File DEFAULTDIR = new File(System.getProperty("user.home") +
            "\\documents\\Time Recorder\\");
    
    // Used for creating a file chooser
    public static Stage theStage;
    private static Scene theScene;
    
    
    private static FXMLLoader fxmlLoader;
    
    // Requests changes in views, passes control to other controllers
    private MainController timeRecorderFXMLController;
    
    // Handles updating and extracting data from the data model
    private final DataController dataController;
    
    
    // The data model
    public TaskRepository tasks;
    
    protected Closer closer;
    
    public TimeRecorder(){
        this.tasks = new TaskRepository();
        
        // Give the data model a copy of the tasks to manage
        this.dataController = new DataController(tasks);
        
        
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        theStage = stage;
        closer = new Closer();
        stage.setOnCloseRequest(closer);
        
        
        this.setMainScene();
                
        stage.show();
    }

    public  void setMainScene(){
        

        
        fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TimeRecorderMainFXML.fxml"));
        BorderPane root = new BorderPane();
       
        root.getStylesheets().add(getClass().getResource("stylesheet.css").toString());
        
            // Give the main controller a dataController so main can pass control to
        // Data Controller when the data model needs to be managed.
        timeRecorderFXMLController = new MainController(this.dataController, theStage);
        fxmlLoader.setController(timeRecorderFXMLController);
        
        try{
            root = fxmlLoader.load();
            
        }catch(IOException e){
        
        }
        
        theScene = new Scene(root, 300, 420);
        
        theStage.setScene(theScene);
    }
    


    public static void main(String[] args) {
        launch(args);
    }
    
    
    // Storage Managment
    public static File saveFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(DEFAULTDIR);
        File file = fileChooser.showSaveDialog(theStage);
        return file;
    }
    
    public static File openFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(DEFAULTDIR);
        File file = fileChooser.showOpenDialog(theStage);
        return file;
    }


   
    public class Closer implements EventHandler<WindowEvent>{

        @Override
        public void handle(WindowEvent event) {
            timeRecorderFXMLController = fxmlLoader.getController();
            timeRecorderFXMLController.cancelTasks();
            System.exit(0);
        }
        
    }
}
