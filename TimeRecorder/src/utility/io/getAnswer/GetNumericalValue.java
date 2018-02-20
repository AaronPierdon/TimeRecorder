/**
 * Developer:       Aaron Pierdon
 * 
 * Description:     this class is to be called by using the upper-case
 *                  name GetBooleanAnswer. There is only one static method
 *                  to be called. It takes a value of type object
 *                  and parses it for Integer, Float, Double, getting the value
 *                  from the user and returning an object casted to the type
 *                  of object received (variable).
 *              
 *                  .getNumericalValue's return type from the calling code should have a 
 *                  cast when assigning a value.
 * 
 *                  Example: int a = 
 *                           (int) new GetNumbericalValue.getNumericalValue(a);
 * 
 * Date:            9/13/2017
 */




package utility.io.getAnswer;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class GetNumericalValue {
    

    
    public static Object getNumericalValue(Object variable){
        


        System.out.println(variable.getClass().getName());
        //INTEGER
        if(variable.getClass().getName().contains("Integer"))
        {
            
            int temp = 0;
            


            while(temp == 0){

              try{
                  System.out.println("Please input an integer: ");
                  variable = new Scanner(System.in).nextInt();
                  temp = (Integer) variable;
              }catch(InputMismatchException e){

                      System.out.println("That was not a valid input.");
                    
                
               }
                          
            }  
            

       
                
            
            
            
            
            return (Integer) variable;
        }
        
        //DOUBLE
        if(variable.getClass().getName().contains("Double"))
        {
            
            double temp = 0;
            


            while(temp == 0){

              try{
                  System.out.println("Please input a double value: ");
                  variable = new Scanner(System.in).nextDouble();
                  temp = (Double) variable;
              }catch(InputMismatchException e){

                      System.out.println("That was not a valid input.");
                    
                
               }
                          
            }  
            

       
                
            
            
            
            
            return (Double) variable;
        }
        
        //FLOAT
        if(variable.getClass().getName().contains("Float"))
        {
            
            Float temp = new Float(0);
            


            while(temp == 0){

              try{
                  System.out.println("Please input a float value: ");
                  variable = new Scanner(System.in).nextFloat();
                  temp = (Float) variable;
              }catch(InputMismatchException e){

                      System.out.println("That was not a valid input.");
                    
                
               }
                          
            }  
            

       
                
            
            
            
            
            return (Float) variable;
        }
        
        //LONG
        if(variable.getClass().getName().contains("Long"))
        {
            
            Long temp = new Long(0);
            


            while(temp == 0){

              try{
                  System.out.println("Please input a long value: ");
                  variable = new Scanner(System.in).nextLong();
                  temp = (Long) variable;
              }catch(InputMismatchException e){

                      System.out.println("That was not a valid input.");
                    
                
               }
                          
            }  
            

       
                
            
            
            
            
            return (Long) variable;
        }
        
        
        return variable;
    }
}
