<%--
  Created by IntelliJ IDEA.
  User: infinity
  Date: 14.02.16
  Time: 18:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="Trucks"/>
</jsp:include>
<jsp:include page="../leftMenu.jsp">
    <jsp:param name="drivers" value=""/>
    <jsp:param name="orders" value=""/>
    <jsp:param name="trucks" value="class=\"active z-depth-2\""/>
</jsp:include>
<p></p>
<div class="row">
    Truck

</div>
<div class="row col s7">
    <a class="right btn-floating btn-large waves-effect waves-light red "><i class="material-icons">add</i></a>
</div>
<jsp:include page="../footer.jsp"/>