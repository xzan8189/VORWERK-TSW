<%@page import="java.util.ArrayList"%>
<%@page import="it.unisa.model.Robot"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	ArrayList<Robot> lastRobot = (ArrayList<Robot>) request.getAttribute("lastRobot");
Boolean isAdmin = (Boolean) session.getAttribute("admin");
String username = (String) session.getAttribute("user");
Boolean account = (Boolean) session.getAttribute("account");

//carico gli ultimi 3 robot
if (lastRobot == null) {
	response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/AccountController?action=lastRobot"));
	return;
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="author" content="Vincenzo Marrazzo">
<link rel="icon" type="image/png"
	href="<%=request.getContextPath()%>/images/Seraph.png">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
	crossorigin="anonymous">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>VORWERK</title>
</head>
<body>

	<!-- Nav bar -->
	<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
		<%
			if (username == null || username.equals("")) {
		%><a class="navbar-brand"
			href="<%=request.getContextPath()%>/login.jsp">Log in</a>
		<%
			} else {
		%><a class="navbar-brand" href="<%=request.getContextPath()%>/Logout">Log
			out</a>
		<!-- si deve ancora fare la servlet per log out account -->
		<%
			}
		%>

		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNavAltMarkup">
			<div class="navbar-nav">
				<a class="nav-item nav-link active"
					href="<%=request.getContextPath()%>/account/account.jsp">Home <span
					class="sr-only">(current)</span></a> <a class="nav-item nav-link"
					href="<%=request.getContextPath()%>/account/robot.jsp">Robot</a> <a
					class="nav-item nav-link"
					href="<%=request.getContextPath()%>/account/premi.jsp">Premi</a>
				<%
					if (account != null && account) {
				%>
				<a class="nav-item nav-link"
					href="<%=request.getContextPath()%>/account/carrello.jsp"
					tabindex="-1">Carrello</a>
				<%
					} else {
				%>
				<a class="nav-item nav-link disabled" href="#" tabindex="-1"
					aria-disabled="true">Carrello</a>
				<%
					}
				%>
			</div>
		</div>
	</nav>

	<!-- Carosello -->
	<%
		if (lastRobot.isEmpty()) {
	%>
	<h3 align="center" style="margin-top: 100px">IMMAGINI AL MOMENTO
		NON DISPONIBILI</h3>
	<%
		} else {
	%>
	<div id="carouselExampleCaptions" class="carousel slide"
		data-ride="carousel" style="padding-top: 55px;">
		<ol class="carousel-indicators">
			<li data-target="#carouselExampleCaptions" data-slide-to="0"
				class="active"></li>
			<li data-target="#carouselExampleCaptions" data-slide-to="1"></li>
			<li data-target="#carouselExampleCaptions" data-slide-to="2"></li>
		</ol>
		<div class="carousel-inner">
			<div class="carousel-item active">
				<a
					href="<%=request.getContextPath()%>/AjaxProductSelectedController?action=details&codeRobot=<%=lastRobot.get(0).getCode()%>">
					<img
					src="AccountController?action=getPictureRobot&code=<%=lastRobot.get(0).getCode()%>"
					onerror="this.src='<%=request.getContextPath()%>/images/NoFoodAllowed.jpg'"
					class="rounded mx-auto d-block w-50" alt="..."
					style="max-height: 500px;">
				</a>
				<div class="carousel-caption d-none d-md-block">
					<h5><%=lastRobot.get(0).getName()%></h5>
					<p>Robot di ultima tecnologia</p>
				</div>
			</div>
			<div class="carousel-item">
				<a
					href="<%=request.getContextPath()%>/AjaxProductSelectedController?action=details&codeRobot=<%=lastRobot.get(1).getCode()%>">
					<img
					src="AccountController?action=getPictureRobot&code=<%=lastRobot.get(1).getCode()%>"
					onerror="this.src='<%=request.getContextPath()%>/images/NoFoodAllowed.jpg'"
					class="rounded mx-auto d-block w-50" alt="..."
					style="max-height: 500px;">
				</a>
				<div class="carousel-caption d-none d-md-block">
					<h5><%=lastRobot.get(1).getName()%></h5>
					<p>Penultimo robot forgiato dall'azienda</p>
				</div>
			</div>
			<div class="carousel-item">
				<a
					href="<%=request.getContextPath()%>/AjaxProductSelectedController?action=details&codeRobot=<%=lastRobot.get(2).getCode()%>">
					<img
					src="AccountController?action=getPictureRobot&code=<%=lastRobot.get(2).getCode()%>"
					onerror="this.src='<%=request.getContextPath()%>/images/NoFoodAllowed.jpg'"
					class="rounded mx-auto d-block w-50" alt="..."
					style="max-height: 500px;">
				</a>
				<div class="carousel-caption d-none d-md-block">
					<h5><%=lastRobot.get(2).getName()%></h5>
					<p>Uno degli ultimi robot più innovativi</p>
				</div>
			</div>
		</div>
		<a class="carousel-control-prev" href="#carouselExampleCaptions"
			role="button" data-slide="prev"> <span
			class="carousel-control-prev-icon" aria-hidden="true"></span> <span
			class="sr-only">Previous</span>
		</a> <a class="carousel-control-next" href="#carouselExampleCaptions"
			role="button" data-slide="next"> <span
			class="carousel-control-next-icon" aria-hidden="true"></span> <span
			class="sr-only">Next</span>
		</a>
	</div>
	<%
		}
	%>

	<!-- importi Javascript-->
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/scripts/jquery.js"></script>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
		integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
		integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
		crossorigin="anonymous"></script>

</body>
</html>