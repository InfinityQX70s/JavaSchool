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
<div class="row col s6 z-depth-2 offset-s4" style="margin-top:50px;">
    <div class="row">
        <div class="input-field col s12">
            <input id="number" type="text" class="validate">
            <label for="number">Order Number</label>
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
            <input id="number" type="text" class="validate">
            <label for="number">Cargo Number</label>
        </div>
    </div>
    <div class="row">
        <div class="input-field col s6">
            <input id="first_name" type="text" class="validate">
            <label for="first_name">Cargo Name</label>
        </div>
        <div class="input-field col s6">
            <input id="last_name" type="text" class="validate">
            <label for="last_name">Cargo Weight</label>
        </div>
    </div>
    <div class="row">
        <div class="input-field col s6">
            <input id="first_name" type="text" class="validate">
            <label for="first_name">PickUp</label>
        </div>
        <div class="input-field col s6">
            <input id="last_name" type="text" class="validate">
            <label for="last_name">UnLoad</label>
        </div>
    </div>
</div>

<!--Кнопка-->
<span id="writeroot"></span>
<div class="row col s6 offset-s4 right-align">
    <button class="btn waves-effect waves-light" type="submit" name="action" style="margin-right:210px;">Submit</button>
    <a class="btn-floating btn-tiny waves-effect waves-light red" style="margin-bottom:70px;"
        onclick="moreFields()"><i class="material-icons">add</i></a>
</div>

<script type="text/javascript">
    function moreFields() {
        var newFields = document.getElementById('readroot').cloneNode(true);
        var insertHere = document.getElementById('writeroot');
        insertHere.parentNode.insertBefore(newFields, insertHere);
    }
</script>
<jsp:include page="../footer.jsp"/>