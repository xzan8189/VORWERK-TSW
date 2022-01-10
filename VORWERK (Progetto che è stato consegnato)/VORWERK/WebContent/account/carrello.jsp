<%@page import="it.unisa.model.Carrello"%>
<%@page import="it.unisa.model.Robot"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	ArrayList<Carrello> carrello = (ArrayList<Carrello>) request.getAttribute("carrello");
ArrayList<Robot> robots = (ArrayList<Robot>) request.getAttribute("robots");
String username = (String) session.getAttribute("user");
Boolean account = (Boolean) session.getAttribute("account");

if (username == null || username.equals("")) {
	response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/login.jsp"));
	return;
}

if (carrello == null) {
	response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/RegisteredAccountController"));
	return;
}

session.setAttribute("carrello", carrello);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="author" content="Vincenzo Marrazzo">
<link rel="icon" type="image/png"
	href="<%=request.getContextPath()%>/images/Seraph.png">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/carrello.css?2">
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
				<a class="nav-item nav-link active"
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

	<!-- controllo messaggio -->
	<%
		String message = (String) request.getAttribute("message");
	String errorMessage = (String) request.getAttribute("errorMessage");

	if (message != null && !message.equals("")) {
	%>
	<div class="flexbox-containerMessage" style="margin-top: 100px;"
		align="center">
		<div id="flexbox-itemMessage flexbox-itemMessage-1"
			style="width: 500px;">
			<div class="alert alert-success alert-dismissible fade show"
				role="alert">
				<%=message%>
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		</div>
	</div>
	<%
		} else if (errorMessage != null && !errorMessage.equals("")) {
	%>
	<div class="flexbox-containerMessage" style="margin-top: 100px;"
		align="center">
		<div id="flexbox-itemMessage flexbox-itemMessage-2"
			style="width: 500px;">
			<div class="alert alert-danger alert-dismissible fade show"
				role="alert">
				<%=errorMessage%>
				<button type="button" class="close"
					data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		</div>
	</div>
	<%
		}
	%>

	<!-- carrello -->
	<%
		if (carrello.isEmpty()) {
	%>
	<h3 align="center" style="margin-top: 100px">CARRELLO VUOTO</h3>
	<%
		} else {
	%>
	<div class="container-fluid" style="margin-top: 80px;">
		<div class="row ">
			<div class="col">
				<div class="shadow-none p-3 mb-5 bg-light rounded"
					style="width: 100%;"height:inherit;">

					<table class="table-hover" style="width: 100%;" align="center">
						<caption>
							<a
								href="<%=request.getContextPath()%>/RegisteredAccountController?action=clear">Clear
								Cart</a>
						</caption>
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
								for (int i = 0, j = 0; i < robots.size(); i++, j++) {
							%>
							<tr>
								<th scope="row"><img
									src="<%=request.getContextPath()%>/AccountController?action=getPictureRobot&code=<%=robots.get(i).getCode()%>"
									class="card-img-top"
									onerror="this.src='<%=request.getContextPath()%>/images/NoFoodAllowed.jpg'"
									style="max-width: 200px; max-height: 200px;" alt="..."></th>
								<th><%=robots.get(i).getName()%></th>
								<td><div style="max-width: 350px;word-wrap:break-word"><%=robots.get(i).getDescription()%></div></td>
								<td><%=carrello.get(j).getQuantity()%></td>
								<td><%=robots.get(i).getPrice()%>$</td>
								<td><a class="badge badge-danger" onclick="areYouSure(<%=robots.get(i).getCode()%>, 'robot')" style="user-select: none;">delete</a></td>
							</tr>
							<%
								}
							%>
						</tbody>
					</table>
				</div>
			</div>
			<!-- Resoconto del carrello -->
			<div class="col-md-auto">
				<div class="float-sm-right" style="width: 100%;">
					<h4>
						Totale provvisorio(<%=carrello.size()%>
						articoli):
					</h4>
					<br>
					<%
						int somma = 0;
					for (int i = 0; i < robots.size(); i++)
						somma += Integer.parseInt(robots.get(i).getPrice()) * carrello.get(i).getQuantity();
					%>
					<h3>
						<b><%=somma%>$</b>
					</h3>
					<br> <a class="btn btn-warning"
						href="<%=request.getContextPath()%>/RegisteredAccountController?action=buyAll"
						style="width: 100%;">Procedi all'ordine</a>
				</div>
			</div>
			<%
				}
			%>
		</div>
	</div>







	<!-- importi Javascript-->
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/scripts/carrello.js"></script>
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