<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css" />
</head>
<body>
    <%-- Session check using JSTL --%>
    <c:if test="${empty sessionScope.username}">
        <c:redirect url="${pageContext.request.contextPath}/login" />
    </c:if>

    <jsp:include page="header.jsp" />

    <div class="main-body">
        <div class="container">
	        <!-- Background Image -->
	        <div class="background-image"></div>
	
	        <!-- Overlay Content -->
	        <div class="overlay">
	            <!-- Title -->
	            <h1 class="title">MediCareDrone</h1>
	
	            <!-- Bullet Points -->
	            <ul class="bullet-points">
	                <li>For Timely Healthcare Delivery</li>
	                <li>For Life-Saving Technology</li>
	                <li>For Reliable Drone Operations</li>
	                <li>For Smarter Medical Solutions</li>
	            </ul>
	        </div>
    	</div>
    </div>

    <jsp:include page="footer.jsp" />
</body>
</html>