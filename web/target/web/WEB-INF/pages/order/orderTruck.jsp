<%@ page import="com.jschool.entities.Truck" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: infinity
  Date: 20.02.16
  Time: 20:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h5 class="center-align">Cargo maximum weight is <%=request.getAttribute("max")%>
</h5>
<table class="bordered centered z-depth-2">
    <thead>
    <tr>
        <th data-field="id">Check</th>
        <th data-field="capacity">Capacity</th>
        <th data-field="shiftSize">Shift size</th>
    </tr>
    </thead>
    <tbody>
    <% List<Truck> trucks = (List<Truck>) request.getAttribute("trucks");%>
    <% for (Truck truck : trucks) { %>
    <tr>
        <td><input name="truckNumber" type="radio" id="<%= truck.getNumber()%>" value="<%= truck.getNumber()%>"/>
            <label for="<%= truck.getNumber()%>"><%= truck.getNumber()%>
            </label></td>
        <td><%= truck.getCapacity()%>
        </td>
        <td><%= truck.getShiftSize()%>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>

