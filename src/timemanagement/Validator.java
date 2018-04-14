/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timemanagement;

import java.text.ParseException;

/**
 *
 * @author henry
 */
public class Validator {
    private String value;
    
    public Validator(){
        this.value="";
    }
    
    public Validator(String v){
        this.setValue(v);
    }
    
    public String getValue(){
        return this.value;
    }
    
    public void setValue(String s){
        if (!isValidInteger(s)&&!isValidDouble(s)&&!isValidBoolean(s)&&!hasOnlyLetters(s)) 
            throw new IllegalArgumentException("It's not a valid input: integer, double or boolean");
        else this.value=s;
    }
    
    public boolean hasOnlyLetters(String s){
        for (int i = 0; i < s.length(); i++) 
             if(!Character.isLetter(s.charAt(i))) return false;
        return true;
    }
     
    public boolean isValidInteger(String s) {
        try{
            Integer.parseInt(s);
        } catch(Exception e){
            return false;
        }
        return true;
    }
    
    public boolean isValidDouble(String s) {
        try{
            Double.parseDouble(s);
        } catch(Exception e){
            return false;
        }
        return true;
    }
    
    public boolean isValidBoolean(String s) {
        if (s.toLowerCase()=="true" || s.toLowerCase()=="false") return true;
        return false;
    }
    
    public boolean isValidRange(int range, boolean min, boolean include) {
        int value=Integer.parseInt(this.value);
        if (value==range)
            return(include)? true:false;      
        else
            return ((value>range && min) || (value<range && !min))? true: false;
    }
    
    public boolean isValidRange(double range, boolean min, boolean include) {
        double value=Double.parseDouble(this.value);
        if (value==range)
            return(include)? true:false;      
        else
            return ((value>range && min) || (value<range && !min))? true: false;
    }
    
    public boolean containDelimiter(String s, char delimiter) {
        for (int i = 0; i < s.length(); i++) 
             if(s.charAt(i)==delimiter) return true;
        return false;
    }
}
