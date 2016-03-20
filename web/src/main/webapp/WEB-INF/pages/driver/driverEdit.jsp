<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="Drivers"/>
</jsp:include>
<jsp:include page="../leftMenu.jsp">
    <jsp:param name="drivers" value="active z-depth-2"/>
    <jsp:param name="orders" value=""/>
    <jsp:param name="trucks" value=""/>
</jsp:include>
<script type="text/javascript">
    $(document).ready(function(){
        $('.city').autocomplete({
            serviceUrl: '/api/cities'
        });
    });
</script>
<div class="row col s6 z-depth-2  offset-s4 white" style="margin-top:50px;">
    <c:if test="${not empty error}">
        <h5><blockquote>${error}</blockquote></h5>
    </c:if>
    <form class="col s12" action="/employee/driver/change" method="post">
        <div class="row" style="margin-top:20px;">
            <div class="input-field col s12">
                <spring:bind path="driver.number">
                    <form:hidden path="driver.number" class="validate" id="number" value="${driver.number}"/>
                </spring:bind>
                <input disabled id="number" type="text" class="validate" value="<c:out value="${driver.number}"/>">
                <label for="number">Personal Number</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s6">
                <spring:bind path="driver.firstName">
                    <form:input path="driver.firstName" class="validate" id="firstName" value="${driver.firstName}"/>
                    <label for="firstName">First Name</label>
                    <form:errors path="driver.firstName" cssClass="blockquote"/>
                </spring:bind>
            </div>
            <div class="input-field col s6">
                <spring:bind path="driver.lastName">
                    <form:input path="driver.lastName" class="validate" id="lastName" value="${driver.lastName}"/>
                    <label for="lastName">Last Name</label>
                    <form:errors path="driver.lastName" cssClass="blockquote"/>
                </spring:bind>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s6">
                <spring:bind path="driver.user.email">
                    <form:input path="driver.user.email" class="validate" id="email"  value="${driver.user.email}" />
                    <label for="email">Email</label>
                    <form:errors path="driver.user.email" cssClass="blockquote"/>
                </spring:bind>
            </div>
            <div class="input-field col s6">
                <spring:bind path="driver.city.name">
                    <form:input path="driver.city.name" class="validate city" id="city" value="${driver.city.name}"/>
                    <label for="city">City</label>
                    <form:errors path="driver.city.name" cssClass="blockquote"/>
                </spring:bind>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s6">
                <spring:bind path="driver.phoneNumber">
                    <form:input path="driver.phoneNumber" class="validate" id="phoneNumber" value="${driver.phoneNumber}"/>
                    <label for="phoneNumber">Phone Number</label>
                    <form:errors path="driver.phoneNumber" cssClass="blockquote"/>
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