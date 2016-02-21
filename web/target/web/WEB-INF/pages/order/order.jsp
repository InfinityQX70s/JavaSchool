<%@ page import="com.jschool.entities.Order" %>
<%@ page import="com.jschool.entities.Cargo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.jschool.entities.CargoStatusLog" %><%--
  Created by IntelliJ IDEA.
  User: infinity
  Date: 14.02.16
  Time: 18:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="Orders"/>
</jsp:include>
<jsp:include page="../leftMenu.jsp">
    <jsp:param name="drivers" value=""/>
    <jsp:param name="orders" value="class=\"active z-depth-2\""/>
    <jsp:param name="trucks" value=""/>
</jsp:include>
<div class="row col s7 offset-s4">
    <a href="/employee/order/add" class="right btn-floating btn-large waves-effect waves-light red "><i class="material-icons">add</i></a>
</div>
<div class="row col s7 offset-s4" >
    <ul class="collapsible" data-collapsible="accordion">
        <% Map<Order,List<Cargo>> orderListMap = (Map<Order,List<Cargo>>) request.getAttribute("orderListMap");%>
        <% for (Map.Entry<Order,List<Cargo>> element : orderListMap.entrySet()) { %>
        <% Order order = element.getKey();%>
        <% List<Cargo> cargos = element.getValue();%>
        <li>
            <% if (order.isDoneState()){%>
                <div class="collapsible-header"><i class="material-icons">done</i><%= "Order number: " + order.getNumber()%></div>
            <% } else {%>
                <div class="collapsible-header"><i class="material-icons">linear_scale</i><%= "Order number: " + order.getNumber()%></div>
            <% } %>
            <div class="collapsible-body">
                <ul class="collection">
                    <% for (Cargo cargo : cargos) {%>
                    <li class="collection-item">
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
            </div>
        </li>
        <% } %>
    </ul>
</div>
<jsp:include page="../footer.jsp"/>
