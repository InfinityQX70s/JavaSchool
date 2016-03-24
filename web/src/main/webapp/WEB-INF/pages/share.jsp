<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Driver Info</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="/css/materialize.css" media="screen,projection"/>
    <script type="text/javascript" src="/js/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="/js/materialize.min.js"></script>
    <script src="https://api-maps.yandex.ru/1.1/index.xml" type="text/javascript"></script>
    <script type="text/javascript">
        YMaps.jQuery(function () {
            var map = new YMaps.Map(YMaps.jQuery("#YMapsID")[0]);
            map.setCenter(new YMaps.GeoPoint(37.61, 55.74), 6);
            var router = new YMaps.Router(
                    // Список точек, которые необходимо посетить
                    [
                        <c:forEach var="city" items="${cities}">
                        "<c:out value="${city}"/>",
                        </c:forEach>
                    ], [],
                    {viewAutoApply: true}
            );
            map.addOverlay(router);
        });
    </script>

</head>
<body background="/image/background.png">
<div class="row light-blue-text text-accent-999" style="margin-top:50px;">
    <div class="col s6 offset-s3">
        <div class="card-panel">
          <span>
              <c:if test="${not empty error}">
                  <h5><blockquote>${error}</blockquote></h5>
              </c:if>
               <blockquote>
                   <h5><c:out value="${driver.number}"/>  <c:out value="${driver.firstName}"/>  <c:out value="${driver.lastName}"/>
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
                  <div class="row">
                      <div class="col s12">
                          <ul class="tabs">
                              <li class="tab col s3"><a class="active" href="#YMapsID">Map</a></li>
                              <li class="tab col s3"><a href="#table">Table</a></li>
                          </ul>
                      </div>
                      <div id="YMapsID" style="width:580px;height:370px"></div>
                      <div id="table" style="width:580px;height:370px">
                          <table class="striped white">
                              <thead>
                              <tr>
                                  <th data-field="id">Number</th>
                                  <th data-field="pickup">Pickup</th>
                                  <th data-field="unload">Unload</th>
                                  <th data-field="name">Name</th>
                                  <th data-field="weight">Weight</th>
                                  <th data-field="status">Status</th>
                              </tr>
                              </thead>

                              <tbody>
                              <c:forEach var="cargo" items="${cargos}">
                                  <tr>
                                      <td><c:out value="${cargo.number}"/>
                                      </td>
                                      <td><c:out value="${cargo.pickup.city.name}"/>
                                      </td>
                                      <td><c:out value="${cargo.unload.city.name}"/>
                                      </td>
                                      <td><c:out value="${cargo.name}"/>
                                      </td>
                                      <td><c:out value="${cargo.weight}"/>
                                      </td>
                                      <td><c:if test="${fn:length(cargo.statusLogs) eq 0}">
                                          undefined
                                      </c:if>
                                          <c:if test="${fn:length(cargo.statusLogs) ne 0}">
                                              <c:forEach var="cargoStatusLog" items="${cargo.statusLogs}"
                                                         varStatus="loop">
                                                  <c:if test="${loop.last}">
                                                      <c:out value="${cargoStatusLog.status}"/>
                                                  </c:if>
                                              </c:forEach>
                                          </c:if>
                                      </td>
                                  </tr>
                              </c:forEach>
                              </tbody>
                          </table>
                      </div>
                  </div>
              </c:if>
               <c:if test="${not assign}">
                   <h6>Do not assigned on order</h6>
               </c:if>
          </span>
        </div>
    </div>
</div>
</body>
</html>