<%@ taglib uri="http://www.springframework.org/tags" prefix="c" %>
<%@ taglib prefix="sp" uri="http://www.springframework.org/tags/form" %>
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
<%--@elvariable id="spitterDTO" type="com.wjiec.tinder.springinaction.spittr.dto.SpitterDTO"--%>
<sp:form modelAttribute="spitterDTO" method="post">
    <div class="control-item">
        <sp:label path="username">Username</sp:label>
        <div class="control-content">
            <sp:input path="username" cssErrorClass="error-control"/>
            <sp:errors path="username" cssClass="error-message" />
        </div>
    </div>
    <div class="control-item">
        <sp:label path="password">Password</sp:label>
        <div class="control-content">
            <sp:password path="password" cssErrorClass="error-control"/>
            <sp:errors path="password" cssClass="error-message" />
        </div>
    </div>
    <div class="control-item">
        <div class="control-content">
            <button class="submit">Submit</button>
        </div>
    </div>
</sp:form>
</body>
</html>
