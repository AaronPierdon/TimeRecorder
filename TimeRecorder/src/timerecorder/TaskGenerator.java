/*
 * Developers: Aaron Pierdon
 * Date: Mar 28, 2018
 * Description :
 * 
 */

package timerecorder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import timerecorderdatamodel.Task;


public class TaskGenerator {

    
    
    public ArrayList<Task> getSomeTasks(){
 
        ArrayList<Task> tasks = new ArrayList<>();
        
        int nameCounter = 0;
        
        // get tasks
        for(int counter = new Random().nextInt(10); counter >= 0; counter--){
            Task newTask = new Task(String.valueOf(nameCounter));
            nameCounter++;
            
            // get dates
            for(int counter2 = new Random().nextInt(1000); counter2 >= 0; counter2--){
                newTask.addSession(new Random().nextInt(86_400_000), getCal());
            }
            
            tasks.add(newTask);
        }
        
        return tasks;
            
    }
    
    
    private Calendar getCal(){
        
        int month; 
        int day;
        int year;
        
        month = new Random().nextInt(11);
        day = new Random().nextInt(28);
        year = 2015 + new Random().nextInt(3);
        Calendar cal = new GregorianCalendar();
        
        cal.set(year, month, day);
        
        
        return cal;
    }
    
    
}
