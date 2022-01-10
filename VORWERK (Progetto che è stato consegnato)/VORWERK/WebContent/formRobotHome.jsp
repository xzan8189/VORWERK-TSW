<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="author" content="Vincenzo Marrazzo">

</head>
<body>

	<form action="<%=response.encodeURL("Controller")%>"
		enctype="multipart/form-data" method="POST"
		onsubmit="event.preventDefault(); controlRobotForm(this)"
		onchange="validInvalidTooltipRobot('insertRobot');" novalidate>
		<input type="hidden" name="action" value="insert">
		<fieldset>
			<legend align="center">
				<h3>Insert ROBOT</h3>
			</legend>
			<div class="shadow-lg p-3 mb-5 bg-white rounded" id="shadowInsert">
				<div class="form-row">
					<div class="col-md-6 mb-3">
						<label for="nameProduct">Name</label> <input type="text"
							class="form-control" id="nameProduct" name="name" maxlength="10"
							placeholder="TM5" required>
						<div class="valid-tooltip" id="errorName">Corretto</div>
					</div>
					<div class="col-md-6 mb-3">
						<label for="priceProduct">Price</label> <input type="number"
							class="form-control" id="priceProduct" name="price" min="1"
							max="1000" placeholder="300" required>
						<div class="invalid-tooltip" id="errorPrice">Il prezzo max è
							1000</div>
					</div>
				</div>
				<div class="form-row">
					<div class="col-md-6 mb-3">
						<label for="description">Description</label>
						<textarea class="form-control" id="description" name="description"
							maxlength="200" placeholder="Description" required></textarea>
					</div>
					<div class="col-md-3 mb-3">
						<label for="quantityProduct">Quantity</label> <input type="number"
							class="custom-select form-control" id="quantityProduct"
							name="quantity" min="1" max="500" placeholder="1" required>
						<div class="invalid-tooltip" id="errorQuantity">Quantità max
							500</div>
					</div>
				</div>
				<div class="form-row">
					<input type="file" name="insertLoadFileRobot">
				</div>

				<div style="margin-top: 10px">
					<button class="btn btn-success" type="submit" id="inserisci">Insert</button>
					<button class="btn btn-warning" type="reset" id="reset">Reset</button>
				</div>
			</div>
			<!-- fine shadow insert -->
		</fieldset>
	</form>
	<!-- fine form Controller insert del robot-->

</body>
</html>