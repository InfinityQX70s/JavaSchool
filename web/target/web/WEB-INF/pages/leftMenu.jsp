<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.jschool.entities.User" %><%--
  Created by IntelliJ IDEA.
  User: infinity
  Date: 14.02.16
  Time: 18:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<body>
<div class="row">
    <div class="col s3">
        <ul id="nav-mobile" class="side-nav fixed" style="width: 240px;">
            <img src="/image/user.png" style="margin-top:20px; margin-left: 60px;">
            <p style="margin-left: 40px; margin-top:30px;color:#616161"><c:out value="${sessionScope.entity.email}" /></p>
            <div class="side-navbar">
                <ul style="margin-top:80px;">
                    <li class="<c:out value="${param.orders}"/>"><a href="/employee/orders">Orders</a></li>
                    <li class="<c:out value="${param.drivers}"/>"><a href="/employee/drivers">Drivers</a></li>
                    <li class="<c:out value="${param.trucks}"/>"><a href="/employee/trucks">Trucks</a></li>
                    <li><a href="/logout">Log out</a></li>
                </ul>
            </div>
        </ul>
    </div>