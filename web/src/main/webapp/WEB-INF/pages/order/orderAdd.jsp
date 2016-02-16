<%--
  Created by IntelliJ IDEA.
  User: infinity
  Date: 15.02.16
  Time: 18:23
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
<!--Заказ-->
<form action="/employee/order/add" method="post">
    <div class="row col s6 z-depth-2 offset-s4" style="margin-top:50px;">
        <div class="row">
            <div class="input-field col s12">
                <input id="orderNumber" name="orderNumber" type="text" class="validate">
                <label for="orderNumber">Order Number</label>
            </div>
        </div>
    </div>

    <!--Груз-->
    <div class="row col s6 z-depth-2 offset-s4" id="readroot">
        <div class="row">
            <a class="secondary-content disabled right-align" style="margin-top:20px;"
               onclick="this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);"><i
                    class="material-icons">clear</i></a>
            <div class="input-field col s12">
                <input id="cargoNumber" name="cargoNumber" type="text" class="validate">
                <label for="cargoNumber">Cargo Number</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s6">
                <input id="cargoName" name="cargoName" type="text" class="validate">
                <label for="cargoName">Cargo Name</label>
            </div>
            <div class="input-field col s6">
                <input id="cargoWeight" name="cargoWeight" type="text" class="validate">
                <label for="cargoWeight">Cargo Weight</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s6">
                <input id="pickup" name="pickup" type="text" class="validate">
                <label for="pickup">PickUp</label>
            </div>
            <div class="input-field col s6">
                <input id="unload" name="unload" type="text" class="validate">
                <label for="unload">UnLoad</label>
            </div>
        </div>
    </div>

    <!--Кнопка-->
    <span id="writeroot"></span>
    <div class="row col s6 offset-s4 right-align">
        <button class="btn waves-effect waves-light" type="submit" name="action" style="margin-right:210px;">Submit
        </button>
        <a class="btn-floating btn-tiny waves-effect waves-light red" style="margin-bottom:70px;"
           onclick="moreFields()"><i class="material-icons">add</i></a>
    </div>
</form>
<script type="text/javascript">
    function moreFields() {
        var newFields = document.getElementById('readroot').cloneNode(true);
        var insertHere = document.getElementById('writeroot');
        insertHere.parentNode.insertBefore(newFields, insertHere);
    }
</script>
<jsp:include page="../footer.jsp"/>