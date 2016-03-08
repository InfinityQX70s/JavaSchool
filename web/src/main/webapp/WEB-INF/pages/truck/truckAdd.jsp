<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
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
    <c:if test="${not empty error}">
        <h5><blockquote>${error}</blockquote></h5>
    </c:if>
    <form class="col s12" action="/employee/truck/add" method="post">
        <div class="row" style="margin-top:20px;">
            <div class="input-field col s12">
                <spring:bind path="truck.number">
                    <form:input path="truck.number" class="validate" id="number"/>
                    <label for="number">Number</label>
                    <form:errors path="truck.number" cssClass="blockquote"/>
                </spring:bind>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s6">
                <spring:bind path="truck.capacity">
                    <form:input path="truck.capacity" class="validate" id="capacity"/>
                    <label for="capacity">Capacity</label>
                    <form:errors path="truck.capacity" cssClass="blockquote"/>
                </spring:bind>
            </div>
            <div class="input-field col s6">
                <spring:bind path="truck.shiftSize">
                    <form:input path="truck.shiftSize" class="validate" id="shiftSize"/>
                    <label for="shiftSize">Shift Size</label>
                    <form:errors path="truck.shiftSize" cssClass="blockquote"/>
                </spring:bind>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <spring:bind path="truck.repairState">
                    <form:select path="truck.repairState">
                        <form:option value="true" label="OK"/>
                        <form:option value="false" label="Broken"/>
                    </form:select>
                    <label>Truck State</label>
                    <form:errors path="truck.repairState" cssClass="blockquote"/>
                </spring:bind>
            </div>
        </div>
        <div class="row right-align">
            <button class="btn waves-effect waves-light light-blue accent-999 light-blue-text text-accent-999" type="submit" name="action">Submit
                <i class="material-icons right">send</i>
            </button>
        </div>
    </form>
</div>
<jsp:include page="../footer.jsp"/>