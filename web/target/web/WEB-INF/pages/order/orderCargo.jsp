<%--
  Created by IntelliJ IDEA.
  User: infinity
  Date: 20.02.16
  Time: 19:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="orderAndCargoForm" method="post">
<div class="row z-depth-2 warning white">
    <div class="row">
        <div class="input-field col s12">
            <input id="orderNumber" name="orderNumber" type="text" class="validate">
            <label for="orderNumber">Order Number</label>
        </div>
    </div>
</div>
<div class="row z-depth-2 white" id="readroot">
    <div class="row">
        <a class="secondary-content disabled right-align light-blue-text text-accent-888"
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
<span id="writeroot"></span>
<div class="row right-align">
    <a class="btn-floating btn-tiny waves-effect waves-light light-blue accent-999" onclick="moreFields()"><i
            class="material-icons">add</i></a>
</div>
</form>