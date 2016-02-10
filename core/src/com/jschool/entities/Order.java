package com.jschool.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by infinity on 06.02.16.
 */
@Entity
@NamedQueries({
        @NamedQuery(name="Orders.findByNumber",
                query="SELECT o FROM Order o WHERE o.number = :number"),
        @NamedQuery(name="Orders.findAllByState",
                query="SELECT o FROM Order o WHERE o.doneState = :doneState"),
        @NamedQuery(name="Orders.findAll",
                query="SELECT o FROM Order o")
})
@Table(name = "Orders", schema = "logiweb")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idOrders", unique = true, nullable = false)
    private int id;
    @Column(name = "number", unique = true, nullable = false)
    private int number;
    @Column(name = "doneState", nullable = false)
    private boolean doneState;
    @OneToMany(fetch=FetchType.LAZY, mappedBy = "order")
    private List<RoutePoint> routePoints;
    @OneToMany(fetch=FetchType.LAZY, mappedBy = "order")
    private List<Driver> drivers;
    @OneToOne()
    @JoinColumn(name="truck_id")
    private Truck truck;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDoneState() {
        return doneState;
    }

    public void setDoneState(boolean doneState) {
        this.doneState = doneState;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<RoutePoint> getRoutePoints() {
        return routePoints;
    }

    public void setRoutePoints(List<RoutePoint> routePoints) {
        this.routePoints = routePoints;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

}
