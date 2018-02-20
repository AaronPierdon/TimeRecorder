/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timerecorder;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import utility.io.parse.LongToReadableTime;


public class TimerReadoutController extends TimerTask{
    
    @FXML protected BorderPane root;
    @FXML protected VBox rootReadout;
    @FXML protected Label lblTimerReadout;
    
    // Concurrent Tasks
    private ColorChanger colorChanger;
    private TimeCounter timeCounter;
    
    // Data Controller
    private DataController dataController;
    
    private MainController mainController;
    
    private int indexOfRunningTask;
    
    private boolean running;
    
    
    public void TimerReadoutController(){
        running = false;
        this.colorChanger = new ColorChanger();
        this.timeCounter = new TimeCounter();
    }
    
    public void startTimerTask(BorderPane root, VBox rootReadout, int indexOfRunningTask, 
            DataController dataController, MainController mainController){
        
        running = true;
        this.root = root;
        
        this.colorChanger = new ColorChanger();
        this.timeCounter = new TimeCounter();
        this.dataController = dataController;
        this.mainController = mainController;
        
        this.indexOfRunningTask = indexOfRunningTask;
        
        Timer thisTimer = new Timer();

        thisTimer.scheduleAtFixedRate(this, 0, 250);
        
        loadReadoutGUI();
        startTimers();
        
        
        
    }
    
    protected void loadReadoutGUI(){
        root.getChildren().clear();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TimerReadout.fxml"));
        loader.setController(this);
             
        try{
            VBox timerReadout = loader.load();
            
            this.rootReadout = timerReadout;
            
            lblTimerReadout.setText("00:00:00");
            root.setCenter(rootReadout);
            root.setStyle("-fx-background-color: #000000");
            
            
        }catch(IOException e){
            
        }
        

    }
    
    protected void startTimers(){
        timeCounter.startTimer(this);
        colorChanger.startTimer(this.rootReadout);
    }

    protected void updateReadoutGUI(){
        lblTimerReadout.setText(LongToReadableTime.getReadableTime(timeCounter.getTotalTime()));
    }
    
    
    protected void endTimerReadout(){
        dataController.getTaskRepo().getTasks().get(indexOfRunningTask).
            addTime(timeCounter.getTotalTime());
        
        dataController.getTaskRepo().getTasks().get(indexOfRunningTask).setLastRun(
            timeCounter.startDate);
        
   
        
        indexOfRunningTask = 0;
        
        revertFromTimerGUI();
        

    }
    
    // Passes control back to main controller by requesting a reset of the GUI
    protected void revertFromTimerGUI(){
        mainController.restoreRoot();
        
    }
    
    @Override
    public void run() {
        Platform.runLater(() -> {


        });
    }

    @FXML
    protected void stopTimer(ActionEvent event){
        endTimerReadout();
        this.cancelTimer();
    }
        
    public void cancelTimer(){
        if(running == true){
            this.colorChanger.cancelTimer();
            this.colorChanger = new ColorChanger();
            this.timeCounter.cancelTimer();
            this.timeCounter = new TimeCounter();

            this.cancel();
            
            running = false;
        }

        
        
        
    }
}
