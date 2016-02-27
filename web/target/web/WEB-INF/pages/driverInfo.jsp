<%@ page import="com.jschool.entities.Driver" %>
<%@ page import="com.jschool.entities.Order" %>
<%@ page import="com.jschool.entities.Cargo" %>
<%@ page import="java.util.List" %>
<%@ page import="com.jschool.entities.CargoStatusLog" %><%--
  Created by IntelliJ IDEA.
  User: infinity
  Date: 22.02.16
  Time: 17:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Driver Info</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="/css/materialize.css" media="screen,projection"/>
    <script type="text/javascript" src="/js/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="/js/materialize.min.js"></script>
</head>
<body>
<div class="row" style="margin-top:50px;">
    <div class="col s6 offset-s3">
        <div class="card-panel">
          <span class="black-text">
               <blockquote>
                   <h5><c:out value="${driver.number}"/>
                   </h5>
                   <h5><c:out value="${driver.firstName}"/>
                   </h5>
                   <h5><c:out value="${driver.lastName}"/>
                   </h5>
               </blockquote>
              <c:if test="${assign}">
                    <h6>Assigned on order: <c:out value="${order.number}"/>
                    </h6>
                    <h6>Truck number : <c:out value="${order.truck.number}"/>
                    </h6>
                    <h6>Co-drivers :
                        <c:forEach var="drive" items="${drivers}" varStatus="loop">
                            <c:if test="${driver.number != drive.number}">
                                    <c:out value="${drive.number}"/>
                                    <c:out value="${drive.firstName}"/>
                                    <c:out value="${drive.lastName}"/>
                                    <c:out value="  "/>
                            </c:if>
                        </c:forEach>
                    </h6>
                    <h6>Route Points List</h6>
                                    <ul>
                                        <c:forEach var="cargo" items="${cargos}">
                                        <li>
                                            <div class="divider"></div>
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
               </c:if>
               <c:if test="${not assign}">
                    <h6>Do not assigned on order</h6>
               </c:if>
          </span>
            <div class="row center" style="margin-top:30px;">
                <a href="/logout" class="waves-effect waves-light btn"><i class="material-icons left">arrow_back</i>Logout</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>