<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="Trucks"/>
</jsp:include>
<jsp:include page="../leftMenu.jsp">
    <jsp:param name="drivers" value=""/>
    <jsp:param name="orders" value=""/>
    <jsp:param name="trucks" value="active z-depth-2"/>
</jsp:include>
<p></p>
<table style="margin-top:50px;" class="bordered centered z-depth-2 col s6 offset-s4 white">
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
    <c:forEach var="truck" items="${requestScope.trucks}">
    <tr>
        <td><c:out value="${truck.number}"/>
        </td>
        <td><c:out value="${truck.capacity}"/>
        </td>
        <td><c:out value="${truck.shiftSize}"/>
        </td>
        <c:if test="${truck.repairState}">
            <td>OK</td>
        </c:if>
        <c:if test="${not truck.repairState}">
        <td>Broken</td>
        </c:if>
        <c:if test="${empty truck.oreder}">
            <td>Free</td>
            <td>
                <a href="/employee/truck/<c:out value="${truck.number}"/>/edit" class="secondary-content light-blue-text text-accent-888">
                    <i class="material-icons">create</i>
                </a>
            </td>
        </c:if>
        <c:if test="${not empty truck.oreder}">
            <td>Assigned</td>
            <td>
            </td>
        </c:if>
        <td>
            <form action="/employee/truck/delete" method="post">
                <input type="hidden" name="number" value="<c:out value="${truck.number}"/>">
                <a class="secondary-content light-blue-text text-accent-888" style="margin-right:20px;" onclick="parentNode.submit();">
                    <i class="material-icons">clear</i>
                </a>
            </form>
        </td>
    </tr>
    </c:forEach>
    </tbody>

</table>
<div class="row col s6 offset-s4 right-align">
    <a href="/employee/truck/add" style="margin-top:5px;" class="right btn-floating btn-large waves-effect waves-light light-blue accent-999"><i
            class="material-icons">add</i></a>
</div>
<jsp:include page="../footer.jsp"/>