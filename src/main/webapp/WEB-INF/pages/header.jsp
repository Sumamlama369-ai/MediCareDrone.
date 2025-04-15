<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="header">
	<header class="header">
		<h1 class="logo">
			<a href=""><img src="${pageContext.request.contextPath}/resources/images/system/logo.png" alt="MediCareDrone Logo" /></a>
		</h1>
		
		<!-- New element added between logo and navigation -->
        <div class="header-middle-content">
            <!-- Add your content here, for example: -->
            <p>Welcome to MediCareDrone!</p>
        </div>
		
		<ul class="main-nav">
			<li><a href="${pageContext.request.contextPath}/home1">Home</a></li>
			<li><a href="${pageContext.request.contextPath}/AboutUs">About</a></li>
			<li><a href="${pageContext.request.contextPath}/Portfolio">Portfolio</a></li>
			<li><a href="#">Contact</a></li>
			<li><a href="${pageContext.request.contextPath}/login">Login</a></li>
		</ul>
	</header>
</div>