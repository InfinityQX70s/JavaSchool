<%@ page import="com.jschool.entities.Driver" %>
<%@ page import="com.jschool.entities.Order" %>
<%@ page import="com.jschool.entities.Cargo" %>
<%@ page import="java.util.List" %>
<%@ page import="com.jschool.entities.CargoStatusLog" %><%--
  Created by IntelliJ IDEA.
  User: infinity
  Date: 22.02.16
  Time: 17:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Driver Info</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="/css/materialize.css" media="screen,projection"/>
    <script type="text/javascript" src="/js/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="/js/materialize.min.js"></script>
</head>
<body>
<div class="row" style="margin-top:50px;">
    <div class="col s6 offset-s3">
        <div class="card-panel">
          <span class="black-text">
               <% Driver driver = (Driver) request.getAttribute("driver");%>
               <blockquote>
                   <h5><%= driver.getNumber()%>
                   </h5>
                   <h5><%= driver.getFirstName()%>
                   </h5>
                   <h5><%= driver.getLastName()%>
                   </h5>
               </blockquote>
               <% Boolean assign = (Boolean) request.getAttribute("assign");%>
               <% if (assign) {%>
                      <% List<Cargo> cargos = (List<Cargo>) request.getAttribute("cargos");%>
                      <% List<Driver> drivers = (List<Driver>) request.getAttribute("drivers");%>
                      <% Order order = (Order) request.getAttribute("order");%>
                    <h6>Assigned on order: <%= order.getNumber()%>
                    </h6>
                    <h6>Truck number : <%= order.getTruck().getNumber()%>
                    </h6>
                    <h6>Co-drivers :
                        <% for (Driver element : drivers) {%>
                        <%= element.getNumber() + "  "%>
                        <%}%>
                    </h6>
                    <h6>Route Points List</h6>
                                    <ul>
                                        <% for (Cargo cargo : cargos) {%>
                                        <li>
                                            <div class="divider"></div>
                                            <div class="row">
                                                <div class="col s4 left-align">
                                                    <%= " Cargo number: " + cargo.getNumber()%>
                                                </div>
                                                <div class="col s4 center-align">
                                                    <%= " Cargo name: " + cargo.getName()%>
                                                </div>
                                                <div class="col s4 right-align">
                                                    <%= " Cargo weight: " + cargo.getWeight() %>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col s4 left-align light-green-text text-accent-4">
                                                    <%= " Pickup city: " + cargo.getPickup().getCity().getName()%>
                                                </div>
                                                <div class="col s4 center-align red-text">
                                                    <% List<CargoStatusLog> cargoStatusLogs = cargo.getStatusLogs(); %>
                                                    <%if (cargoStatusLogs.size() == 0){%>
                                                    <%= " Status: undefined"%>
                                                    <%}else{%>
                                                    <% CargoStatusLog cargoStatusLog = cargoStatusLogs.get(cargoStatusLogs.size()-1);%>
                                                    <%= " Status: " + cargoStatusLog.getStatus()%>
                                                    <% }%>
                                                </div>
                                                <div class="col s4 right-align light-green-text text-accent-4">
                                                    <%= " Unload city: " + cargo.getUnload().getCity().getName()%>
                                                </div>
                                            </div>
                                        </li>
                                        <% } %>
                                    </ul>

               <%} else {%>
                    <h6>Do not assigned on order</h6>
               <%}%>
          </span>
            <div class="row center" style="margin-top:30px;">
                <a href="/logout" class="waves-effect waves-light btn"><i class="material-icons left">arrow_back</i>Logout</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>