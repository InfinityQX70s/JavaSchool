package com.jschool.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by infinity on 06.02.16.
 */
@Entity
@NamedQueries({
        @NamedQuery(name="Driver.findByNumber",
                query="SELECT d FROM DriverEntity d WHERE d.number = :number"),
        @NamedQuery(name="Driver.findByFirstNameAndLastName",
                query="SELECT d FROM DriverEntity d WHERE d.firstName = :firstName AND d.lastName = :lastName"),
        @NamedQuery(name="Driver.findAll",
                query="SELECT d FROM DriverEntity d")
})
@Table(name = "Driver", schema = "logiweb")
public class DriverEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDriver", unique = true, nullable = false)
    private int id;
    @Column(name = "number", unique = true, nullable = false)
    private int number;
    @Column(name = "firstName", nullable = false)
    private String firstName;
    @Column(name = "firstName", nullable = false)
    private String lastName;
    @OneToOne(optional=false)
    @JoinColumn(name="user_id", nullable=false)
    private UserEntity user;
    @OneToMany(fetch=FetchType.LAZY, mappedBy = "driver")
    private List<DriverStatusLogEntity> statuses;
    @OneToMany(fetch=FetchType.LAZY, mappedBy = "driver")
    private List<DriverStatisticEntity> statistics;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_id")
    private OrdersEntity order;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<DriverStatusLogEntity> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<DriverStatusLogEntity> statuses) {
        this.statuses = statuses;
    }

    public List<DriverStatisticEntity> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<DriverStatisticEntity> statistics) {
        this.statistics = statistics;
    }

    public OrdersEntity getOrder() {
        return order;
    }

    public void setOrder(OrdersEntity order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DriverEntity that = (DriverEntity) o;

        if (id != that.id) return false;
        if (number != that.number) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + number;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }
}
