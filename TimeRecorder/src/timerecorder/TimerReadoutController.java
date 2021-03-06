/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timerecorder;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import utility.io.parse.LongToReadableTime;


public class TimerReadoutController{
    

    
    // Concurrent Tasks
    private TimeCounter timeCounter;
    
    
    @FXML private VBox rootReadout;
    @FXML private StackPane readoutPane;
    @FXML private Label lblTimerReadout;
    @FXML private StackPane panePins;
    @FXML private Button asd;
    
    private boolean running;
    private boolean alwaysOnTopChange;

    public void setAlwaysOnTopChange(boolean alwaysOnTopChange) {
        this.alwaysOnTopChange = alwaysOnTopChange;
    }

    public boolean isAlwaysOnTopChange() {
        return alwaysOnTopChange;
    }
    
    private ColorChanger colorChanger;
    private float opacity;
    private boolean opacityAdding;
    
    
    public void setRunning(boolean running) {
        this.alwaysOnTopChange = false;
        this.running = running;
    }

    public boolean isRunning() {
        return running;
    }
    
    
    public TimerReadoutController(){
        running = false;
        this.alwaysOnTopChange = false;
        this.colorChanger = new ColorChanger();
        this.timeCounter = new TimeCounter();
        opacity = (float) new Float(0.6);
        

    }
    

    
    protected long getTimerDuration(){
        
        return this.timeCounter.totalTime;
         
    }
    

    
    protected void startTimers(){
        this.timeCounter.startTimer();
        running = true;
    }


    
    

    

    @FXML
    protected void stopTimer(ActionEvent event){
        
        this.running = false;
        this.cancelTimer();
    }
    
    @FXML
    protected void alwaysOnTop(MouseEvent event){
            this.alwaysOnTopChange = true;
    }
        
    public void cancelTimer(){
        if(running == true){
            this.timeCounter.cancelTimer();

        }

        
        
        
    }
    
    protected void buildReadoutView(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TimerReadout.fxml"));
        
        loader.setController(this);
        
        try{
           this.rootReadout = loader.load();

        }catch(IOException e){
            System.out.println("failed");
        }
       
          
    }
    
    protected VBox getReadoutView(){
        if(this.rootReadout != null)
            return this.rootReadout;
        else{
            buildReadoutView();
            return this.rootReadout;
        }
    }
    
    protected void setReadoutStyle(String inStyle){
        this.rootReadout.setStyle(inStyle);
    }
    
    protected void updateReadoutView(){
        // how will u make the readout updated in reponse to color changes.u
        
        if(opacityAdding == true){
            this.opacity += 0.01;
            if(opacity > .9)
                opacityAdding = false;
        }
            
        
        
        
        if(opacityAdding == false){
            opacity -= 0.01;
            if(opacity < 0.6)
                opacityAdding = true;
        }
            


        rootReadout.setStyle(colorChanger.getColorSequence());
        lblTimerReadout.setOpacity(opacity);
        lblTimerReadout.setText(LongToReadableTime.getReadableTime(this.timeCounter.getTotalTime()));
        
    
        
    }
    

}
