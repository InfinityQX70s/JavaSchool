package com.jschool.entities;

import javax.persistence.*;

/**
 * Created by infinity on 06.02.16.
 */
@Entity
@NamedQueries({
        @NamedQuery(name="Truck.findByNumber",
                query="SELECT t FROM Truck t WHERE t.number = :number"),
        @NamedQuery(name="Truck.findAllFreeByStateAndCapacity",
                query="SELECT t FROM Truck t WHERE t.repairState = :repairState AND t.capacity >= :capacity AND t.oreder IS null "),
        @NamedQuery(name="Truck.findAll",
                query="SELECT t FROM Truck t")
})
@Table(name = "Truck", schema = "logiweb")
public class Truck {
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
    @OneToOne(mappedBy="truck")
    private Order oreder;

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

    public Order getOreder() {
        return oreder;
    }

    public void setOreder(Order oreder) {
        this.oreder = oreder;
    }

}
