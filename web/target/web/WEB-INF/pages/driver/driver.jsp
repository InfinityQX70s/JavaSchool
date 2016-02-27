<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    <c:forEach var="driver" items="${requestScope.drivers}">
    <tr>
        <td><c:out value="${driver.number}"/>
        </td>
        <td><c:out value="${driver.firstName}"/>
        </td>
        <td><c:out value="${driver.lastName}"/>
        </td>
        <c:if test="${empty driver.order}">
        <td>Free</td>
        <td>
            <a href="/employee/driver/<c:out value="${driver.number}"/>/edit" class="secondary-content">
                <i class="material-icons">create</i>
            </a>
        </td>
        </c:if>
        <c:if test="${not empty driver.order}">
        <td>Assigned</td>
        <td>
        </td>
        </c:if>
        <%--<td><%=driver.getStatusLogs().get(driver.getStatusLogs().size() - 1).getStatus().toString()%>--%>
        <%--</td>--%>
        <td>
            <form action="/employee/driver/delete" method="post">
                <input type="hidden" name="number" value="<c:out value="${driver.number}"/>">
                <a class="secondary-content" style="margin-right:20px;" onclick="parentNode.submit();">
                    <i class="material-icons">clear</i>
                </a>
            </form>
        </td>
    </tr>
    </c:forEach>
    </tbody>

</table>
<div class="row col s6 offset-s4 right-align">
    <a href="/employee/driver/add" class="right btn-floating btn-large waves-effect waves-light red "><i
            class="material-icons">add</i></a>
</div>
<jsp:include page="../footer.jsp"/>