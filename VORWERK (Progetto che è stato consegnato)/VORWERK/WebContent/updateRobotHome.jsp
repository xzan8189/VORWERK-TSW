<%@page import="it.unisa.model.Robot"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="author" content="Vincenzo Marrazzo">

</head>
<body>

	<%
		Robot updateRobot = (Robot) request.getAttribute("product");

	if (updateRobot != null) {
	%>
	<div class="btn-group">
		<button type="button" id="buttonUpdateRobot"
			class="btn btn-primary dropdown-toggle" data-toggle="dropdown"
			aria-haspopup="true" aria-expanded="false"
			onclick="buttonUpdate('updateRobot')">Update</button>
	</div>

	<!-- Update del ROBOT -->
	<form action="<%=response.encodeURL("Controller")%>"
		enctype="multipart/form-data" method="POST"
		onsubmit="event.preventDefault(); controlUpdateRobotForm(this)"
		onchange="validInvalidTooltipRobot('updateRobot');"
		id="formUpdateRobot" novalidate>

		<input type="hidden" name="action" value="updateRobot"> <input
			type="hidden" name="codeRobot" value="<%=updateRobot.getCode()%>">

		<!-- Caricamento foto AWARD -->
		<div class="form-row">
			<img alt="loading"
				src="<%=request.getContextPath()%>/Controller?action=getPicture&code=<%=updateRobot.getCode()%>"
				class="rounded mx-auto d-block"
				onerror="this.src='./images/NoFoodAllowed.jpg'" style="width: 200px">
		</div>

		<div class="form-row">
			<div class="col-md-6 mb-3">
				<label for="updateNameRobot">Name</label> <input type="text"
					class="form-control" id="updateNameRobot" name="updateNameRobot"
					maxlength="10" placeholder="TM5" value="<%=updateRobot.getName()%>"
					required>
			</div>
			<div class="col-md-6 mb-3">
				<label for="updatePriceRobot">Price</label> <input type="number"
					class="form-control" id="updatePriceRobot" name="updatePriceRobot"
					min="1" max="1000" placeholder="300"
					value="<%=updateRobot.getPrice()%>" required>
				<div class="invalid-tooltip" id="updateErrorPrice">Il prezzo
					max è 1000</div>
			</div>
		</div>
		<div class="form-row">
			<div class="col-md-6 mb-3">
				<label for="updateDescriptionRobot">Description</label>
				<textarea class="form-control" id="updateDescriptionRobot"
					name="updateDescriptionRobot" maxlength="200"
					placeholder="Description" required><%=updateRobot.getDescription()%></textarea>
			</div>
			<div class="col-md-3 mb-3">
				<label for="updateQuantityRobot">Quantity</label> <input
					type="number" class="custom-select form-control"
					id="updateQuantityRobot" name="updateQuantityRobot" min="1"
					max="500" placeholder="1" value="<%=updateRobot.getQuantity()%>"
					required>
				<div class="invalid-tooltip" id="updateErrorQuantity">Quantità
					max 500</div>
			</div>
		</div>
		<div class="form-row">
			<input type="file" name="updateLoadFileRobot">
		</div>

		<div style="margin-top: 10px">
			<button class="btn btn-success" type="submit" id="updateInserisci">Update</button>
			<button class="btn btn-warning" type="reset" id="updateReset">Reset</button>
		</div>
	</form>
	<!-- fine form Controller update del robot-->

	<%
		}
	%>
</body>
</html>