package com.embarkx.jobms.job.external;



public class Company {

    private Long id;
    private String description;
    private String name;



    public Company() {
    }

    public Company(Long id, String description, String name) {
        this.id = id;
        this.description = description;
        this.name = name;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
