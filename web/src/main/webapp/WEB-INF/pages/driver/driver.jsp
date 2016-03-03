<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="Drivers"/>
</jsp:include>
<jsp:include page="../leftMenu.jsp">
    <jsp:param name="drivers" value="active z-depth-2"/>
    <jsp:param name="orders" value=""/>
    <jsp:param name="trucks" value=""/>
</jsp:include>
<p></p>
<table style="margin-top:40px;" class="striped z-depth-2 col s6 offset-s4 white">
    <thead>
    <tr>
        <th data-field="id">Number</th>
        <th data-field="first_name">First Name</th>
        <th data-field="last_name">Last Name</th>
        <th data-field="change"></th>
        <th data-field="delete"></th>
    </tr>
    </thead>

    <tbody>
    <c:forEach var="driver" items="${requestScope.drivers}">
        <tr>
            <td><c:out value="${driver.number}"/>
            </td>
            <td><c:out value="${driver.firstName}"/>
            </td>
            <td><c:out value="${driver.lastName}"/>
            </td>
            <c:if test="${empty driver.order}">
                <td>Free</td>
                <td>
                    <a href="/employee/driver/<c:out value="${driver.number}"/>/edit"
                       class="secondary-content light-blue-text text-accent-888">
                        <i class="material-icons">create</i>
                    </a>
                </td>
                <td>
                    <form action="/employee/driver/delete" method="post">
                        <input type="hidden" name="number" value="<c:out value="${driver.number}"/>">
                        <a class="secondary-content light-blue-text text-accent-888" style="margin-right:20px;"
                           onclick="parentNode.submit();">
                            <i class="material-icons">clear</i>
                        </a>
                    </form>
                </td>
            </c:if>
            <c:if test="${not empty driver.order}">
                <td>Assigned</td>
                <td>
                </td>
                <td>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>

</table>
<div class="col s6 offset-s4 right-align">
    <a href="/employee/driver/add" style="margin-top:5px;"
       class="right btn-floating btn-large waves-effect waves-light light-blue accent-999"><i
            class="material-icons">add</i></a>
</div>
<c:if test="${requestScope.pageCount ne 1}">
    <div class="col s6 offset-s4 center-align">
        <ul class="pagination text-white">
            <c:forEach begin="1" end="${requestScope.pageCount}" varStatus="loop">
                <c:if test="${requestScope.currentPage eq loop.index}">
                    <li class="active light-blue accent-999 white-text"><a
                            href="/employee/drivers?page=<c:out value="${loop.index}"/>"><c:out
                            value="${loop.index}"/></a></li>
                </c:if>
                <c:if test="${requestScope.currentPage ne loop.index}">
                    <li class="waves-effect waves-light light-blue accent-999 light-blue-text text-accent-888"><a
                            href="/employee/drivers?page=<c:out value="${loop.index}"/>"><c:out
                            value="${loop.index}"/></a></li>
                </c:if>
            </c:forEach>
        </ul>
    </div>
</c:if>
<jsp:include page="../footer.jsp"/>