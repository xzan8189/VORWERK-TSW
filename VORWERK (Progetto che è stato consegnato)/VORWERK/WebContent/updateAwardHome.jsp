<%@page import="it.unisa.model.Premio"%>
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
		Premio updatePremio = (Premio) request.getAttribute("premio");

	if (updatePremio != null) {
	%>
	<div class="btn-group">
		<button type="button" id="buttonUpdateAward"
			class="btn btn-primary dropdown-toggle" data-toggle="dropdown"
			aria-haspopup="true" aria-expanded="false"
			onclick="buttonUpdate('updateAward')">Update</button>
	</div>

	<!-- Update dell'AWARD -->
	<form action="<%=response.encodeURL("AwardsController")%>"
		enctype="multipart/form-data" method="POST" id="formUpdateAward"
		onsubmit="event.preventDefault(); controlUpdateAwardForm(this)"
		onchange="validInvalidTooltipAward('updateAward');" novalidate>

		<input type="hidden" name="action" value="updateAward"> <input
			type="hidden" name="idNameAward" value="<%=updatePremio.getName()%>">

		<!-- Caricamento foto AWARD -->
		<div class="form-row">
			<img alt="loading"
				src="<%=request.getContextPath()%>/AwardsController?action=getPicture&name=<%=updatePremio.getName()%>"
				class="rounded mx-auto d-block"
				onerror="this.src='./images/NoFoodAllowed.jpg'" style="width: 200px">
		</div>

		<div class="form-row">
			<div class="col-md-6 mb-3">
				<label for="updateNameAward">Name</label> <input type="text"
					class="form-control" id="updateNameAward" name="updateNameAward"
					maxlength="20" placeholder="Frullatore"
					value="<%=updatePremio.getName()%>" required>
			</div>
			<div class="col-md-6 mb-3">
				<label for="updatePointsAward">Points</label> <input type="number"
					class="form-control" id="updatePointsAward"
					name="updatePointsAward" min="1" max="1500" placeholder="50"
					value="<%=updatePremio.getPunti()%>" required>
				<div class="invalid-tooltip" id="updateErrorPointsAward">Max
					points 1500</div>
			</div>
		</div>
		<div class="form-row">
			<div class="col-md-6 mb-3">
				<label for="updateDescriptionAward">Description</label>
				<textarea class="form-control" id="updateDescriptionAward"
					name="updateDescriptionAward" maxlength="200"
					placeholder="Description" required><%=updatePremio.getDescription()%></textarea>
			</div>
			<div class="col-md-3 mb-3">
				<label for="updateQuantityAward">Quantity</label> <input
					type="number" class="custom-select form-control"
					id="updateQuantityAward" name="updateQuantityAward" min="1"
					max="100" placeholder="1" value="<%=updatePremio.getQuantity()%>"
					required>
				<div class="invalid-tooltip" id="updateErrorQuantityAward">Max
					quantity 100</div>
			</div>
		</div>
		<div class="form-row">
			<input type="file" name="updateLoadFileAward">
		</div>


		<div style="margin-top: 10px">
			<button class="btn btn-success" type="submit"
				id="updateInserisciAward">Update</button>
			<button class="btn btn-warning" type="reset" id="updateResetAward">Reset</button>
		</div>
	</form>
	<!-- fine form Controller update dell'award-->
	<%
		}
	%>
</body>
</html>