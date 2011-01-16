<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style type="text/css">
            .user {
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <c:forEach var="status" items="${it}">
            <span class="user">${status.user.name}</span>: <span class="msg">${status.text}</span><br/>
        </c:forEach>
    </body>
</html>
