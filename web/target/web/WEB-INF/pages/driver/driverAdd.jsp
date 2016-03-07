<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="Drivers"/>
</jsp:include>
<jsp:include page="../leftMenu.jsp">
    <jsp:param name="drivers" value="active z-depth-2"/>
    <jsp:param name="orders" value=""/>
    <jsp:param name="trucks" value=""/>
</jsp:include>
<div class="row col s6 z-depth-2  offset-s4 white" style="margin-top:50px;">
    <form class="col s12" action="/employee/driver/add" method="post">
        <div class="row" style="margin-top:20px;">
            <div class="input-field col s12">
                <spring:bind path="driver.number">
                    <input id="number" name="${status.expression}" type="text" class="validate">
                    <label for="number">Personal Number</label>
                </spring:bind>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s6">
                <spring:bind path="driver.firstName">
                    <input id="firstName" name="${status.expression}" type="text" class="validate">
                    <label for="firstName">First Name</label>
                </spring:bind>
            </div>
            <div class="input-field col s6">
                <spring:bind path="driver.lastName">
                    <input id="lastName" name="${status.expression}" type="text" class="validate">
                    <label for="lastName">Last Name</label>
                </spring:bind>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <spring:bind path="user.email">
                    <input id="email" name="${status.expression}" type="text" class="validate">
                    <label for="email">Email</label>
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