<%@ page import="com.jschool.entities.Driver" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: infinity
  Date: 20.02.16
  Time: 20:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row center-align">
    <h5 class="center-align">The duration of the distance is <%=request.getAttribute("duration") %> hours
    </h5>
</div>
<div class="row center-align">
    <h6 class="center-align">Select <%=request.getAttribute("shiftSize") %> drivers for order
    </h6>
</div>
<table class="bordered centered z-depth-2">
    <thead>
    <tr>
        <th data-field="id">Check</th>
        <th data-field="firstName">First Name</th>
        <th data-field="lastName">Last Name</th>
        <th data-field="hoursWorked">Hours Worked</th>
    </tr>
    </thead>
    <tbody>
    <% Map<Driver,Integer> drivers = (Map<Driver,Integer>) request.getAttribute("drivers");%>
    <% for (Map.Entry<Driver,Integer> element : drivers.entrySet()) { %>
    <% Driver driver = element.getKey();%>
    <tr>
        <td><input name="driverNumber" type="checkbox" id="<%= driver.getNumber()%>" value="<%= driver.getNumber()%>"/>
            <label for="<%= driver.getNumber()%>"><%= driver.getNumber()%>
            </label></td>
        <td><%= driver.getFirstName()%>
        </td>
        <td><%= driver.getLastName()%>
        </td>
        <td><%= element.getValue()%>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>