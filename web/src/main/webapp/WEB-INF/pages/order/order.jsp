<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="Orders"/>
</jsp:include>
<jsp:include page="../leftMenu.jsp">
    <jsp:param name="drivers" value=""/>
    <jsp:param name="orders" value="active z-depth-2"/>
    <jsp:param name="trucks" value=""/>
</jsp:include>
<c:if test="${not empty message}">
    <script type="text/javascript" >
        $(document).ready(function(){
            Materialize.toast("${message}", 4000);
        });
    </script>
</c:if>
<div class="col s7 offset-s4" style="margin-top:50px;">
    <ul class="collapsible" data-collapsible="accordion">
        <c:forEach var="orderList" items="${requestScope.orderListMap}">
            <li>
                <c:if test="${orderList.key.doneState}">
                    <div class="collapsible-header"><i class="material-icons">done</i>Order number: <c:out
                            value="${orderList.key.number}"/></div>
                </c:if>
                <c:if test="${not orderList.key.doneState}">
                    <div class="collapsible-header"><i class="material-icons">linear_scale</i>Order number: <c:out
                            value="${orderList.key.number}"/></div>
                </c:if>
                <div class="collapsible-body">
                    <ul class="collection">
                        <table class="striped centered white">
                            <thead>
                            <tr>
                                <th data-field="number">Point</th>
                                <th data-field="pickup">Pickup</th>
                                <th data-field="number">Point</th>
                                <th data-field="unload">Unload</th>
                                <th data-field="name">Name</th>
                                <th data-field="weight">Weight</th>
                                <th data-field="status">Status</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach var="cargo" items="${orderList.value}">
                                <tr>
                                    <td><c:out value="${cargo.pickup.point}"/>
                                    </td>
                                    <td><c:out value="${cargo.pickup.city.name}"/>
                                    </td>
                                    <td><c:out value="${cargo.unload.point}"/>
                                    </td>
                                    <td><c:out value="${cargo.unload.city.name}"/>
                                    </td>
                                    <td><c:out value="${cargo.name}"/>
                                    </td>
                                    <td><c:out value="${cargo.weight}"/>
                                    </td>
                                    <td><c:if test="${fn:length(cargo.statusLogs) eq 0}">
                                        undefined
                                    </c:if>
                                        <c:if test="${fn:length(cargo.statusLogs) ne 0}">
                                            <c:forEach var="cargoStatusLog" items="${cargo.statusLogs}"
                                                       varStatus="loop">
                                                <c:if test="${loop.last}">
                                                    <c:if test="${cargoStatusLog.status eq 'ready'}">
                                                        <i class="material-icons" style="color: #f8982b">schedule</i>
                                                    </c:if>
                                                    <c:if test="${cargoStatusLog.status eq 'loaded'}">
                                                        <i class="material-icons" style="color: #ca7834">local_shipping</i>
                                                    </c:if>
                                                    <c:if test="${cargoStatusLog.status eq 'delivered'}">
                                                        <i class="material-icons" style="color: #6ecddd">done</i>
                                                    </c:if>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </ul>
                </div>
            </li>
        </c:forEach>
    </ul>
</div>
<div class="row col s7 offset-s4">
    <a href="/employee/order/add" class="right btn-floating btn-large waves-effect waves-light light-blue accent-999"><i
            class="material-icons">add</i></a>
</div>
<jsp:include page="../footer.jsp"/>
