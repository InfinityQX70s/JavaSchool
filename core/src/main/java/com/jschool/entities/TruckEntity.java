package com.jschool.entities;

import javax.persistence.*;

/**
 * Created by infinity on 06.02.16.
 */
@Entity
@NamedQueries({
        @NamedQuery(name="Truck.findAllByStateAndCapacity",
                query="SELECT t FROM TruckEntity t WHERE t.repairState = :repairState AND t.capacity = :capacity"),
        @NamedQuery(name="Truck.findAll",
                query="SELECT t FROM TruckEntity t")
})
@Table(name = "Truck", schema = "logiweb")
public class TruckEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTruck", unique = true, nullable = false)
    private int id;
    @Column(name = "number", unique = true, nullable = false)
    private String number;
    @Column(name = "capacity", nullable = false)
    private int capacity;
    @Column(name = "shiftSize", nullable = false)
    private int shiftSize;
    @Column(name = "repairState", nullable = false)
    private boolean repairState;
    @OneToOne(optional=false, mappedBy="truck")
    private OrdersEntity oreder;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getShiftSize() {
        return shiftSize;
    }

    public void setShiftSize(int shiftSize) {
        this.shiftSize = shiftSize;
    }

    public boolean isRepairState() {
        return repairState;
    }

    public void setRepairState(boolean repairState) {
        this.repairState = repairState;
    }

    public OrdersEntity getOreder() {
        return oreder;
    }

    public void setOreder(OrdersEntity oreder) {
        this.oreder = oreder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TruckEntity that = (TruckEntity) o;

        if (id != that.id) return false;
        if (capacity != that.capacity) return false;
        if (shiftSize != that.shiftSize) return false;
        if (repairState != that.repairState) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + capacity;
        result = 31 * result + shiftSize;
        return result;
    }
}
