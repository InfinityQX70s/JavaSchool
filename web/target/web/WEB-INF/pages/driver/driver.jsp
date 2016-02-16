<%@ page import="java.util.List" %>
<%@ page import="com.jschool.entities.Driver" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="Drivers"/>
</jsp:include>
<jsp:include page="../leftMenu.jsp">
    <jsp:param name="drivers" value="class=\"active z-depth-2\""/>
    <jsp:param name="orders" value=""/>
    <jsp:param name="trucks" value=""/>
</jsp:include>
<p></p>
<table style="margin-top:50px;" class="bordered centered z-depth-2 col s6 offset-s4">
    <thead>
    <tr>
        <th data-field="id">Number</th>
        <th data-field="first_name">First Name</th>
        <th data-field="last_name">Last Name</th>
        <th data-field="change"></th>
        <th data-field="delete"></th>
    </tr>
    </thead>

    <tbody>
    <% List<Driver> drivers = (List<Driver>) request.getAttribute("drivers");%>
    <% for (Driver driver : drivers) { %>
    <tr>
        <td><%=driver.getNumber()%>
        </td>
        <td><%=driver.getFirstName()%>
        </td>
        <td><%=driver.getLastName()%>
        </td>
        <%--<td><%=driver.getStatusLogs().get(driver.getStatusLogs().size() - 1).getStatus().toString()%>--%>
        <%--</td>--%>
        <td>
            <a href="/employee/driver/<%=driver.getNumber()%>/edit" class="secondary-content">
                <i class="material-icons">create</i>
            </a>
        </td>
        <td>
            <form action="/employee/driver/delete" method="post">
                <input type="hidden" name="number" value="<%=driver.getNumber()%>">
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
    <a href="/employee/driver/add" class="right btn-floating btn-large waves-effect waves-light red "><i
            class="material-icons">add</i></a>
</div>
<jsp:include page="../footer.jsp"/>