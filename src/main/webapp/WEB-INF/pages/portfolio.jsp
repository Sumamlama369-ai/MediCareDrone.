<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/portfolio.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css" />
</head>
<body>
        
        
     <jsp:include page="header.jsp" />
	
	<div class="main-body">
		<div class="container">
        <div class="profile-pic">
            <div class="circle">
                <span class="icon">👤</span>
                <div class="edit-icon">
                    <span class="pencil">✏️</span>
                </div>
            </div>
        </div>
        <div class="max-size">MAX IMAGE SIZE = 1MB</div>

        <div class="form-group">
            <label>Full Name</label>
            <input type="text" value="Suman Lama" readonly>
        </div>

        <div class="form-group">
            <label>Nick Name</label>
            <input type="text" value="Suman_Lama">
        </div>

        <div class="form-group">
            <label>Email Address</label>
            <div class="info">suman.lama@islingtoncollege.edu.np</div>
            <div class="note">
                This is your primary email address and will be used to send notification emails.
                <a href="#">Change Email Address</a>
            </div>
        </div>

        <div class="form-group">
            <label>Location</label>
            <input type="text" value="Kathmandu, Nepal">
        </div>

        <div class="form-group">
            <label>School</label>
            <div class="school-id">
                <div class="info">Islington College Kathmandu</div>
                <!-- <span class="id">27/28</span> -->
            </div>
        </div>

        <button class="save-btn">Save Changes</button>
    </div>
	</div>
	
	<jsp:include page="footer.jsp" />
</body>
</html>