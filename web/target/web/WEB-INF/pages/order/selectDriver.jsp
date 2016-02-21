<%@ page import="com.jschool.entities.Driver" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: infinity
  Date: 15.02.16
  Time: 18:24
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
<!--Заказ-->
<form action="/employee/order/driver" method="post">
    <div class="row col s7 offset-s3 center-align" style="margin-top:50px;" >
        <h5 class="center-align">The duration of the distance is <%=request.getAttribute("duration") %> hours
        </h5>
    </div>
    <div class="row col s7 offset-s3 center-align">
        <h6 class="center-align">Select <%=request.getAttribute("shiftSize") %> drivers for order
        </h6>
    </div>
    <table class="bordered centered z-depth-2 col s5 offset-m4">
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
                <label for="<%= driver.getNumber()%>"><%= driver.getNumber()%></label>
            </td>
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
    <div class="row col s7 offset-s4 center-align">
        <input type="hidden" name="orderNumber" value="<%=request.getAttribute("orderNumber")%>">
        <button class="btn waves-effect waves-light" type="submit" name="action" style="margin-right:250px;">Submit
        </button>
    </div>
</form>
<jsp:include page="../footer.jsp"/>
