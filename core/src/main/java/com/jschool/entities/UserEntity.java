package com.jschool.entities;

import javax.persistence.*;

/**
 * Created by infinity on 06.02.16.
 */
@Entity
@NamedQueries({
        @NamedQuery(name="User.findByRole",
                query="SELECT u FROM UserEntity u WHERE u.role = :role"),
        @NamedQuery(name="User.findByEmail",
                query="SELECT u FROM UserEntity u WHERE u.email = :email"),
        @NamedQuery(name="User.findAll",
                query="SELECT u FROM UserEntity u")
})
@Table(name = "User", schema = "logiweb")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser", unique = true, nullable = false)
    private int id;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "role", nullable = false)
    private boolean role;
    @OneToOne(mappedBy="user")
    private DriverEntity driver;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }

    public DriverEntity getDriver() {
        return driver;
    }

    public void setDriver(DriverEntity driver) {
        this.driver = driver;
    }

}
