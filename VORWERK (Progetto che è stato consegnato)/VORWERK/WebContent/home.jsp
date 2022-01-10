<%@page import="it.unisa.model.Premio"%>
<%@page import="it.unisa.model.Robot"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<% //ho messo getServletContext perchè altrimenti andarebbe in loop il ricevimento dei prodotti
	ArrayList<Premio> listPremi = (ArrayList<Premio>) request.getServletContext().getAttribute("listPremi");
	ArrayList<Robot> listRobot = (ArrayList<Robot>) request.getServletContext().getAttribute("listRobot");
	String username = (String) session.getAttribute("user");
	
	if (listPremi == null) { /*prendo lista premi*/
		response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/AwardsController"));
		return;
	}
	
	if (listRobot == null) { /*prendo lista robot*/
		response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/Controller"));
		return;
	}
	
	if (username == null || username.equals("")) { /*se non è loggato*/
		response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/login.jsp"));
		return;
	}
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="author" content="Vincenzo Marrazzo">
<link rel="icon" type="image/png" href="images/Seraph.png">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/home.css?12">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
	crossorigin="anonymous">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>VORWERK</title>
</head>
<body class="m-0 p-0 mx-auto"
	onload="event.preventDefault(); listOnLoadToShow('<%=request.getServletContext().getAttribute("list")%>')">

	<h1 align="center" id="titolo">
		<a href="<%=request.getContextPath()%>/Logout"><img src="<%=request.getContextPath()%>/images/Seraph.png"
			class="align-self-start mr-3" alt="..." width="72" height="72"></a>

		BENTORNATO
		<%=username%>!
	</h1>
	<br>
	<br>

	<div class="container-fluid m-0 p-0">
		<div id="flexbox-itemTop flexbox-itemTop-1">

			<!-- SCELTA DELL'ELENCO del database: ROBOT e PREMI -->
			<div align="center" class="container-lg" id="nav-bar">
				<div class="row-2">
					<div class="list-group list-group-horizontal" id="list-tab"
						role="tablist">
						<a class="list-group-item list-group-item-action active"
							id="tab-robot" onclick="listShow();">ROBOT</a> <a
							class="list-group-item list-group-item-action" id="tab-premi"
							onclick="listShow();">PREMI</a>
					</div>
				</div>
			</div>

			<%
				if (listRobot.isEmpty()) {
			%>
			<div class="shadow-lg p-3 mb-5 bg-white rounded" id="shadowProducts"
				style="height: inherit;">
				<table align="center" class="table table-hover table-dark"
					id="products">
					<tr>
						<th><a href=""
							class="link--subsequent core-stripe__more text-decoration-none">Code</a></th>
						<th><a href=""
							class="link--subsequent core-stripe__more text-decoration-none">Name</a></th>
						<th><a href=""
							class="link--subsequent core-stripe__more text-decoration-none">Description</a></th>
						<th><button type="button" class="btn btn-primary">Actions</button></th>
					</tr>

					<tr>
						<td colspan="4">No robot available</td>
					</tr>

				</table>
			</div>
			<%
				} else {
			%>
			<!-- TABELLA ROBOT -->
			<div
				class="shadow-lg p-3 mb-5 bg-white tab-pane fade show active rounded"
				id="shadowProducts">
				<table align="center" class=" table-hover table-dark" id="products">
					<caption>All Robots</caption>
					<tr>
						<th><a
							href="<%=response.encodeURL("Controller?order=code")%>"
							class="link--subsequent core-stripe__more , text-decoration-none">Code</a></th>
						<th><a
							href="<%=response.encodeURL("Controller?order=name")%>"
							class="link--subsequent core-stripe__more , text-decoration-none">Name</a></th>
						<th class="d-none d-md-block" style="height: 48px;"><a
							href="<%=response.encodeURL("Controller?order=description")%>"
							class="link--subsequent core-stripe__more , text-decoration-none">Description</a></th>
						<th><button type="button" class="btn btn-primary">Actions</button></th>
					</tr>

					<%
						for (Robot robot : listRobot) {
					%>

					<tr>
						<td><span class="d-inline-block text-truncate"
							style="max-width: 100px;"><%=robot.getCode()%></span></td>
						<td><span class="d-inline-block text-truncate"
							style="max-width: 200px;"><%=robot.getName()%></span></td>
						<td class="d-none d-md-block"><span
							class="d-inline-block text-truncate" style="max-width: 450px;"><%=robot.getDescription()%></span></td>
						<td><a
							href="<%=response.encodeURL("Controller?action=details&code=" + robot.getCode())%>"
							class="badge badge-warning">details</a> <a
							onclick="areYouSure(<%=robot.getCode()%>, 'robot')"
							class="badge badge-danger" style="user-select: none;">delete</a>
						</td>
					</tr>
					<%
						}
					%>
				</table>
			</div>

			<%
				}
			if (listPremi.isEmpty()) {
			%>
			<div class="shadow-lg p-3 mb-5 bg-white rounded" id="shadowAwards"
				style="height: inherit; display: none;">
				<table align="center" class="table table-hover table-dark"
					id="awards">
					<tr>
						<th><a href=""
							class="link--subsequent core-stripe__more text-decoration-none">Name</a></th>
						<th><a href=""
							class="link--subsequent core-stripe__more text-decoration-none">Description</a></th>
						<th><button type="button" class="btn btn-primary">Actions</button></th>
					</tr>

					<tr>
						<td colspan="3">No awards available</td>
					</tr>

				</table>
			</div>
			<%
				} else {
			%>
			<!-- TABELLA PREMI -->
			<div class="shadow-lg p-3 mb-5 bg-white tab-pane fade rounded"
				id="shadowAwards" style="display: none;">
				<table align="center" class=" table-hover table-dark" id="awards">
					<caption>All Awards</caption>
					<tr>
						<th><a
							href="<%=response.encodeURL("AwardsController?order=name")%>"
							class="link--subsequent core-stripe__more , text-decoration-none">Name</a></th>
						<th class="d-none d-md-block" style="height: 48px;"><a
							href="<%=response.encodeURL("AwardsController?order=description")%>"
							class="link--subsequent core-stripe__more , text-decoration-none">Description</a></th>
						<th><button type="button" class="btn btn-primary">Actions</button></th>
					</tr>

					<%
						for (Premio premio : listPremi) {
					%>

					<tr>
						<td><span class="d-inline-block text-truncate"
							style="max-width: 200px;"><%=premio.getName()%></span></td>
						<td class="d-none d-md-block"><span
							class="d-inline-block text-truncate" style="max-width: 450px;"><%=premio.getDescription()%></span></td>
						<td><a
							href="<%=response.encodeURL("AwardsController?action=details&name=" + premio.getName())%>"
							class="badge badge-warning">details</a> <a
							onclick="areYouSure('<%=premio.getName()%>', 'premio')"
							class="badge badge-danger" style="user-select: none;">delete</a>
							<!-- non funzionava perchè devi mettere sempre gli apici alle stringhe... mentre ai numeri no -->
						</td>
					</tr>
					<%
						}
					%>
				</table>
			</div>
			<%
				}
			%>

		</div>
	</div>
	<!-- fine container top del sito -->


	<!-- controllo del messaggio -->
	<%
		String message = (String) request.getAttribute("message");
	String errorMessage = (String) request.getAttribute("errorMessage");

	if (message != null && !message.equals("")) {
	%>
	<div class="flexbox-containerMessage">
		<div id="flexbox-itemMessage flexbox-itemMessage-1">
			<div class="alert alert-success alert-dismissible fade show"
				role="alert">
				<%=message%>
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close" onclick="deleteMessage();">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		</div>
	</div>

	<%
		} else if (errorMessage != null && !errorMessage.equals("")) {
	%>
	<div class="flexbox-containerMessage">
		<div id="flexbox-itemMessage flexbox-itemMessage-2">
			<div class="alert alert-danger alert-dismissible fade show"
				role="alert">
				<%=errorMessage%>
				<button type="button" class="close"
					data-dismiss="flexbox-containerMessage" aria-label="Close"
					onclick="deleteMessage();">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		</div>
	</div>

	<%
		}
	%>


	<div class="flexbox-container">
		<div id="flexbox-item flexbox-item-1">
			<%
				Robot robot = (Robot) request.getAttribute("product");

			if (robot != null) {
			%>
			<fieldset>
				<legend align="center">
					<h3>Details ROBOT</h3>
				</legend>
				<div class="shadow-lg p-3 mb-5 bg-white rounded" id="shadowProduct">
					<table align="center" class="table table-hover table-dark"
						id="product">
						<caption id="captionDetails"><%@include
								file="./updateRobotHome.jsp"%></caption>
						<tr>
							<th>Code</th>
							<th>Name</th>
							<th>Description</th>
							<th>Price</th>
							<th>Quantity</th>
						</tr>
						<tr>
							<td><%=robot.getCode()%></td>
							<td><%=robot.getName()%></td>
							<td id="descriptionDetails">
								<div style="word-break: break-all;">
									<%=robot.getDescription()%>
								</div>
							</td>
							<td><%=robot.getPrice()%></td>
							<td><%=robot.getQuantity()%></td>
						</tr>
					</table>
				</div>
			</fieldset>
		</div>
		<%
			}

		Premio premio = (Premio) request.getAttribute("premio");

		if (premio != null) {
		%>
		<fieldset>
			<legend align="center">
				<h3>Details AWARD</h3>
			</legend>
			<div class="shadow-lg p-3 mb-5 bg-white rounded" id="shadowProduct">
				<table align="center" class="table table-hover table-dark"
					id="product">
					<caption id="captionDetails"><%@include
							file="./updateAwardHome.jsp"%></caption>
					<tr>
						<th>Name</th>
						<th>Description</th>
						<th>Points</th>
						<th>Quantity</th>
					</tr>
					<tr>
						<td><%=premio.getName()%></td>
						<td id="descriptionDetails">
							<div style="word-break: break-all;">
								<%=premio.getDescription()%>
							</div>
						</td>
						<td><%=premio.getPunti()%></td>
						<td><%=premio.getQuantity()%></td>
					</tr>
				</table>
			</div>
		</fieldset>
	</div>
	<%
		}
	%>

	<div id="flexbox-item flexbox-item-2">


		<!-- Form ROBOT -->
		<div id="insertFormRobot">
			<%@include file="./formRobotHome.jsp"%>
			<!-- form insert dei robot -->
		</div>


		<!-- Form AWARD -->
		<div id="insertFormAward" style="display: none;">
			<%@include file="./formAwardHome.jsp"%>
			<!-- form insert degli awards -->
		</div>

	</div>
	<!-- fine flexbox-item-2 -->


	<footer>
		&copy;Informazioni segrete<br> Autore: Vincenzo Marrazzo
	</footer>


	<!-- importi Javascript-->
	<script type="text/javascript" src="scripts/jquery.js"></script>
	<script type="text/javascript" src="scripts/home.js?11111"></script>
	<script type="text/javascript" src="scripts/formAwardHome.js?11111"></script>
	<script type="text/javascript" src="scripts/formRobotHome.js?111111"></script>


</body>
</html>


















