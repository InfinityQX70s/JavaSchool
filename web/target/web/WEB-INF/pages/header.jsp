<%--
  Created by IntelliJ IDEA.
  User: infinity
  Date: 14.02.16
  Time: 18:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title><%= request.getParameter("title")%></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <!--Import Google Icon Font-->
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!--Import materialize.css-->
    <link type="text/css" rel="stylesheet" href="/css/materialize.css" media="screen,projection"/>
    <link href="/css/smart_wizard.css" rel="stylesheet" type="text/css">
    <script src="https://api-maps.yandex.ru/1.1/index.xml" type="text/javascript"></script>
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="/js/materialize.min.js"></script>
    <script type="text/javascript" src="/js/jquery.smartWizard.js"></script>
    <!--Let browser know website is optimized for mobile-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <script type="text/javascript" >
        $(document).ready(function(){
            $('select').material_select();
            $('#wizard').smartWizard({contentURL:'/employee/order/add',onFinish:onFinishCallback});
            function onFinishCallback(){
                $('form#fullForm').submit();
            }
        });
        function moreFields() {
            var newFields = document.getElementById('readroot').cloneNode(true);
            var insertHere = document.getElementById('writeroot');
            insertHere.parentNode.insertBefore(newFields,insertHere);
        };
    </script>
</head>
