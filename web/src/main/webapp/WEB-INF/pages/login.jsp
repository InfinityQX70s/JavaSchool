<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <!--Import Google Icon Font-->
    <title>Login</title>
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!--Import materialize.css-->
    <link type="text/css" rel="stylesheet" href="/css/materialize.css" media="screen,projection"/>
    <script type="text/javascript" src="/js/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="/js/materialize.min.js"></script>
    <c:if test="${not empty param.error}">
        <script type="text/javascript" >
            $(document).ready(function(){
                Materialize.toast("Invalid username or password", 4000);
            });
        </script>
    </c:if>
    <c:if test="${not empty param.sign_out}">
        <script type="text/javascript" >
            $(document).ready(function(){
                Materialize.toast("You have been logged out successfully", 4000);
            });
        </script>
    </c:if>
</head>

<body background="/image/background.png">
<br>
<br>
<br>
<br>
<div class="row">
    <div class="col col s4 offset-l4">
        <form action="/login" method="post">
        <div class="card white">
            <div class="card-content black-text">
                <div class="row">
                    <div class="col s12">
                        <ul class="tabs">
                            <li class="tab col s3"><a class="active" href="#manager">Manager</a></li>
                            <li class="tab col s3"><a href="#driver">Driver</a></li>
                        </ul>
                    </div>
                    <div id="manager" class="col s12">
                        <div class="row">
                            <div class="input-field col s12">
                                <input id="email" name="email" type="email" class="validate">
                                <label for="email">Email</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="input-field col s12">
                                <input id="password" type="password" name="password" class="validate">
                                <label for="password">Password</label>
                            </div>
                        </div>
                    </div>
                    <div id="driver" class="col s12">
                        <div class="row">
                            <div class="input-field col s12">
                                <input id="number" type="text" name="number" class="validate">
                                <label for="number">Number</label>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card-action center">
                    <a href="#" onclick="document.forms[0].submit();">Sign in</a>
                </div>
            </div>
        </div>
            <%--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
        </form>
    </div>
</div>
</body>
</html>
