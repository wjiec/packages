<%@ taglib uri="http://www.springframework.org/tags" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="d" %>
<%@ page session="false" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Spittle-<d:out value="${spittle.id}"/></title>
    <link rel="stylesheet" href="<c:url value="/style/style.css"/>">
</head>
<body>

<div class="spittle-wrapper">
    <div class="spittle-content">
        <p><d:out value="${spittle.content}"/></p>
    </div>
    <div class="spittle-metadata">
        <span><d:out value="${spittle.createdAt}"/></span>
        <span>(<d:out value="${spittle.latitude}"/>, <d:out value="${spittle.longitude}"/>)</span>
    </div>
</div>

</body>
</html>
