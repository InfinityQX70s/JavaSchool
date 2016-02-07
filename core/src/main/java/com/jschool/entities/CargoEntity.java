package com.jschool.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by infinity on 06.02.16.
 */
@Entity
@NamedQueries({
        @NamedQuery(name="Cargo.findByNumber",
                query="SELECT c FROM CargoEntity c WHERE c.number = :number"),
        @NamedQuery(name="Cargo.findByName",
                query="SELECT c FROM CargoEntity c WHERE c.name = :name"),
        @NamedQuery(name="Cargo.findAll",
                query="SELECT c FROM CargoEntity c")
})
@Table(name = "Cargo", schema = "logiweb")
public class CargoEntity {
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
    private List<RoutePointEntity> routePoints;
    @OneToMany(fetch=FetchType.LAZY, mappedBy = "cargo")
    private List<CargoStatusLogEntity> statuses;

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

    public List<RoutePointEntity> getRoutePoints() {
        return routePoints;
    }

    public void setRoutePoints(List<RoutePointEntity> routePoints) {
        this.routePoints = routePoints;
    }

    public List<CargoStatusLogEntity> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<CargoStatusLogEntity> statuses) {
        this.statuses = statuses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CargoEntity that = (CargoEntity) o;
        if (id != that.id) return false;
        if (number != that.number) return false;
        if (weight != that.weight) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + number;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + weight;
        return result;
    }
}
