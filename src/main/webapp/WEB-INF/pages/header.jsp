<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="header">
    <header class="header">
        <!-- Logo linking to home -->
        <h1 class="logo">
            <a href="${pageContext.request.contextPath}/home1">
                <img src="${pageContext.request.contextPath}/resources/images/system/logo.png" alt="MediCareDrone Logo" />
            </a>
        </h1>

        <!-- Middle welcome message (static or could be dynamic later) -->
        <div class="header-middle-content">
            <p>Welcome to MediCareDrone!</p>
        </div>

        <!-- Navigation Menu -->
        <ul class="main-nav">
            <li><a href="${pageContext.request.contextPath}/home1">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/Explore">Explore</a></li>
 
            
            <c:choose>
			    <c:when test="${sessionScope.username == 'admin'}">
			        <li><a href="${pageContext.request.contextPath}/Dashboard">Dashboard</a></li>
			    </c:when>
			    <c:otherwise>
			        <li><a href="${pageContext.request.contextPath}/DashboardUser">Dashboard</a></li>
			    </c:otherwise>
			</c:choose>
			            
            
            
            <li><a href="${pageContext.request.contextPath}/AboutUs">About</a></li>
            <li><a href="${pageContext.request.contextPath}/Portfolio">Portfolio</a></li>
            <li><a href="${pageContext.request.contextPath}/ContactUs">Contact</a></li>

            <!-- Login/Logout Logic -->
            <c:choose>
			    <%-- If user is logged in --%>
			    <c:when test="${sessionScope.username != null}">
			        <li><form action="${pageContext.request.contextPath}/logout" method="post">
			            <button type="submit" class="logout-button">Logout</button>
			        </form></li>
			    </c:when>
			    
			    <%-- If user is not logged in --%>
			    <c:otherwise>
			        <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
			    </c:otherwise>
			</c:choose>

        </ul>
    </header>
</div>
