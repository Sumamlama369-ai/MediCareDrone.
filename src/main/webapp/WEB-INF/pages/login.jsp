<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login to your account</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css" />
</head>
<body>
    <!-- Logo Container -->
    <div class="logo-container">
        <img src="/demo1/resources/images/system/logo.png" alt="Logo" class="logo">
    </div>

    <!-- Login Form Container -->
    <div class="login-container">
        <!-- If an error exists, show the error message -->
        <c:if test="${not empty error}">
            <div style="color: red; font-weight: bold;">
                ${error}
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/login" method="POST">
            <div class="row">
                <div class="col">
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" required>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required>
                </div>
            </div>
            <button type="submit" class="login-button">Login</button>
        </form>
    </div>
</body>
</html>