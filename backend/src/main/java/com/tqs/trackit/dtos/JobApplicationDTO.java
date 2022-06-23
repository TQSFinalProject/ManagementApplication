package com.tqs.trackit.dtos;

import com.tqs.trackit.model.JobApplication;

import java.time.LocalDate;

public class JobApplicationDTO {
    private long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phone;
    private String email;
    private String photo;
    private String cv;

    public static JobApplicationDTO fromJobApplicationEntity(JobApplication jobApplication){
        return new JobApplicationDTO(jobApplication.getFirstName(),jobApplication.getLastName(),jobApplication.getDateOfBirth(),jobApplication.getPhone(),jobApplication.getEmail(),jobApplication.getPhoto(),jobApplication.getCv(),jobApplication.getId());
    }
    public JobApplication toJobApplicationEntity(){
        return new JobApplication(getFirstName(),getLastName(),getDateOfBirth(),getPhone(),getEmail(),getPhoto(),getCv(),getId());
    }

    public JobApplicationDTO() {
    }

    public JobApplicationDTO(String firstName, String lastName, LocalDate dateOfBirth, String phone, String email, String photo, String cv, Long id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.email = email;
        this.photo = photo;
        this.cv = cv;
        this.id = id;
    }


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
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


}
