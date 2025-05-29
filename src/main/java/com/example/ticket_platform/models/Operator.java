package com.example.ticket_platform.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Operator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    
    @Column(nullable = false)
    private boolean activityStatus = true;  // Default true, stato dell'operatore (attivo)

    // Area di specializzazione dell'operatore (es. "Supporto Tecnico", "Assistenza Clienti")
    @Column(nullable = true)
    private String specialization;

   
    @Column(nullable = true)
    private String department;
    
     // Relazione OneToOne con User (Ogni Operator è associato a un User)
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    
    // Relazione Many-to-Many con Department (un operatore può appartenere a più dipartimenti)
    @ManyToMany
    @JoinTable(
        name = "operator_department",
        joinColumns = @JoinColumn(name = "operator_id"),
        inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    private List<Department> departments; 


    // Getter e Setter

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(boolean activityStatus) {
        this.activityStatus = activityStatus;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

}




