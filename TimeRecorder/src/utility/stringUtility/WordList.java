/**
 * Developer:       Aaron Pierdon
 * 
 * Description:     Represents a string split up into words. The words are tokenized
 *                  by internal functions by a separator. Default is whitespace.
 * 
 * Date:            9/13/2017
 */
package utility.stringUtility;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;


/**
 *
 * @author Aaron Pierdon
 */
public class WordList implements Serializable{
    private String source;  //the string to be tokenized
    private ArrayList<String> wordList;     //an array to store the list of words
    transient StringTokenizer toke;   //the StringTokenizer object that tokenizes
    
    
    
    public WordList(){
        this.source = null;
        this.wordList = new ArrayList<>();
        this.toke = null;
    }
    public WordList(String source){
        this.source = source;
        this.wordList = new ArrayList<>();
        this.toke = new StringTokenizer(this.source, " ");
        this.tokenizeSource();
    }
    
    public WordList(String source, char seperator){
        this.source = source;
        this.wordList = new ArrayList<>();
        this.toke = new StringTokenizer(this.source, String.valueOf(seperator));
        this.tokenizeSource();
    }
    
    public WordList(String source, String seperator){
        this.source = source;
        this.wordList = new ArrayList<>();
        this.toke = new StringTokenizer(this.source, seperator);
        this.tokenizeSource();
    }
    
    //this is the method that splits the source string into tokens
    
    private void tokenizeSource(){
        while(this.toke.hasMoreTokens()){
            this.wordList.add(this.toke.nextToken());
        }
    }
    
    public void tokenizeSource(String source){
        this.source = source;
        this.toke = new StringTokenizer(this.source, " ");
        this.tokenizeSource();
    }
    
    public void tokenizeSource(String source, char seperator){
        this.source = source;
        this.toke = new StringTokenizer(this.source, String.valueOf(seperator));
        this.tokenizeSource();
    }
    
    public void tokenizeSource(String source, String seperator){
        this.source = source;
        this.toke = new StringTokenizer(this.source, seperator);
        this.tokenizeSource();
    }
    
    
    
    public void buildList(char seperator){
        
        
           
            String sep = String.valueOf(seperator);
            
            if(this.source != null){
                int exitValue = 1;
                String tempString = this.source;
                ArrayList<String> tempWordList = new ArrayList<>(); 




                 while(tempString.contains(sep) && exitValue == 1){




                    int temp = 1;
        
                    int indexOfLastFound = tempString.indexOf(seperator);
                    String sWhitespace = " ";
                    char charWhitespace = sWhitespace.charAt(0);

                    try{
                        
                     

                        
                        //remove seperator
                        if(tempString.charAt(0) == seperator)
                            tempString = tempString.substring(1, tempString.length());
                        //remove whitespace
                        if(tempString.charAt(0) == charWhitespace)
                            tempString = tempString.substring(1, tempString.length());

                        
                        if(tempString.contains(sep)){
                            tempWordList.add(tempString.substring(0,
                                    tempString.indexOf(sep)));
                            tempString = tempString.substring(tempString.indexOf(sep),
                                    tempString.length());
 
                        }
                        else{
                            tempWordList.add(tempString.substring(0, tempString.length()));
                            tempString = tempString.substring(0, tempString.length());
                            exitValue = 0;

                        }



                    }catch(IndexOutOfBoundsException e){}
                }       
                 

                
                
                 this.wordList = tempWordList;
                 
                 this.removeGarbage(seperator);
                 

           
    }

            
    }
    
    public void removeGarbage(char seperator){

                String sep = String.valueOf(seperator);

                Iterator<String> iter = this.wordList.iterator();

                while(iter.hasNext()){
                    String curString = iter.next();
                    if(curString.equals(sep)){
                        iter.remove();
                    }
                }
                   
                
    }
            
          
    
    public void setSource(String source){
	this.source = source;
    }

    public void setSource(ArrayList<String> words){
        this.wordList = words;
    }

    public String getSource(){
            return this.source;
    }
    
    public void printWordList(){
        for(String string : this.wordList)
            System.out.println("Oh Lordy: " + string);
    }
    
    
    public ArrayList<String> getWordList(){
        return this.wordList;

        
   
    }
        
}



