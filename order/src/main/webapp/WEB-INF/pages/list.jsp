<%--
  Created by IntelliJ IDEA.
  User: xiaobao
  Date: 2016/6/1
  Time: 13:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
</head>
<body>

<table>
  <tr>
    <td>id</td><td>orderName</td><td>createTime</td>
  </tr>
  <c:forEach items="${list}" var="item">
    <tr>
      <td>${item.id}</td><td>${item.orderName}</td><td>${item.createTime}</td>
    </tr>
  </c:forEach>
</table>

</body>
</html>
