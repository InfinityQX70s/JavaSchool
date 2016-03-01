<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.jschool.entities.Truck" %><%--
  Created by IntelliJ IDEA.
  User: infinity
  Date: 15.02.16
  Time: 12:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="Trucks"/>
</jsp:include>
<jsp:include page="../leftMenu.jsp">
    <jsp:param name="drivers" value=""/>
    <jsp:param name="orders" value=""/>
    <jsp:param name="trucks" value="active z-depth-2"/>
</jsp:include>
<div class="row col s6 z-depth-2  offset-s4 white" style="margin-top:50px;">
    <form class="col s12" action="/employee/truck/change" method="post">
        <div class="row" style="margin-top:20px;">
            <div class="input-field col s12">
                <input type="hidden" name="number" value="<c:out value="${truck.number}"/>">
                <input disabled id="number" type="text" class="validate" value="<c:out value="${truck.number}"/>">
                <label for="number">Number</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s6">
                <input id="capacity" name="capacity" type="text" class="validate" value="<c:out value="${truck.capacity}"/>">
                <label for="capacity">Capacity</label>
            </div>
            <div class="input-field col s6">
                <input id="shiftSize" name="shiftSize" type="text" class="validate" value="<c:out value="${truck.shiftSize}"/>">
                <label for="shiftSize">Shift Size</label>
            </div>
        </div>
        <div class="row">
            <c:if test="${truck.repairState}">
            <div class="input-field col s12">
                <select name="status">
                    <option value="ok">OK</option>
                    <option value="broken">Broken</option>
                </select>
                <label>Truck State</label>
            </div>
            </c:if>
            <c:if test="${not truck.repairState}">
            <div class="input-field col s12">
                <select name="status">
                    <option value="broken">Broken</option>
                    <option value="ok">OK</option>
                </select>
                <label>Truck State</label>
            </div>
            </c:if>
        </div>
        <div class="row right-align">
            <button class="btn waves-effect waves-light light-blue accent-999 light-blue-text text-accent-999" type="submit" name="action">Submit
                <i class="material-icons right">send</i>
            </button>
        </div>
    </form>
</div>
<jsp:include page="../footer.jsp"/>