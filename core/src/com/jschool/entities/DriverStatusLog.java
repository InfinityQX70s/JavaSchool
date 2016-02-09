package com.jschool.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by infinity on 06.02.16.
 */
@Entity
@NamedQuery(name="DriverStatusLog.findLastStatus",
        query="SELECT d FROM DriverStatusLog d WHERE d.driver = :driver ORDER BY d.timestamp DESC")
@Table(name = "DriverStatusLog", schema = "logiweb")
public class DriverStatusLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDriverStatusLog", unique = true, nullable = false)
    private int id;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DriverStatus status;
    @Column(name = "timestamp", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date timestamp;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="driver_id", nullable = false)
    private Driver driver;

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

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

}
