<%@page import="java.util.ArrayList"%>
<%@page import="it.unisa.model.Robot"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String username = (String) session.getAttribute("user");
Boolean account = (Boolean) session.getAttribute("account");
Robot robot = (Robot) request.getAttribute("robot");
ArrayList<Robot> listSearchedRobot = (ArrayList<Robot>) request.getAttribute("listSearchedRobot");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="author" content="Vincenzo Marrazzo">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/ajaxProductSelected.css?1">
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

	<!-- robot selezionato -->
	<%
		if (robot != null) {
	%>
	<div class="container-fluid shadow-lg p-3 mb-5 bg-white rounded" style="margin-top: 110px;" align="center">
		<div class="row d-flex p-2 bd-highlight">
			<div class="col" style="min-width: 300px;">
				<img
					src="<%=request.getContextPath()%>/AccountController?action=getPictureRobot&code=<%=robot.getCode()%>"
					class="card-img-top"
					onerror="this.src='<%=request.getContextPath()%>/images/NoFoodAllowed.jpg'"
					style="max-height: 500px;" alt="...">
			</div>
			<div class="col " style="min-width: 200px;">
				<div style="padding-bottom: 50px;">
					<h3>
						<b>Name</b>
					</h3>
					<%=robot.getName()%>
				</div>
				<div style="padding-bottom: 50px;">
					<h3>
						<b>Description</b>
					</h3>
					<div style="word-wrap: break-word"><%=robot.getDescription()%></div>
				</div>
				<div style="padding-bottom: 50px;">
					<h3>
						<b>Price</b>
					</h3><%=robot.getPrice()%>
				</div>
				<div style="padding-bottom: 50px;">
					<h3>
						<b>Quantity</b>
					</h3><%=robot.getQuantity()%>
				</div>
				<div>
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

	</div>
	<%
		} else if (listSearchedRobot != null && !listSearchedRobot.isEmpty()) {
	%>
	
	<!-- Lista dei robot trovati -->
	<div class="container-fluid" style="margin-top: 80px;">
		<div class="row ">
			<div class="col">
				<div class="shadow-none p-3 mb-5 bg-light rounded"
					style="width: 100%;"height:inherit;">

					<table class="table-hover" style="width: 100%;" align="center">
						<caption>List Robots</caption>
						<thead align="center">
							<tr>
								<th scope="col"></th>
								<th scope="col">Name</th>
								<th scope="col">Description</th>
								<th scope="col">Quantity</th>
								<th scope="col">Price</th>
								<th scope="col">Action</th>
							</tr>
						</thead>
						<tbody align="center">
							<%
								for (Robot searchedRobots : listSearchedRobot) {
							%>
							<tr>
								<th scope="row"><img
									src="<%=request.getContextPath()%>/AccountController?action=getPictureRobot&code=<%=searchedRobots.getCode()%>"
									class="card-img-top"
									onerror="this.src='<%=request.getContextPath()%>/images/NoFoodAllowed.jpg'"
									style="max-width: 200px; max-height: 200px;" alt="..."></th>
								<th><%=searchedRobots.getName()%></th>
								<td><div style="max-width: 350px; word-wrap: break-word"><%=searchedRobots.getDescription()%></div></td>
								<td><%=searchedRobots.getQuantity()%></td>
								<td><%=searchedRobots.getPrice()%>$</td>
								<td><a class="badge badge-primary"
									href="<%=request.getContextPath()%>/AjaxProductSelectedController?action=details&codeRobot=<%=searchedRobots.getCode()%>"
									style="user-select: none;">details</a></td>
							</tr>
							<%
								}
							%>
						</tbody>
					</table>
				</div>
			</div>

			<%
				} else {
			%>
			<h3 align="center" style="margin-top: 100px">PRODOTTO NON
				TROVATO</h3>
			<%
				}
			%>


			<!-- importi Javascript-->
			<script type="text/javascript"
				src="<%=request.getContextPath()%>/scripts/jquery.js"></script>
			<script type="text/javascript"
				src="<%=request.getContextPath()%>/scripts/Ajax.js?2"></script>
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