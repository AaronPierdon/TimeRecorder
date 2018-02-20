/**
 * Developer:       Aaron Pierdon
 * 
 * Description:     Represents a text file. Contains the contents, line for line
 *                  in the contents field. location poitns to where the file is 
 *                  located.
 * 
 * Date:            9/13/2017
 */

package utility.fileUtility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import utility.stringUtility.WordList;


 public class TextFile implements Serializable{
	
	protected String type;		//the type of file, like .txt or .bin
	protected String location;	//where the file is located
	protected ArrayList<String> contents = new ArrayList<>();

        public TextFile(){
            this.type = "TextFile";
        }
	public TextFile(String name){
		if(name.contains(".txt"))
			type = ".txt";
		else
			type =  ".txt";
		try{    
                        String string = name.concat(type);
			File file = new File(string);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
                        String tempString;
                        while((tempString = br.readLine()) != null)
                            this.contents.add(tempString);

			
		}catch(Exception e){
			System.out.println("Fatal Error");
		}
                
                this.type = "TextFile";
			
	}
        
        
        public void printContents(){
            for(String string : this.contents)
                System.out.println(string);
        }

        

      


    
}
