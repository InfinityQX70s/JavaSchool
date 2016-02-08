package com.jschool.entities;

import com.jschool.entities.status.CargoStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by infinity on 06.02.16.
 */
@Entity
@Table(name = "CargoStatusLog", schema = "logiweb")
public class CargoStatusLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCargoStatusLog", unique = true, nullable = false)
    private int id;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CargoStatus status;
    @Column(name = "timestamp", nullable = false)
    private Date timestamp;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="cargo_id", nullable = false)
    private CargoEntity cargo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CargoStatus getStatus() {
        return status;
    }

    public void setStatus(CargoStatus status) {
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public CargoEntity getCargo() {
        return cargo;
    }

    public void setCargo(CargoEntity cargo) {
        this.cargo = cargo;
    }

}
