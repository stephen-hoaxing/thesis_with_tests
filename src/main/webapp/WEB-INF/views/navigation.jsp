<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="d" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Nidal
  Date: 2017.10.24.
  Time: 21:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Navigation</title>
    <link rel="stylesheet" href="webjars/bootstrap/3.3.6/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <div class="from-group col-lg-6">
    <form action="getnavigationdetails" method="get">
    <p class="errorMsg">${errorMsg}</p>
        <label for="from">From: </label>
                    <select name="start" id="from" class="form-control">
                        <c:forEach var="room" items="${rooms}">
                            <option value="${room.id}">${room.name} (Room ${room.number})</option>
                        </c:forEach>
                        <c:forEach var="poi" items="${pois}">
                            <option value="${poi.id}">${poi.name}</option>
                        </c:forEach>
                    </select>
        <br>
        <label for="end">End: </label>
                    <select name="end" id="end" class="form-control">
                        <c:forEach var="room" items="${rooms}">
                            <option value="${room.id}">${room.name} (Room ${room.number})</option>
                        </c:forEach>
                        <c:forEach var="poi" items="${pois}">
                            <option value="${poi.id}">${poi.name}</option>
                        </c:forEach>
                    </select>
        <br>
        <input type="submit" value="OK" class="btn btn-success">
        <div class="checkbox">
            <label for="checkbox"><input id="checkbox" type="checkbox" value="true" name="isWheelchair">Navigation with disability</label>
        </div>
        </form>
    </div>
</div>
<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>