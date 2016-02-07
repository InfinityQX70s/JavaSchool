package com.jschool.entities;

import com.jschool.entities.status.DriverStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by infinity on 06.02.16.
 */
@Entity
@Table(name = "DriverStatusLog", schema = "logiweb")
public class DriverStatusLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDriverStatusLog", unique = true, nullable = false)
    private int id;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DriverStatus status;
    @Column(name = "timestamp", nullable = false)
    private Date timestamp;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="driver_id", nullable = false)
    private DriverEntity driver;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public void setStatus(DriverStatus status) {
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
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

        DriverStatusLogEntity that = (DriverStatusLogEntity) o;

        if (id != that.id) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }
}
