<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>About Us</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/aboutUs.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css" />
</head>
<body>
    <%-- Session check using JSTL --%>
    <c:if test="${empty sessionScope.username}">
        <c:redirect url="${pageContext.request.contextPath}/login" />
    </c:if>

    <jsp:include page="header.jsp" />

    <!-- Your content section with two images -->
    <div class="about-us-content">
        <img class="about-img left-img" src="${pageContext.request.contextPath}/resources/images/system/vtol1.png" alt="Left Image">
        <img class="about-img right-img" src="${pageContext.request.contextPath}/resources/images/system/vtol2.png" alt="Right Image">
    </div>


	<!-- Text content below images -->
    <div class="about-us-text">
        <h2>About Us</h2>
        <p>
            At MediCareDrone, we are driven by the mission to revolutionize healthcare access through innovative drone technology.
            Our project focuses on delivering essential medical equipment from central and district hospitals to remote health posts in rural areas where road transportation is limited or unreliable.
            Powered by an advanced VTOL (Vertical Take-Off and Landing) system, our drones are designed for long-distance autonomous flights, ensuring fast, reliable, and precise deliveries even in the most challenging terrains.
            By combining cutting-edge flight technology with smart logistics, we aim to bridge the healthcare delivery gap, especially during emergencies.
            MediCareDrone is committed to supporting frontline health workers and saving lives — one flight at a time.
        </p>
    </div>
    
    <jsp:include page="footer.jsp" />
</body>
</html>
