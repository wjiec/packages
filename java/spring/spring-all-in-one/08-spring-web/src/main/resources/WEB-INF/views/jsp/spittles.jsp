<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Spittles | Spittr</title>
    <link rel="stylesheet" href="<s:url value="/style/style.css"/>">
</head>
<body>
    <h2>Recent Spittles</h2>
    <ul>
        <c:forEach items="${spittleList}" var="spittle">
            <li id="spittle-<c:out value="${spittle.id}" />">
                <div class="spittle-wrapper">
                    <div class="spittle-content">
                        <a href="/spittle/<c:out value="${spittle.id}"/>"><p><c:out value="${spittle.content}"/></p></a>
                    </div>
                    <div class="spittle-metadata" style="color: gray">
                        <span><c:out value="${spittle.createdAt}"/></span>
                        <span>(<c:out value="${spittle.latitude}"/>, <c:out value="${spittle.longitude}"/>)</span>
                    </div>
                </div>
            </li>
        </c:forEach>
    </ul>
</body>
</html>
