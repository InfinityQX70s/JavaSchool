<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="Orders"/>
</jsp:include>
<jsp:include page="../leftMenu.jsp">
    <jsp:param name="drivers" value=""/>
    <jsp:param name="orders" value="class=\"active z-depth-2\""/>
    <jsp:param name="trucks" value=""/>
</jsp:include>
<div class="row col s7 offset-s4">
    <a href="/employee/order/add" class="right btn-floating btn-large waves-effect waves-light red "><i
            class="material-icons">add</i></a>
</div>
<div class="row col s7 offset-s4">
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
                    <c:forEach var="cargo" items="${orderList.value}">
                    <li class="collection-item">
                        <div class="row">
                            <div class="col s4 left-align">
                                Cargo number: <c:out value="${cargo.number}"/>
                            </div>
                            <div class="col s4 center-align">
                                Cargo name: <c:out value="${cargo.name}"/>
                            </div>
                            <div class="col s4 right-align">
                                Cargo weight: <c:out value="${cargo.weight}"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s4 left-align light-green-text text-accent-4">
                                Pickup city: <c:out value="${cargo.pickup.city.name}"/>
                            </div>
                            <div class="col s4 center-align red-text">
                                <c:if test="${fn:length(cargo.statusLogs) eq 0}">
                                    Status: undefined
                                </c:if>
                                <c:if test="${fn:length(cargo.statusLogs) ne 0}">
                                    <c:forEach var="cargoStatusLog" items="${cargo.statusLogs}" varStatus="loop">
                                        <c:if test="${loop.last}">
                                            Status: <c:out value="${cargoStatusLog.status}"/>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                            </div>
                            <div class="col s4 right-align light-green-text text-accent-4">
                                Unload city: <c:out value="${cargo.unload.city.name}"/>
                            </div>
                        </div>
                    </li>
                    </c:forEach>
                </ul>
            </div>
        </li>
        </c:forEach>
    </ul>
</div>
<jsp:include page="../footer.jsp"/>
