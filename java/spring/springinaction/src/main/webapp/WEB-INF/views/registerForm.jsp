<%@ taglib uri="http://www.springframework.org/tags" prefix="c" %>
<%@ page session="false" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Spitter Register</title>
    <link rel="stylesheet" href="<c:url value="/style/style.css"/>">
</head>
<body>
<form method="post">
    <div class="control-item">
        <label for="username">Username</label>
        <div class="control-content">
            <input type="text" id="username" name="username">
        </div>
    </div>
    <div class="control-item">
        <label for="password">Password</label>
        <div class="control-content">
            <input type="text" id="password" name="password">
        </div>
    </div>
    <div class="control-item">
        <div class="control-content">
            <button class="submit">Submit</button>
        </div>
    </div>
</form>
</body>
</html>
