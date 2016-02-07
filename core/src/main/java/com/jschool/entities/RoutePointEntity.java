package com.jschool.entities;

import javax.persistence.*;

/**
 * Created by infinity on 06.02.16.
 */
@Entity
@Table(name = "RoutePoint", schema = "logiweb")
public class RoutePointEntity {
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
    private OrdersEntity order;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="city_id", nullable = false)
    private CityEntity city;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="cargo_id", nullable = false)
    private CargoEntity cargo;

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

    public OrdersEntity getOrder() {
        return order;
    }

    public void setOrder(OrdersEntity order) {
        this.order = order;
    }

    public CityEntity getCity() {
        return city;
    }

    public void setCity(CityEntity city) {
        this.city = city;
    }

    public CargoEntity getCargo() {
        return cargo;
    }

    public void setCargo(CargoEntity cargo) {
        this.cargo = cargo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoutePointEntity that = (RoutePointEntity) o;

        if (id != that.id) return false;
        if (point != that.point) return false;
        if (loadType != that.loadType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + point;
        return result;
    }
}
