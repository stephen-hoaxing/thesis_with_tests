<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="d" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Nidal
  Date: 2017.10.22.
  Time: 21:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${organization.name} details</title>
    <link href="<d:url value="/resources/basic.css" />" rel="stylesheet">
</head>
<body>
    <table border="1" class="fancy-table">
        <tr>
            <th>Name</th>
            <th>Employees</th>
        </tr>
        <tr>
            <td>${organization.name}</td>
            <td>
                <ul>
                    <c:forEach var="employee" items="${organization.employees}">
                        <li>${employee.name}</li>
                    </c:forEach>
                </ul>
            </td>
        </tr>
    </table>
</body>
</html>
