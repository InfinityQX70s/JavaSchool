<%--
  Created by IntelliJ IDEA.
  User: infinity
  Date: 20.02.16
  Time: 19:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
    $(document).ready(function(){
        $('.pickup,.unload').autocomplete({
            serviceUrl: '/api/cities'
        });
    });
</script>
<div class="row z-depth-2 warning white">
    <div class="row">
        <div class="input-field col s12">
            <input id="orderNumber" name="orderNumber" type="number" class="validate">
            <label for="orderNumber">Order Number</label>
        </div>
    </div>
</div>
<div class="row z-depth-2 white root" id="readroot">
    <div class="row">
        <a class="secondary-content disabled right-align light-blue-text text-accent-888 remove">
            <i class="material-icons">clear</i></a>
        <div class="input-field col s12">
            <input id="cargoNumber" name="cargoNumber" type="number" class="validate number">
            <label for="cargoNumber">Cargo Number</label>
        </div>
    </div>
    <div class="row">
        <div class="input-field col s6">
            <input id="cargoName" name="cargoName" type="text" class="validate">
            <label for="cargoName">Cargo Name</label>
        </div>
        <div class="input-field col s6">
            <input id="cargoWeight" name="cargoWeight" type="number" class="validate">
            <label for="cargoWeight">Cargo Weight</label>
        </div>
    </div>
    <div class="row">
        <div class="input-field col s6">
            <input id="pickup" name="pickup" type="text" class="validate pickup">
            <label for="pickup">PickUp</label>
        </div>
        <div class="input-field col s6">
            <input id="unload" name="unload" type="text" class="validate pickup">
            <label for="unload">UnLoad</label>
        </div>
    </div>
</div>
<span id="writeroot"></span>
<div class="row right-align">
    <a class="btn-floating btn-tiny waves-effect waves-light light-blue accent-999 add"><i
            class="material-icons">add</i></a>
</div>