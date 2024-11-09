package cz.muni.fi.pv168.project.business.model;

import cz.muni.fi.pv168.project.business.model.abstracts.Entity;

public class User extends Entity {
    private final String username;
    private final Long Id;

    public User(String username, Long id) {
        super(id);
        this.username = username;
        this.Id = id;
    }

    public Long getId(){
        return this.Id;
    }

    public String getUsername(){
        return this.username;
    }
}

