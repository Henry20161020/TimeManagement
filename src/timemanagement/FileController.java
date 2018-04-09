/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timemanagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author henry
 */
public class FileController {
    
    
    public static ArrayList<Task> readFile(File file) throws FileNotFoundException {
        Scanner scan = new Scanner(file);
        ArrayList<Task> tasks=new ArrayList<>();
        while (scan.hasNext()){
            String[] result=(scan.nextLine()+"end|").split(Pattern.quote("|"));
            tasks.add(new Task(Integer.parseInt(result[0]),result[1],result[2],result[3],result[4],result[5],result[6],result[7]));
        }
        scan.close();
        return tasks;
    }
    
    public static void writeFile(File file, ArrayList<Task> tasks) throws FileNotFoundException {
        PrintWriter pw=new PrintWriter(file);
        for (int i = 0; i < tasks.size(); i++) {
            pw.printf("%d|%s|%s|%s|%s|%s|%s|%s|\n", 
                i,
                tasks.get(i).getTaskDescription(),
                tasks.get(i).getTaskCategory(),
                tasks.get(i).getTaskDueDate(),
                tasks.get(i).getTaskCoworker(),
                tasks.get(i).getTaskSituation(),
                tasks.get(i).getTaskFinishDate(),
                tasks.get(i).getComments()
                );
        }
        pw.close();
  
    }
    
  
    
    public static void main(String[] args) throws FileNotFoundException{
        File file=new File("test.txt");
        ArrayList<Task> tasks=new ArrayList<>();
        tasks=readFile(file);
        System.out.println(tasks);
        
    }
}
