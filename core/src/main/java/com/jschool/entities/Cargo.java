package com.jschool.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by infinity on 06.02.16.
 */
@Entity
@NamedQueries({
        @NamedQuery(name="Cargo.findByNumber",
                query="SELECT c FROM Cargo c WHERE c.number = :number"),
        @NamedQuery(name="Cargo.findByName",
                query="SELECT c FROM Cargo c WHERE c.name = :name"),
        @NamedQuery(name="Cargo.findAll",
                query="SELECT c FROM Cargo c")
})
@Table(name = "Cargo", schema = "logiweb")
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCargo", unique = true, nullable = false)
    private int id;
    @Column(name = "number", unique = true, nullable = false)
    private int number;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "weight", nullable = false)
    private int weight;
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="pickup")
    private RoutePoint pickup;
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="unload")
    private RoutePoint unload;
    @OneToMany(fetch=FetchType.EAGER, mappedBy = "cargo", cascade = CascadeType.ALL)
    private List<CargoStatusLog> statusLogs;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<CargoStatusLog> getStatusLogs() {
        return statusLogs;
    }

    public void setStatusLogs(List<CargoStatusLog> statusLogs) {
        this.statusLogs = statusLogs;
    }

    public RoutePoint getPickup() {
        return pickup;
    }

    public void setPickup(RoutePoint pickup) {
        this.pickup = pickup;
    }

    public RoutePoint getUnload() {
        return unload;
    }

    public void setUnload(RoutePoint unload) {
        this.unload = unload;
    }
}
