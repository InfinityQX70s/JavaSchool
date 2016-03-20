package com.jschool.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by infinity on 19.03.16.
 */
@Entity
@NamedQuery(name="DriverAuthCode.findLastCode",
        query="SELECT d FROM DriverAuthCode d WHERE d.driver = :driver ORDER BY d.timestamp DESC")
@Table(name = "DriverAuthCode", schema = "logiweb")
public class DriverAuthCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDriverAuthCode", unique = true, nullable = false)
    private int id;
    @Column(name = "code", nullable = false)
    private int code;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
