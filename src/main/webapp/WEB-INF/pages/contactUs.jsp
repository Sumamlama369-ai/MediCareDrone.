<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Contact Us</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/contactUs.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css" />
</head>
<body>
    <%-- Session check using JSTL --%>
    <c:if test="${empty sessionScope.username}">
        <c:redirect url="${pageContext.request.contextPath}/login" />
    </c:if>

    <jsp:include page="header.jsp" />
    <div class="container">
        <!-- Background Image -->
        <div class="background-image"></div>

        <!-- Overlay Content -->
        <div class="overlay">
            <!-- Logo and Title -->
            <div class="contact-left">
                <h1 class="title">MediCareDrone</h1>
                <img src="${pageContext.request.contextPath}/resources/images/system/contact.png" alt="Drone Image" class="drone-image">
            </div>

            <!-- Get in Touch Section -->
            <div class="contact-right">
                <h2 class="subtitle">GET IN TOUCH</h2>
                <ul class="contact-details">
                    <li>Location :</li>
                    <li>Putalisadak, Nepal</li>
                    <li>Email Address :</li>
                    <li>SL2296450@gmail.com</li>
                    <li>Phone Number :</li>
                    <li>+977-9803729956</li>
                </ul>
            </div>
        </div>
    </div>
    <jsp:include page="footer.jsp" />
</body>
</html>