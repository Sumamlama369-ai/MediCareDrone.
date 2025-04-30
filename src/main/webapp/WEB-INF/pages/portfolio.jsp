<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.MediCareDrone.model.UserModel" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Portfolio</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/portfolio.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css" />
</head>
<body>

<jsp:include page="header.jsp" />

<div class="main-body">
    <img src="${pageContext.request.contextPath}/resources/images/system/vector.png" class="portfolio-image" alt="Portfolio Image">
    
    <div class="container">
        <div class="profile-pic">
            <div class="circle">
                <span class="icon">👤</span>
                <div class="edit-icon">
                    <span class="pencil">✏️</span>
                </div>
            </div>
        </div>

        <form action="${pageContext.request.contextPath}/Portfolio" method="post">
            <!-- Hidden Username (to identify the record to update) -->
            <input type="hidden" name="username" value="${user.username}"/>

            <div class="form-group">
                <label>First Name</label>
                <input type="text" name="firstName" value="${user.firstName}" required>
            </div>

            <div class="form-group">
                <label>Last Name</label>
                <input type="text" name="lastName" value="${user.lastName}" required>
            </div>

            <div class="form-group">
                <label>Email Address</label>
                <div class="info">${user.email}</div>
            </div>

            <div class="form-group">
                <label>Phone Number</label>
                <input type="text" value="${user.phone}" readonly>
            </div>

            <div class="form-group">
                <label>Username</label>
                <div class="school-id">
                    <div class="info">${user.username}</div>
                </div>
            </div>

            <div class="form-group">
                <label>Gender</label>
                <input type="text" value="${user.gender}" readonly>
            </div>

            <button class="save-btn">Update</button>
        </form>
    </div>
</div>

<jsp:include page="footer.jsp" />

</body>
</html>
