<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Spitter Register</title>
    <link rel="stylesheet" href="<s:url value="/style/style.css"/>">
</head>
<body>
<%--@elvariable id="spitterDTO" type="com.wjiec.tinder.springinaction.spittr.dto.SpitterDTO"--%>
<sf:form modelAttribute="spitterDTO" method="post" enctype="multipart/form-data">
    <div class="control-item">
        <sf:label path="username">Username</sf:label>
        <div class="control-content">
            <sf:input path="username" cssErrorClass="error-control"/>
            <sf:errors path="username" cssClass="error-message" />
        </div>
    </div>
    <div class="control-item">
        <sf:label path="password">Password</sf:label>
        <div class="control-content">
            <sf:password path="password" cssErrorClass="error-control"/>
            <sf:errors path="password" cssClass="error-message" />
        </div>
    </div>
    <div class="control-item">
        <label for="avatar">Avatar</label>
        <div class="control-content">
            <input type="file" id="avatar" name="avatar"/>
        </div>
    </div>
    <div class="control-item">
        <div class="control-content">
            <button class="submit">Submit</button>
        </div>
    </div>
</sf:form>
</body>
</html>
