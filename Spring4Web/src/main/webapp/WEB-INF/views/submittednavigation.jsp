<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="d" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Nidal
  Date: 2017.10.24.
  Time: 21:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Navigation details</title>
    <link rel="stylesheet" href="webjars/bootstrap/3.3.6/css/bootstrap.min.css">
</head>
<body>
<br><br>
<div class="container col-lg-6">
    <div class="btn-group">
        <a href="navigate" class="btn btn-success">Back to Navigation</a>
        <a href="roomlist" class="btn btn-success">Back to Rooms</a>
    </div>
    <br><br>
    <c:if test="${empty stations}">
        <div class="alert alert-warning">The two endpoints are not connected</div>
    </c:if>
    <ul class="list-group">
        <c:forEach var="station" items="${stations}">
            <li class="list-group-item">${station}</li>
        </c:forEach>
    </ul>
    <br><br>
</div>
<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>
