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
    @OneToMany(fetch=FetchType.LAZY, mappedBy = "cargo")
    private List<RoutePoint> routePoints;
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="pickup")
    private RoutePoint pickup;
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="unload")
    private RoutePoint unload;
    @OneToMany(fetch=FetchType.LAZY, mappedBy = "cargo")
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

    public List<RoutePoint> getRoutePoints() {
        return routePoints;
    }

    public void setRoutePoints(List<RoutePoint> routePoints) {
        this.routePoints = routePoints;
    }

    public List<CargoStatusLog> getStatusLogs() {
        return statusLogs;
    }

    public void setStatusLogs(List<CargoStatusLog> statusLogs) {
        this.statusLogs = statusLogs;
    }
}
