<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="d" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Nidal
  Date: 2017.10.23.
  Time: 16:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>POI Details</title>
    <link rel="stylesheet" href="webjars/bootstrap/3.3.6/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <br><br>
    <table class="table table-bordered">
        <tr>
            <th>Name</th>
            <th>Connected POIs</th>
        </tr>
        <tr>
            <td>${poi.name}</td>
            <td>
                <c:forEach var="p" items="${poi.pois}">
                    ${p.name}<br>
                </c:forEach>
            </td>
        </tr>
    </table>
</div>
<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>
