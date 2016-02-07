package com.jschool.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by infinity on 06.02.16.
 */
@Entity
@NamedQueries({
        @NamedQuery(name="City.findByName",
                query="SELECT c FROM CityEntity c WHERE c.name = :name"),
        @NamedQuery(name="City.findAll",
                query="SELECT c FROM CityEntity c")
})
@Table(name = "City", schema = "logiweb")
public class CityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCity", unique = true, nullable = false)
    private int id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @OneToMany(fetch=FetchType.LAZY, mappedBy = "city")
    private List<RoutePointEntity> routePoints;

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

    public List<RoutePointEntity> getRoutePoints() {
        return routePoints;
    }

    public void setRoutePoints(List<RoutePointEntity> routePoints) {
        this.routePoints = routePoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CityEntity that = (CityEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
