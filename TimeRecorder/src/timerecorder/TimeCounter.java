/**
*Developer:     Aaron Pierdon
*
*Description:   
*
*Date:           
*
*/

package timerecorder;

import java.util.Date;
import java.util.TimerTask;
import javafx.application.Platform;



public class TimeCounter extends TimerTask{
    
    protected Date startDate;
    protected Date lastRunDate;
    protected long totalTime;
    
    
    protected MainController controller;
    
    public void startTimer(MainController controller){
        
        this.controller = controller;
        
        //start the timer 
        java.util.Timer timer = new java.util.Timer();
        //initilize startDate
        startDate = new Date();
        
        totalTime = 0;
        
        //schedule a timer to tick every 500ms
        timer.scheduleAtFixedRate(this, 500, 200);
        //set the last run date/time to now as the timer is now running
        lastRunDate = new Date();
        lastRunDate.setTime(new Date().getTime());
        
        this.controller.startTimerGUI();

    }
    

    
    @Override
    public void run(){
        
        Platform.runLater(() -> {
            //get the time elapsed between lastRunDate and now
            long timeDifference = new Date().getTime() - lastRunDate.getTime();

            //timer readout every second

            controller.updateTimerReadout();



            //if 1 minute has elapsed, add 1 to this.totalMinutes
            if(timeDifference >= 1_000){
                this.totalTime += 1_000;

                controller.updateTimerReadout();
                //set lastRunDate to now so that the timeDifference will count 
                //from 0 to 60,000 again
                this.lastRunDate.setTime(new Date().getTime());
            }
        });
    }
    
    public long getTotalTime(){
        return this.totalTime;
    }
    
    // Cancels the timer and returns the final, total time
    public void cancelTimer(){
        
       
        this.cancel();
    }
}
