package com.jschool.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by infinity on 26.03.16.
 */
@Entity
@NamedQuery(name = "TruckStatistic.findAllByOneMonth",
        query = "SELECT t FROM TruckStatistic t WHERE t.truck = :truck AND t.timestamp >= :endDate")
@Table(name = "TruckStatistic", schema = "logiweb")
public class TruckStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTruckStatistic", unique = true, nullable = false)
    private int id;
    @Column(name = "timestamp", nullable = false)
    @Temporal(value = TemporalType.DATE)
    private Date timestamp;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "truck_id", nullable = false)
    private Truck truck;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }
}
