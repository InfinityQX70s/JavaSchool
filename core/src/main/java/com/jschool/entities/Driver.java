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
                query="SELECT d FROM Driver d LEFT JOIN d.order o LEFT JOIN d.city c WHERE o IS NULL AND c.name = :city"),
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
    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;
    @Column(name = "token")
    private String token;
    @OneToOne(optional=false)
    @JoinColumn(name="user_id", nullable=false)
    private User user;
    @OneToMany(fetch=FetchType.LAZY, mappedBy = "driver", cascade = CascadeType.ALL)//cascade=CascadeType.ALL
    private List<DriverStatusLog> statusLogs;
    @OneToMany(fetch=FetchType.LAZY, mappedBy = "driver", cascade = CascadeType.ALL)//cascade=CascadeType.ALL
    private List<DriverStatistic> statistics;
    @OneToMany(fetch=FetchType.LAZY, mappedBy = "driver", cascade = CascadeType.ALL)//cascade=CascadeType.ALL
    private List<DriverAuthCode> driverAuthCodes;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="order_id")
    private Order order;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="сity_id", nullable = false)
    private City city;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public List<DriverAuthCode> getDriverAuthCodes() {
        return driverAuthCodes;
    }

    public void setDriverAuthCodes(List<DriverAuthCode> driverAuthCodes) {
        this.driverAuthCodes = driverAuthCodes;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
