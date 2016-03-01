<%--
  Created by IntelliJ IDEA.
  User: infinity
  Date: 20.02.16
  Time: 19:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="Orders"/>
</jsp:include>
<jsp:include page="../leftMenu.jsp">
    <jsp:param name="drivers" value=""/>
    <jsp:param name="orders" value="active z-depth-2"/>
    <jsp:param name="trucks" value=""/>
</jsp:include>
<div class="row col s7 offset-s3" >
<form id="fullForm" action="/employee/order/submit" method="post">
<div id="wizard" class="swMain">
    <ul>
        <li><a href="#step-1">
            <label class="stepNumber">1</label>
             <span class="stepDesc">Step 1<br/><small>Order and Cargos</small></span>
        </a></li>
        <li><a href="#step-2">
            <label class="stepNumber">2</label>
            <span class="stepDesc">Step 2<br/><small>Truck</small></span>
        </a></li>
        <li><a href="#step-3">
            <label class="stepNumber">3</label>
            <span class="stepDesc">Step 3<br/><small>Map</small></span>
        </a></li>
        <li><a href="#step-4">
            <label class="stepNumber">4</label>
            <span class="stepDesc">Step 4<br/><small>Drivers</small></span>
        </a></li>
    </ul>
    <div id="step-1">

    </div>

    <div id="step-2">

    </div>

    <div id="step-3">

    </div>

    <div id="step-4">

    </div>
</div>
</form>
</div>
<jsp:include page="../footer.jsp"/>