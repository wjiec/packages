<%@ taglib uri="http://www.springframework.org/tags" prefix="c" %>
<%@ page session="false" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Spittr Home</title>
    <link rel="stylesheet" href="<c:url value="/style/style.css"/>">
</head>
<body>
    <h1>Welcome to Spittr</h1>

    <div>
        <a href="<c:url value="/spittles"/>">Spittles</a> |
        <a href="<c:url value="/spitter/register"/>">Register</a>
    </div>
</body>
</html>
