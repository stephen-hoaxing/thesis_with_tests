<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="d" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Nidal
  Date: 2017.10.22.
  Time: 20:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Room details</title>
    <link rel="stylesheet" href="webjars/bootstrap/3.3.6/css/bootstrap.min.css">
</head>
<body>
    <c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
    <div class="container">
        <br><br>
        <a href="navigate" class="btn btn-success">Navigation</a>
        <br><br>
        <table class="table table-striped table-bordered">
            <tr>
                <th>Name</th>
                <th>POIs</th>
            </tr>
            <c:forEach var="room" items="${rooms}">
                <tr>
                    <td><a href="${contextPath}/roominfo?roomid=${room.id}">${room.name}</a></td>
                    <td>
                        <ul class="list-group">
                            <c:forEach var="poi" items="${room.pois}">
                                <li class="list-group-item"><a href="${contextPath}/poiinfo?poiid=${poi.id}">${poi.name}</a></li>
                            </c:forEach>
                        </ul>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>
