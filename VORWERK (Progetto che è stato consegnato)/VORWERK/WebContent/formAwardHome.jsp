<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="author" content="Vincenzo Marrazzo">

</head>
<body>

	<form action="<%=response.encodeURL("AwardsController")%>"
		enctype="multipart/form-data" method="POST" id="insertFormAward"
		onsubmit="event.preventDefault(); controlAwardsForm(this)"
		onchange="validInvalidTooltipAward('insertAward');" novalidate>
		<input type="hidden" name="action" value="insert">
		<fieldset>
			<legend align="center">
				<h3>Insert AWARD</h3>
			</legend>
			<div class="shadow-lg p-3 mb-5 bg-white rounded" id="shadowInsert">
				<div class="form-row">
					<div class="col-md-6 mb-3">
						<label for="nameAward">Name</label> <input type="text"
							class="form-control" id="nameAward" name="nameAward"
							maxlength="20" placeholder="Frullatore" required>
						<div class="valid-tooltip" id="errorNameAward">Correct</div>
					</div>
					<div class="col-md-6 mb-3">
						<label for="pointsAward">Points</label> <input type="number"
							class="form-control" id="pointsAward" name="pointsAward" min="1"
							max="1500" placeholder="50" required>
						<div class="invalid-tooltip" id="errorPointsAward">Max
							points 1500</div>
					</div>
				</div>
				<div class="form-row">
					<div class="col-md-6 mb-3">
						<label for="descriptionAward">Description</label>
						<textarea class="form-control" id="descriptionAward"
							name="descriptionAward" maxlength="200" placeholder="Description"
							required></textarea>
					</div>
					<div class="col-md-3 mb-3">
						<label for="quantityAward">Quantity</label> <input type="number"
							class="custom-select form-control" id="quantityAward"
							name="quantityAward" min="1" max="100" placeholder="1" required>
						<div class="invalid-tooltip" id="errorQuantityAward">Max
							quantity 100</div>
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
	<!-- fine form Controller insert degli awards-->



</body>
</html>