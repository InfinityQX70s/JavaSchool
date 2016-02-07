package com.jschool.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by infinity on 06.02.16.
 */
@Entity
@NamedQueries({
        @NamedQuery(name="Orders.findByNumber",
                query="SELECT o FROM OrdersEntity o WHERE o.number = :number"),
        @NamedQuery(name="Orders.findAllByState",
                query="SELECT o FROM OrdersEntity o WHERE o.doneState = :doneState"),
        @NamedQuery(name="Orders.findAll",
                query="SELECT o FROM OrdersEntity o")
})
@Table(name = "Orders", schema = "logiweb")
public class OrdersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idOrders", unique = true, nullable = false)
    private int id;
    @Column(name = "number", unique = true, nullable = false)
    private int number;
    @Column(name = "doneState", nullable = false)
    private boolean doneState;
    @OneToMany(fetch=FetchType.LAZY, mappedBy = "order")
    private List<RoutePointEntity> routePoints;
    @OneToMany(fetch=FetchType.LAZY, mappedBy = "order")
    private List<DriverEntity> drivers;
    @OneToOne(optional=false)
    @JoinColumn(name="truck_id", nullable=false)
    private TruckEntity truck;

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

    public List<RoutePointEntity> getRoutePoints() {
        return routePoints;
    }

    public void setRoutePoints(List<RoutePointEntity> routePoints) {
        this.routePoints = routePoints;
    }

    public List<DriverEntity> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<DriverEntity> drivers) {
        this.drivers = drivers;
    }

    public TruckEntity getTruck() {
        return truck;
    }

    public void setTruck(TruckEntity truck) {
        this.truck = truck;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrdersEntity that = (OrdersEntity) o;

        if (id != that.id) return false;
        if (number != that.number) return false;
        if (doneState != that.doneState) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + number;
        return result;
    }
}
