package com.jschool.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by infinity on 06.02.16.
 */
@Entity
@NamedQueries({
        @NamedQuery(name="City.findByName",
                query="SELECT c FROM City c WHERE c.name = :name"),
        @NamedQuery(name="City.findByNameLike",
                query="SELECT c FROM City c WHERE c.name like :name"),
        @NamedQuery(name="City.findAll",
                query="SELECT c FROM City c")
})
@Table(name = "City", schema = "logiweb")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCity", unique = true, nullable = false)
    private int id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @OneToMany(fetch=FetchType.LAZY, mappedBy = "city")
    private List<RoutePoint> routePoints;
    @OneToMany(fetch=FetchType.LAZY, mappedBy = "city")
    private List<Truck> trucks;
    @OneToMany(fetch=FetchType.LAZY, mappedBy = "city")
    private List<Driver> drivers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RoutePoint> getRoutePoints() {
        return routePoints;
    }

    public void setRoutePoints(List<RoutePoint> routePoints) {
        this.routePoints = routePoints;
    }

    public List<Truck> getTrucks() {
        return trucks;
    }

    public void setTrucks(List<Truck> trucks) {
        this.trucks = trucks;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }
}
