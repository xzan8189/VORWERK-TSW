<%@page import="it.unisa.model.Premio"%>
<%@page import="it.unisa.model.Carrello"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	ArrayList<Premio> listPremi = (ArrayList<Premio>) request.getAttribute("listPremi");
String username = (String) session.getAttribute("user");
Boolean account = (Boolean) session.getAttribute("account");
String points = (String) request.getAttribute("points");

if (username != null && points == null) { /*recupero i punti dell'utente*/
	response.sendRedirect(response
	.encodeRedirectURL(request.getContextPath() + "/RegisteredAccountController?action=dispatchPremiLogged"));
	return;
}

if (listPremi == null) { /*recupero la lista dei premi*/
	response.sendRedirect(
	response.encodeRedirectURL(request.getContextPath() + "/RegisteredAccountController?action=dispatchPremi"));
	return;
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="author" content="Vincenzo Marrazzo">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/premi.css">
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
					class="nav-item nav-link active"
					href="<%=request.getContextPath()%>/account/premi.jsp">Premi</a>

				<!-- Controllo se è loggato altrimenti gli disabilito il link per il carrello -->
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
		<!-- Controllo i punti dell'utente -->
		<%
			if (points != null) {
		%>
		<div class="nav-item btn btn-primary float-right">
			Points <span class="badge badge-light"><%=points%></span>
		</div>
		<%
			}
		%>
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
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		</div>
	</div>
	<%
		}
	%>

	<!-- lista premi -->
	<%
		if (listPremi.isEmpty()) {
	%>
	<h3 align="center" style="margin-top: 100px">PREMI AL MOMENTO NON
		DISPONIBILI</h3>
	<%
		} else {
	%>
	<div class="shadow-none p-3 mb-5 bg-light rounded"
		style="margin-top: 100px; height: inherit;">
		<div class="d-flex justify-content-around" align="center">

			<div class="row">

				<%
					for (Premio premio : listPremi) {
				%>
				<div class="col-md-4" style="padding: 10px;">
					<div class="card" style="width: 20rem;">
						<img
							src="<%=request.getContextPath()%>/AccountController?action=getPictureAward&name=<%=premio.getName()%>"
							class="card-img-top"
							onerror="this.src='<%=request.getContextPath()%>/images/NoFoodAllowed.jpg'"
							style="max-height: 350px;" alt="...">
						<div class="card-body">
							<h5 class="card-title"><%=premio.getName()%></h5>
							<p class="card-text" style="word-wrap: break-word"><%=premio.getDescription()%><br>
								<b>Points: </b><%=premio.getPunti()%><br> <b>Quantity:
								</b><%=premio.getQuantity()%>
							</p>
							<%
								if (account != null && account) {
							%>
							<a
								href="<%=response.encodeURL(
		request.getContextPath() + "/RegisteredAccountController?action=buyAward&codeAward=" + premio.getName())%>"
								onclick="areYouSureToBuyAward('<%=premio.getName()%>')"
								class="btn btn-primary">Buy</a>
							<%
								} else {
							%>
							<a
								href="<%=response.encodeURL(
		request.getContextPath() + "/RegisteredAccountController?action=buyAward&codeAward=" + premio.getName())%>"
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
	<script type="text/javascript" src="scripts/premi.js?12"></script>
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