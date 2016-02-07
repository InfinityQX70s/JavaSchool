package com.jschool.entities;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by infinity on 06.02.16.
 */
@Entity
@Table(name = "DriverStatistic", schema = "logiweb")
public class DriverStatisticEntity {
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
    private DriverEntity driver;

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

    public DriverEntity getDriver() {
        return driver;
    }

    public void setDriver(DriverEntity driver) {
        this.driver = driver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DriverStatisticEntity that = (DriverStatisticEntity) o;

        if (id != that.id) return false;
        if (hoursWorked != that.hoursWorked) return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + hoursWorked;
        return result;
    }
}
