<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${not empty message}">
    <h5 class="center-align white-text">${message}</h5>
</c:if>
<c:if test="${empty message}">
    <h5 class="center-align white-text">Cargo maximum weight is <c:out value="${max}"/>
    </h5>
    <table class="bordered centered z-depth-2 white">
        <thead>
        <tr>
            <th data-field="id">Check</th>
            <th data-field="capacity">Capacity</th>
            <th data-field="shiftSize">Shift size</th>
            <th data-field="city">City</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="truck" items="${trucks}">
            <tr>
                <td><input name="truckNumber" type="radio" id="<c:out value="${truck.number}"/>"
                           value="<c:out value="${truck.number}"/>"/>
                    <label for="<c:out value="${truck.number}"/>"><c:out value="${truck.number}"/>
                    </label></td>
                <td><c:out value="${truck.capacity}"/>
                </td>
                <td><c:out value="${truck.shiftSize}"/>
                </td>
                <td><c:out value="${truck.city.name}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

