/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timemanagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author henry
 */
public class FileController {
    
    
    public static List<Task> readFile(File file) throws FileNotFoundException {
        Scanner scan = new Scanner(file);
        List<Task> tasks=new ArrayList<>();
        while (scan.hasNext()){
            String[] result=(scan.nextLine()+"|end").split(Pattern.quote("|"));
            tasks.add(new Task(Integer.parseInt(result[0]),result[1],result[2],result[3],result[4],result[5],result[6],result[7]));
        }
        return tasks;
    }
    
  
    
    public static void main(String[] args) throws FileNotFoundException{
        File file=new File("test.txt");
        List<Task> tasks=new ArrayList<>();
        tasks=readFile(file);
        for (Task task : tasks) System.out.println(task);
        
    }
}