
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${not empty message}">
    <div class="center-align">
    <h5 class="center-align white-text hours">${message}</h5>
</c:if>
<c:if test="${empty message}">
    <div class="center-align">
        <h5 class="center-align white-text hours">The duration of the distance is <c:out value="${duration}"/> hours
        </h5>
    </div>
    <div class="center-align">
        <input hidden id="count" name="count" type="text" class="validate" value="${shiftSize}">
        <h6 class="center-align white-text">Select <c:out value="${shiftSize}"/> drivers for order
        </h6>
    </div>
    <table class="row bordered centered z-depth-2 white">
        <thead>
        <tr>
            <th data-field="id">Check</th>
            <th data-field="firstName">First Name</th>
            <th data-field="lastName">Last Name</th>
            <th data-field="city">City</th>
            <th data-field="hoursWorked">Hours Worked</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="driverMap" items="${drivers}">
            <tr>
                <td><input name="driverNumber" type="checkbox" id="<c:out value="${driverMap.key.number}"/>" value="<c:out value="${driverMap.key.number}"/>"/>
                    <label for="<c:out value="${driverMap.key.number}"/>"><c:out value="${driverMap.key.number}"/>
                    </label></td>
                <td><c:out value="${driverMap.key.firstName}"/>
                </td>
                <td><c:out value="${driverMap.key.lastName}"/>
                </td>
                <td><c:out value="${driverMap.key.city.name}"/>
                </td>
                <td><c:out value="${driverMap.value}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
