<%@ page import="com.jschool.entities.Truck" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: infinity
  Date: 14.02.16
  Time: 18:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="Trucks"/>
</jsp:include>
<jsp:include page="../leftMenu.jsp">
    <jsp:param name="drivers" value=""/>
    <jsp:param name="orders" value=""/>
    <jsp:param name="trucks" value="class=\"active z-depth-2\""/>
</jsp:include>
<p></p>
<table style="margin-top:50px;" class="bordered centered z-depth-2 col s6 offset-s4">
    <thead>
    <tr>
        <th data-field="id">Number</th>
        <th data-field="capacity">Capacity</th>
        <th data-field="shift_size">Shift Size</th>
        <th data-field="state">State</th>
        <th data-field="change"></th>
        <th data-field="delete"></th>
    </tr>
    </thead>

    <tbody>
    <% List<Truck> trucks = (List<Truck>) request.getAttribute("trucks");%>
    <% for (Truck truck : trucks) { %>
    <tr>
        <td><%=truck.getNumber()%>
        </td>
        <td><%=truck.getCapacity()%>
        </td>
        <td><%=truck.getShiftSize()%>
        </td>
        <% if (truck.isRepairState()) {%>
        <td>OK</td>
        <% } else { %>
        <td>Broken</td>
        <% } %>
        <% if (truck.getOreder() == null) {%>
        <td>Free</td>
        <td>
            <a href="/employee/truck/<%=truck.getNumber()%>/edit" class="secondary-content">
                <i class="material-icons">create</i>
            </a>
        </td>
        <% } else { %>
        <td>Assigned</td>
        <td>
        </td>
        <% } %>
        <td>
            <form action="/employee/truck/delete" method="post">
                <input type="hidden" name="number" value="<%=truck.getNumber()%>">
                <a class="secondary-content" style="margin-right:20px;" onclick="parentNode.submit();">
                    <i class="material-icons">clear</i>
                </a>
            </form>
        </td>
    </tr>
    <% } %>
    </tbody>

</table>
<div class="row col s6 offset-s4 right-align">
    <a href="/employee/truck/add" class="right btn-floating btn-large waves-effect waves-light red "><i
            class="material-icons">add</i></a>
</div>
<jsp:include page="../footer.jsp"/>