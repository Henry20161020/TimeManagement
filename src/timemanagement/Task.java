/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timemanagement;

/**
 *
 * @author henry
 */
public class Task {
    private int taskId;
    private String taskDescription;
    private String taskCategory;
    private String taskDueDate;
    private String taskCoworker;
    private String taskSituation;
    private String taskFinshDate;
    private String comments;
    
    Task(int id, String description, String category, String dueDate, String coworker, String situation, String finishDate, String comments) {
        this.taskId=id;
        this.taskDescription=description;
        this.taskCategory=category;
        this.taskDueDate=dueDate;
        this.taskCoworker=coworker;
        this.taskSituation=situation;
        this.taskFinshDate=finishDate;
        this.comments=comments;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public String getTaskCategory() {
        return taskCategory;
    }

    public String getTaskDueDate() {
        return taskDueDate;
    }

    public String getTaskCoworker() {
        return taskCoworker;
    }

    public String getTaskSituation() {
        return taskSituation;
    }

    public String getComments() {
        return comments;
    }
    
    
    public String toString() {
    
        return String.valueOf(this.taskId)+this.taskDescription+this.taskCategory+this.taskDueDate+this.taskCoworker+this.taskSituation+this.comments;
    }
    
    
}
