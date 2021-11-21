package com.pirmas.laboratorinis.DataStructures;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String taskName;
    private String taskDesc;
    private LocalDate dateCreated;
    private LocalDate dateCompleted;
    private LocalDate deploymentDate;
    @OneToMany(mappedBy = "parentTask", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    @OrderBy("id ASC")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Task> subtasks;
    @ManyToOne
    private Task parentTask;
    @ManyToOne
    private Person responsible;
    @ManyToOne
    private User creator;
    @ManyToOne
    private Course course;

    public Task(String taskName, String taskDesc, LocalDate dateCreated, LocalDate dateCompleted, LocalDate deploymentDate, ArrayList<Task> subtasks, Person responsible, User creator) {
        //this.id = id;
        this.taskName = taskName;
        this.taskDesc = taskDesc;
        this.dateCreated = dateCreated;
        this.dateCompleted = dateCompleted;
        this.deploymentDate = deploymentDate;
        this.subtasks = subtasks;
        this.responsible = responsible;
        this.creator = creator;
    }

    public Task(String taskName, String taskDesc, Person responsible, User creator) {
        this.taskName = taskName;
        this.taskDesc = taskDesc;
        this.responsible = responsible;
        this.creator = creator;
        this.dateCreated = LocalDate.now();
    }

    public Task() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(LocalDate dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public LocalDate getDeploymentDate() {
        return deploymentDate;
    }

    public void setDeploymentDate(LocalDate deploymentDate) {
        this.deploymentDate = deploymentDate;
    }

    public List<Task> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<Task> subtasks) {
        this.subtasks = subtasks;
    }

    public Person getResponsible() {
        return responsible;
    }

    public void setResponsible(Person responsible) {
        this.responsible = responsible;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Task getParentTask() {
        return parentTask;
    }

    public void setParentTask(Task parentTask) {
        this.parentTask = parentTask;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
