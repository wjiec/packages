<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Spittle-<c:out value="${spittle.id}"/></title>
    <link rel="stylesheet" href="<s:url value="/style/style.css"/>">
</head>
<body>

<div class="spittle-wrapper">
    <div class="spittle-content">
        <p><c:out value="${spittle.content}"/></p>
    </div>
    <div class="spittle-metadata">
        <span><c:out value="${spittle.createdAt}"/></span>
        <span>(<c:out value="${spittle.latitude}"/>, <c:out value="${spittle.longitude}"/>)</span>
    </div>
</div>

</body>
</html>
