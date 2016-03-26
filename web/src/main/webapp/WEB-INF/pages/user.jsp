<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>User</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="/css/materialize.css" media="screen,projection"/>
    <script type="text/javascript" src="/js/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="/js/materialize.min.js"></script>
</head>
<body background="/image/background.png">
<c:if test="${not empty message}">
    <script type="text/javascript">
        $(document).ready(function () {
            Materialize.toast("${message}", 4000);
        });
    </script>
</c:if>
<p></p>
<div class="row light-blue-text text-accent-999">
    <div class="col s3">
    </div>
    <table style="margin-top:40px;" class="striped z-depth-2 col s6 offset-s3 white">
        <thead>
        <tr>
            <th data-field="email">Email</th>
            <th data-field="delete"></th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="user" items="${requestScope.users}">
            <tr>
                <td><c:out value="${user.email}"/>
                </td>
                <td>
                    <form action="/admin/user/delete" method="post">
                        <input type="hidden" name="email" value="<c:out value="${user.email}"/>">
                        <a class="secondary-content light-blue-text text-accent-888" style="margin-right:20px;"
                           onclick="parentNode.submit();">
                            <i class="material-icons">clear</i>
                        </a>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="col s6 offset-s3 right-align">
        <a href="/admin/user/add" style="margin-top:5px;"
           class="right btn-floating btn-large waves-effect waves-light light-blue accent-999"><i
                class="material-icons">add</i></a>
    </div>
    <div class="col s6 offset-s3 center-align">
        <a href="/logout" class="waves-effect waves-light btn light-blue accent-999 light-blue-text text-accent-999"><i
                class="material-icons left">arrow_back</i>Logout</a>
    </div>
</div>
</body>
</html>