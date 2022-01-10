<%@page import="it.unisa.model.Robot"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	ArrayList<Robot> listRobot = (ArrayList<Robot>) request.getAttribute("listRobot");
String username = (String) session.getAttribute("user");
Boolean account = (Boolean) session.getAttribute("account");

if (listRobot == null) {
	response.sendRedirect(
	response.encodeRedirectURL(request.getContextPath() + "/RegisteredAccountController?action=dispatchRobot"));
	return;
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="author" content="Vincenzo Marrazzo">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/robot.css">
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

	<!-- navbar -->
	<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
		<%
			if (username == null || username.equals("")) {
		%><a class="navbar-brand"
			href="<%=request.getContextPath()%>/login.jsp">Log in</a>
		<%
			} else {
		%><a class="navbar-brand" href="<%=request.getContextPath()%>/Logout">Log
			out</a>
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
				<a class="nav-item nav-link"
					href="<%=request.getContextPath()%>/account/account.jsp">Home <span
					class="sr-only">(current)</span></a> <a
					class="nav-item nav-link active"
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
		<div>
			<div class="row">
				<div class="col">
					<form
						action="<%=response.encodeURL("AjaxProductSelectedController?action=details")%>"
						method="POST" class="form-inline my-2 my-lg-0">
						<input class="form-control mr-sm-2" type="search"
							placeholder="Search" aria-label="Search" id="SearchRobot"
							name="SearchRobot"
							onkeyup="ajaxCall('result', '/VORWERK/AjaxController', stateChanged,this.value);" required>
						<button class="btn btn-outline-success my-2 my-sm-0" type="submit"
							id="buttonSearchRobot" name="buttonSearchRobot">Search</button>
					</form>
					<div class="shadow-sm p-3 mb-5 bg-white rounded" id="shadowAjax"
						style="display: none; right: 0; width: 300px; position: absolute;">
						<b id="AjaxNameRobot1" style="display: none;"
							onclick="productSelected(AjaxCodeRobot1)"></b>
						<div id="AjaxCodeRobot1" style="display: none;"></div>
						<!-- codeRobot1 -->
						<hr id="hrAjaxNameRobot1">

						<b id="AjaxNameRobot2" style="display: none;"
							onclick="productSelected(AjaxCodeRobot2)"></b>
						<div id="AjaxCodeRobot2" style="display: none;"></div>
						<!-- codeRobot2 -->
						<hr id="hrAjaxNameRobot2">

						<b id="AjaxNameRobot3" style="display: none;"
							onclick="productSelected(AjaxCodeRobot3)"></b>
						<div id="AjaxCodeRobot3" style="display: none;"></div>
						<!-- codeRobot3 -->
						<hr id="hrAjaxNameRobot3">

						<b id="AjaxNameRobot4" style="display: none;"
							onclick="productSelected(AjaxCodeRobot4)"></b>
						<div id="AjaxCodeRobot4" style="display: none;"></div>
						<!-- codeRobot4 -->
					</div>
				</div>
			</div>
		</div>
	</nav>

	<!-- lista robot -->
	<%
		if (listRobot.isEmpty()) {
	%>
	<h3 align="center" style="margin-top: 100px">ROBOT AL MOMENTO NON
		DISPONIBILI</h3>
	<%
		} else {
	%>
	<div class="shadow-none p-3 mb-5 bg-light rounded"
		style="margin-top: 100px; height: inherit;">
		<div class="d-flex justify-content-around" align="center">

			<div class="row">

				<%
					for (Robot robot : listRobot) {
				%>
				<div class="col-md-4" style="padding: 10px;">
					<div class="card" style="width: 20rem;">
						<img
							src="<%=request.getContextPath()%>/AccountController?action=getPictureRobot&code=<%=robot.getCode()%>"
							class="card-img-top"
							onerror="this.src='<%=request.getContextPath()%>/images/NoFoodAllowed.jpg'"
							style="max-height: 350px;" alt="...">
						<div class="card-body">
							<h5 class="card-title"><%=robot.getName()%></h5>
							<p class="card-text" style="word-wrap:break-word"><%=robot.getDescription()%><br> <b>Price:
								</b><%=robot.getPrice()%>$<br> <b>Quantity: </b><%=robot.getQuantity()%></p>
							<%
								if (account != null && account) {
							%>
							<a
								href="<%=response.encodeURL(request.getContextPath()
		+ "/RegisteredAccountController?action=addCarrelloRobot&codeRobot=" + robot.getCode())%>"
								class="btn btn-primary">Buy</a>
							<%
								} else {
							%>
							<a
								href="<%=response.encodeURL(request.getContextPath()
		+ "/RegisteredAccountController?action=addCarrelloRobot&codeRobot=" + robot.getCode())%>"
								class="btn btn-primary disabled" aria-disabled="true">Buy</a>
							<%
								}
							%>
						</div>
					</div>
				</div>
				<%
					}
				%>


			</div>
		</div>
	</div>
	<%
		}
	%>

	<!-- importi Javascript-->
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/scripts/jquery.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/scripts/Ajax.js?4"></script>
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