package com.jschool.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by infinity on 06.02.16.
 */
@Entity
@NamedQueries({
        @NamedQuery(name="Driver.findByNumber",
                query="SELECT d FROM Driver d WHERE d.number = :number"),
        @NamedQuery(name="Driver.findAllFreeDrivers",
                query="SELECT d FROM Driver d LEFT JOIN d.order o WHERE o IS NULL"),
        @NamedQuery(name="Driver.findAllByFirstNameAndLastName",
                query="SELECT d FROM Driver d WHERE d.firstName = :firstName AND d.lastName = :lastName"),
        @NamedQuery(name="Driver.findAll",
                query="SELECT d FROM Driver d")
})
@Table(name = "Driver", schema = "logiweb")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDriver", unique = true, nullable = false)
    private int id;
    @Column(name = "number", unique = true, nullable = false)
    private int number;
    @Column(name = "firstName", nullable = false)
    private String firstName;
    @Column(name = "lastName", nullable = false)
    private String lastName;
    @OneToOne(optional=false)
    @JoinColumn(name="user_id", nullable=false)
    private User user;
    @OneToMany(fetch=FetchType.LAZY, mappedBy = "driver", cascade = CascadeType.ALL)//cascade=CascadeType.ALL
    private List<DriverStatusLog> statusLogs;
    @OneToMany(fetch=FetchType.LAZY, mappedBy = "driver", cascade = CascadeType.ALL)//cascade=CascadeType.ALL
    private List<DriverStatistic> statistics;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="order_id")
    private Order order;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<DriverStatusLog> getStatusLogs() {
        return statusLogs;
    }

    public void setStatusLogs(List<DriverStatusLog> statusLogs) {
        this.statusLogs = statusLogs;
    }

    public List<DriverStatistic> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<DriverStatistic> statistics) {
        this.statistics = statistics;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
