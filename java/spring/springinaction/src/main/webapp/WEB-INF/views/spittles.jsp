<%@ taglib uri="http://www.springframework.org/tags" prefix="c" %>
<%@ page session="false" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Spittles | Spittr</title>
</head>
<body>
    <h2>Recent Spittles</h2>
    <ul>
        <c:forEach items="$(spittleList)" var="spittle">
            <li id="spittle-<c:out value="spittle.id" />"></li>
        </c:forEach>
    </ul>
</body>
</html>
