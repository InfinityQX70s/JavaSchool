package com.jschool.entities;

import javax.persistence.*;

/**
 * Created by infinity on 06.02.16.
 */
@Entity
@Table(name = "RoutePoint", schema = "logiweb")
public class RoutePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRoute", unique = true, nullable = false)
    private int id;
    @Column(name = "point", nullable = false)
    private int point;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="orders_id", nullable = false)
    private Order order;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="city_id", nullable = false)
    private City city;
    @OneToOne(mappedBy="pickup")
    private Cargo pickup;
    @OneToOne(mappedBy="unload")
    private Cargo unload;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Cargo getPickup() {
        return pickup;
    }

    public void setPickup(Cargo pickup) {
        this.pickup = pickup;
    }

    public Cargo getUnload() {
        return unload;
    }

    public void setUnload(Cargo unload) {
        this.unload = unload;
    }
}
