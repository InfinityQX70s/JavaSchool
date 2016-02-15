<%--
  Created by IntelliJ IDEA.
  User: infinity
  Date: 14.02.16
  Time: 18:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="Orders"/>
</jsp:include>
<jsp:include page="../leftMenu.jsp">
    <jsp:param name="drivers" value=""/>
    <jsp:param name="orders" value="class=\"active z-depth-2\""/>
    <jsp:param name="trucks" value=""/>
</jsp:include>
<p></p>
<div class="row">

    Orders
</div>
<div class="row col s7">
    <a href="/employee/order/add" class="right btn-floating btn-large waves-effect waves-light red "><i class="material-icons">add</i></a>
</div>
<jsp:include page="../footer.jsp"/>
