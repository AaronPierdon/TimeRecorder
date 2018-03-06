/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timerecorder;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;


// Color Changer
public class ColorChanger{



    private int red;
    private int green;
    private int blue;
    private boolean up;
    private String style;



    public ColorChanger(){
        red = 58;
        green = 110;
        blue = 21;
        
        up = true;
        style = "-fx-background-color: black;";
    }
    

    
    protected void shiftColors(){
            

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

            
            this.style = colorSequence;


    }
    
    protected String getColorSequence(){
        this.shiftColors();
        return this.style;
    }


}
