package com.pirmas.laboratorinis.DataStructures;

import javafx.scene.control.TreeItem;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Folder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String folderName;
    private String folderDesc;
    private LocalDate dateCreated;
    private LocalDate dateCompleted;
    private LocalDate deploymentDate;
    @OneToMany(mappedBy = "parentFolder", cascade = {/*CascadeType.PERSIST,*/ CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    @OrderBy("id ASC")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Folder> subFolders;
    @ManyToOne
    private Folder parentFolder;
    @ManyToOne
    private Person responsible;
    @ManyToOne
    private User creator;
    @ManyToOne
    private Course course;

    public Folder(String folderName, String folderDesc, LocalDate dateCreated, LocalDate dateCompleted, LocalDate deploymentDate, ArrayList<Folder> subFolders, Person responsible, User creator) {
        this.folderName = folderName;
        this.folderDesc = folderDesc;
        this.dateCreated = dateCreated;
        this.dateCompleted = dateCompleted;
        this.deploymentDate = deploymentDate;
        this.subFolders = subFolders;
        this.responsible = responsible;
        this.creator = creator;
    }

    public Folder(String folderName, Person responsible, User creator) {
        this.folderName = folderName;
        //this.folderDesc = folderDesc;
        this.responsible = responsible;
        this.creator = creator;
        this.dateCreated = LocalDate.now();
        //this.course=
    }

    public Folder() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String taskName) {
        this.folderName = taskName;
    }

    public String getFolderDesc() {
        return folderDesc;
    }

    public void setFolderDesc(String taskDesc) {
        this.folderDesc = taskDesc;
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

    public List<Folder> getSubFolders() {
        return subFolders;
    }

    public void setSubFolders(List<Folder> subtasks) {
        this.subFolders = subtasks;
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

    public Folder getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(Folder parentFolder) {
        this.parentFolder = parentFolder;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Folder> getEverySubfolder(Folder folder){
        List<Folder> result = new ArrayList<>();
        result.add(folder);
        for (Folder subFolder : folder.getSubFolders()) {
            if(subFolder.getId()==subFolder.getParentFolder().getId()){
                continue;
            }
            result.addAll(getEverySubfolder(subFolder));
        }
        return result;
    }
}
