<%--
  Created by IntelliJ IDEA.
  User: infinity
  Date: 17.02.16
  Time: 21:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Error</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="/css/materialize.css" media="screen,projection"/>
    <script type="text/javascript" src="/js/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="/js/materialize.min.js"></script>
</head>
<body>
<div class="row" style="margin-top:50px;">
    <div class="col s6 offset-s3">
        <div class="card-panel">
          <span class="black-text">
                 <blockquote>
                     <h5><c:out value="${requestScope.errorMessage}"/></h5>
                 </blockquote>
          </span>
            <div class="row center" style="margin-top:30px;">
                <button class="btn waves-effect waves-light" onclick="window.history.back();">Back
                    <i class="material-icons left">arrow_back</i>
                </button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
