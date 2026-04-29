package com.lostfound.demo.models;

/**
 * Data class representing a user in the Lost & Found system
 * @author Nguyen The Minh Duc
 */

public class User {
    private String id;
    private String name;
    private String email;
    private String role;

    public User(String id, String name, String email, String role){
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }
     public String getId() {
        return id;
    }
     public String getName() {
        return name;
    }
     public String getEmail() {
        return email;
    }
     public String getRole() {
        return role;
    }
    
     @Override
    public String toString() {
        return "User{id='" + id + "', name='" + name + "', email='" + email + "', role='" + role + "'}";
    }
}
