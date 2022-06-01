package com.tqs.trackit.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "job_application") 
public class Job_Application {
    @Id //The ID will be auto generated
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name", nullable = false)
    private String first_name;

    @Column(name = "last_name", nullable = false)
    private String last_name;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate date_of_birth;

    @Column(name = "phone", nullable = false)
    private Long phone;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "photo", nullable = false)
    private String photo;

    @Column(name = "cv", nullable = false)
    private String cv;



    public Job_Application() {
    }



    public Job_Application(long id, String first_name, String last_name, LocalDate date_of_birth, Long phone, String email, String photo, String cv) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_of_birth = date_of_birth;
        this.phone = phone;
        this.email = email;
        this.photo = photo;
        this.cv = cv;
    }


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return this.first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return this.last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public LocalDate getDate_of_birth() {
        return this.date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public Long getPhone() {
        return this.phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCv() {
        return this.cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Job_Application)) {
            return false;
        }
        Job_Application job_Application = (Job_Application) o;
        return id == job_Application.id && Objects.equals(first_name, job_Application.first_name) && Objects.equals(last_name, job_Application.last_name) && Objects.equals(date_of_birth, job_Application.date_of_birth) && Objects.equals(phone, job_Application.phone) && Objects.equals(email, job_Application.email) && Objects.equals(photo, job_Application.photo) && Objects.equals(cv, job_Application.cv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first_name, last_name, date_of_birth, phone, email, photo, cv);
    }


    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", first_name='" + getFirst_name() + "'" +
            ", last_name='" + getLast_name() + "'" +
            ", date_of_birth='" + getDate_of_birth() + "'" +
            ", phone='" + getPhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", cv='" + getCv() + "'" +
            "}";
    }
    

    
}
