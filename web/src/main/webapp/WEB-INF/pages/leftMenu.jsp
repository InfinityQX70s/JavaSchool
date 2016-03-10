<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.jschool.entities.User" %><%--
  Created by IntelliJ IDEA.
  User: infinity
  Date: 14.02.16
  Time: 18:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<body background="/image/background.png">
<div class="row light-blue-text text-accent-999">
    <div class="col s3">
        <ul id="nav-mobile" class="side-nav fixed z-depth-2" style="width: 240px;">
            <div style="background-image: url(/image/background.png); height: 250px; width: 400px;">
                <img src="/image/user.png" style="margin-top:20px; margin-left: 60px;">
                <p style="margin-left: 40px; margin-top:30px;color:#FFFFFF">${pageContext.request.userPrincipal.name} </p>
                <br>
                <br>
            </div>
            <div class="side-navbar">
                <ul style="margin-top:80px;" class="">
                    <li class="<c:out value="${param.orders}"/>"><a href="/employee/orders">Orders</a></li>
                    <li class="<c:out value="${param.drivers}"/>"><a href="/employee/drivers">Drivers</a></li>
                    <li class="<c:out value="${param.trucks}"/>"><a href="/employee/trucks">Trucks</a></li>
                    <li><a href="/logout">Sign out</a></li>
                </ul>
            </div>
        </ul>
    </div>