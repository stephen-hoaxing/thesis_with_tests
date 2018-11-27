<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="d" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Nidal
  Date: 2017.10.23.
  Time: 21:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Room Info</title>
    <link rel="stylesheet" href="webjars/bootstrap/3.3.6/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <br><br>
    <table class="table table-bordered">
        <tr>
            <td>Room number:</td>
            <td>${room.number}<c:if test="${room.isAccessibleByWheelchair()}"> &#x267f;</c:if></td>
        </tr>
        <tr>
            <td>Room type:</td>
            <td>${room.roomType}</td>
        </tr>
        <tr>
            <td>Room name:</td>
            <td>${room.name}</td>
        </tr>
        <tr>
            <td>POIs</td>
            <td>
                <c:forEach var="poi" items="${room.pois}">
                    ${poi.name}<br>
                </c:forEach>
            </td>
        </tr>
        <c:if test="${not empty room.getRoomEquipments()}">
        <tr>
            <td>Room Equipments</td>
            <td>
                <c:forEach var="roomEquipment" items="${room.roomEquipments}">
                    Name: ${roomEquipment.name}<br>
                    Width: ${roomEquipment.width}m<br>
                    Height: ${roomEquipment.height}m<br>
                    Quantity: ${roomEquipment.quantity}<br>
                </c:forEach>
            </td>
        </tr>
        </c:if>
    </table>
</div>
<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>
