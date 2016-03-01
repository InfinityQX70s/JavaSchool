<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="Drivers"/>
</jsp:include>
<jsp:include page="../leftMenu.jsp">
    <jsp:param name="drivers" value="active z-depth-2"/>
    <jsp:param name="orders" value=""/>
    <jsp:param name="trucks" value=""/>
</jsp:include>
<div class="row col s6 z-depth-2  offset-s4 white" style="margin-top:50px;">
    <form class="col s12" action="/employee/driver/change" method="post">
        <div class="row" style="margin-top:20px;">
            <div class="input-field col s12">
                <input type="hidden" name="number" value="<c:out value="${driver.number}"/>">
                <input disabled id="number" type="text" class="validate" value="<c:out value="${driver.number}"/>">
                <label for="number">Personal Number</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s6">
                <input id="first_name" name="firstName" type="text" class="validate" value="<c:out value="${driver.firstName}"/>">
                <label for="first_name">First Name</label>
            </div>
            <div class="input-field col s6">
                <input id="last_name" name="lastName" type="text" class="validate" value="<c:out value="${driver.lastName}"/>">
                <label for="last_name">Last Name</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <input disabled id="email" name="email" type="email" class="validate" value="<c:out value="${driver.user.email}"/>">
                <label for="email">Email</label>
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