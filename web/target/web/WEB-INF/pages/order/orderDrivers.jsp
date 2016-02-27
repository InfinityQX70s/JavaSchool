
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row center-align">
    <h5 class="center-align">The duration of the distance is <c:out value="${duration}"/> hours
    </h5>
</div>
<div class="row center-align">
    <h6 class="center-align">Select <c:out value="${shiftSize}"/> drivers for order
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
    <c:forEach var="driverMap" items="${drivers}">
    <tr>
        <td><input name="driverNumber" type="checkbox" id="<c:out value="${driverMap.key.number}"/>" value="<c:out value="${driverMap.key.number}"/>"/>
            <label for="<c:out value="${driverMap.key.number}"/>"><c:out value="${driverMap.key.number}"/>
            </label></td>
        <td><c:out value="${driverMap.key.firstName}"/>
        </td>
        <td><c:out value="${driverMap.key.lastName}"/>
        </td>
        <td><c:out value="${driverMap.value}"/>
        </td>
    </tr>
    </c:forEach>
    </tbody>
</table>