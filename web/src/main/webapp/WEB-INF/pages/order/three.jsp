<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: infinity
  Date: 20.02.16
  Time: 20:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row z-depth-2">
    <div id="YMapsID" style="width:740px;height:370px"></div>
    <div class="row">
        <div class="input-field col s6">
            <input id="duration" name="duration" type="text" class="validate">
        </div>
        <div class="input-field col s6">
            <input id="distance" name="distance" type="text" class="validate">
        </div>
    </div>
</div>
<% List<String> cities = (List<String>) request.getAttribute("cities");%>
<% String list = "";%>
<% for (String city : cities) { %>
<% list += "\""; %>
<% list += city; %>
<% list += "\""; %>
<% list += ","; %>
<% } %>
<script type="text/javascript">
        $("#YMapsID").click(function () {
            var map = new YMaps.Map(YMaps.jQuery("#YMapsID")[0]);
            map.setCenter(new YMaps.GeoPoint(37.61, 55.74), 6);
            var router = new YMaps.Router(
                    // Список точек, которые необходимо посетить
                    [<%=list%>], [],
                    {viewAutoApply: true}
            );
            map.addOverlay(router);
            YMaps.Events.observe(router, router.Events.Success, function () {
                var duration = document.getElementById('duration');
                duration.value = Math.ceil(router.getDuration() / 3600) + ' hours';
                var distance = document.getElementById('distance');
                distance.value = Math.ceil(router.getDistance() / 1000) + ' kilometers';
            });
    });
</script>

