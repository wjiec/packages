<%@ taglib uri="http://www.springframework.org/tags" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="d" %>
<%@ page session="false" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Spittles | Spittr</title>
    <link rel="stylesheet" href="<c:url value="/style/style.css"/>">
</head>
<body>
    <h2>Recent Spittles</h2>
    <ul>
        <d:forEach items="${spittleList}" var="spittle">
            <li id="spittle-<d:out value="${spittle.id}" />">
                <div class="spittle-wrapper">
                    <div class="spittle-content">
                        <a href="/spittle/<d:out value="${spittle.id}"/>"><p><d:out value="${spittle.content}"/></p></a>
                    </div>
                    <div class="spittle-metadata" style="color: gray">
                        <span><d:out value="${spittle.createdAt}"/></span>
                        <span>(<d:out value="${spittle.latitude}"/>, <d:out value="${spittle.longitude}"/>)</span>
                    </div>
                </div>
            </li>
        </d:forEach>
    </ul>
</body>
</html>
