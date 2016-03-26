<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>User Add</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="/css/materialize.css" media="screen,projection"/>
    <script type="text/javascript" src="/js/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="/js/materialize.min.js"></script>
</head>
<body background="/image/background.png">
<div class="row light-blue-text text-accent-999">
    <div class="row col s6 z-depth-2  offset-s3 white" style="margin-top:50px;">
        <c:if test="${not empty error}">
            <h5>
                <blockquote>${error}</blockquote>
            </h5>
        </c:if>
        <form action="/admin/user/add" method="post">
            <div class="row" style="margin-top:20px;">
                <div class="input-field col s6">
                    <spring:bind path="user.email">
                        <form:input path="user.email" class="validate" id="email"/>
                        <label for="email">Email</label>
                        <form:errors path="user.email" cssClass="blockquote"/>
                    </spring:bind>
                </div>
                <div class="input-field col s6">
                    <spring:bind path="user.password">
                        <form:input path="user.password" class="validate" id="password"/>
                        <label for="password">Password</label>
                        <form:errors path="user.password" cssClass="blockquote"/>
                    </spring:bind>
                </div>
            </div>
            <div class="row right-align">
                <button class="btn waves-effect waves-light light-blue accent-999 light-blue-text text-accent-999"
                        type="submit" name="action">Submit
                    <i class="material-icons right">send</i>
                </button>
            </div>
        </form>
    </div>
</div>
</body>
</html>