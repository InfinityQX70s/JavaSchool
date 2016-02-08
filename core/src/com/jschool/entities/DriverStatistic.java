package com.jschool.entities;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by infinity on 06.02.16.
 */
@Entity
@Table(name = "DriverStatistic", schema = "logiweb")
public class DriverStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDriverStatistic", unique = true, nullable = false)
    private int id;
    @Column(name = "timestamp", nullable = false)
    private Date timestamp;
    @Column(name = "hoursWorked", nullable = false)
    private int hoursWorked;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="driver_id", nullable = false)
    private Driver driver;

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

    public int getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

}
