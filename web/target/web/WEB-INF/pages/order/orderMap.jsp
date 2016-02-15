<%--
  Created by IntelliJ IDEA.
  User: infinity
  Date: 15.02.16
  Time: 22:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <div id="YMapsID" style="width:620px;height:400px" class="center"></div>
    <div class="row">
        <div class="input-field col s6">
            <input id="duration" type="text" class="validate">
        </div>
        <div class="input-field col s6">
            <input id="distance" type="text" class="validate">
        </div>
    </div>
</div>
<div class="row col s6 offset-s4 right-align">
    <button class="btn waves-effect waves-light" type="submit" name="action" style="margin-right:250px;">Submit</button>
</div>
<script type="text/javascript">
    // Создание обработчика для события window.onLoad
    YMaps.jQuery(function () {
        // Создание экземпляра карты и его привязка к созданному контейнеру
        var map = new YMaps.Map(YMaps.jQuery("#YMapsID")[0]);
        map.setCenter(new YMaps.GeoPoint(37.61, 55.74), 6);
        var router = new YMaps.Router(
                // Список точек, которые необходимо посетить
                ["Москва", "Орел", "Махачкала", "Тула",], [],
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
<jsp:include page="../footer.jsp"/>
