<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Registration</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register.css">
</head>
<body>
    <div class="card">
        <h2>Registration Form</h2>
        <%-- Display error message if present --%>
        <c:if test="${not empty error}">
            <p style="color:red">${error}</p>
        </c:if>
        <form action="${pageContext.request.contextPath}/register" method="post">
            <div class="row">
                <div class="col">
                    <label for="firstName">First Name:</label>
                    <input type="text" id="firstName" name="firstName" value="${firstName != null ? firstName : ''}" required>
                </div>
                <div class="col">
                    <label for="lastName">Last Name:</label>
                    <input type="text" id="lastName" name="lastName" value="${lastName != null ? lastName : ''}" required>
                </div>
            </div>
            
            <div class="row">
                <div class="col">
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" value="${username != null ? username : ''}" required>
                </div>
                <div class="col">
                    <label for="phone">Phone Number:</label>
                    <input type="tel" id="phone" name="phone" value="${phone != null ? phone : ''}" required>
                </div>
            </div>
            
            <div class="row">
                <div class="col">
                    <label for="gender">Gender:</label>
                    <select id="gender" name="gender" required>
                        <option value="male" ${gender == 'male' ? 'selected' : ''}>Male</option>
                        <option value="female" ${gender == 'female' ? 'selected' : ''}>Female</option>
                    </select>
                </div>
                <div class="col">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" value="${email != null ? email : ''}" required>
                </div>
            </div>
            
            
            <div class="row">
                <div class="col">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <div class="col">
                    <label for="retypePassword">Retype Password:</label>
                    <input type="password" id="retypePassword" name="retypePassword" required>
                </div>
            </div>
            
            <button type="submit" class="submit-btn">Submit</button>
        </form>
    </div>
</body>
</html>