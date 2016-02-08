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
    @Column(name = "loadType", nullable = false)
    private boolean loadType;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="orders_id", nullable = false)
    private Order order;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="city_id", nullable = false)
    private City city;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="cargo_id", nullable = false)
    private Cargo cargo;

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

    public boolean isLoadType() {
        return loadType;
    }

    public void setLoadType(boolean loadType) {
        this.loadType = loadType;
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

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

}
