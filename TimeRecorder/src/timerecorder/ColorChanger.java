/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timerecorder;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;


// Color Changer
public class ColorChanger extends TimerTask{



    private int red;
    private int green;
    private int blue;
    private boolean up;
    @FXML protected VBox rootReadout;


    public void startTimer(VBox rootReadout){

        this.rootReadout = rootReadout;

        red = 58;
        green = 110;
        blue = 21;

        up = true;


        Timer timer = new Timer();

        timer.scheduleAtFixedRate(this, 0, 60);
    }

    @Override
    public void run(){


        Platform.runLater(() -> {


            if(up == true){
                red++;
                green++;
                blue++;
            }

            if(green >= 255){
                up = false;
            }

            if(up == false){
                red--;
                green--;
                blue--;
            }

            if(green < 110){
                up = true;
            }


            String colorSequence = "-fx-background-color: RGB(" +
                    red + ", " + green + ", " + blue +")";

            
            rootReadout.setStyle(colorSequence);


        });

    }


    public void cancelTimer(){
        this.cancel();
    }
}
