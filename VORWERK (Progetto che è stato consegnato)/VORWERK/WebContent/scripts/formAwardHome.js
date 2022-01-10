/**
 * 
 */


//Controllo del Form dell'inserimento di un award
function controlAwardsForm(form) {
	try {
		var flag=true;
		
		var name= $("#nameAward").val();
		var points= $("#pointsAward").val();
		var description= $("#descriptionAward").val();
		var quantity= $("#quantityAward").val();
			
		$(".error").remove();

		//quantity
		if (quantity<1 || quantity>100) {
			flag=false;
			$("#quantityAward").focus();
			console.log("quantity false");
		} else {
			$("#quantityAward").addClass("was-validated");
			console.log("quantity true");
		}

		//description
		if (description.length<1 || description.length>200) {
			flag= false;
			$("#descriptionAward").focus();
			console.log("description false");
		} else {
			$("#descriptionAward").addClass("was-validated");
			console.log("description true");
		}

		//points
		if (points<1 || points>1500) {
			flag=false;
			$("#pointsAward").focus();
			console.log("points false");
		} else {
			$("#pointsAward").addClass("was-validated");
			console.log("points true");
		}
		
		//name
		if (name.length<1 || name.length>20) {
			flag=false;
			$("#nameAward").focus();
			console.log("name false");
		} else {
			$("#nameAward").addClass("was-validated");
			console.log("name true");
		}

		
		console.log("\n-------------");
		
		if (flag) {
			form.submit();
		}
	} catch (e) {
		console.log(e);
	}
}

//Controllo del Form dell'update di un award
function controlUpdateAwardForm(form) {
	try {
		var flag=true;
		
		var name= $("#updateNameAward").val();
		var points= $("#updatePointsAward").val();
		var description= $("#updateDescriptionAward").val();
		var quantity= $("#updateQuantityAward").val();
			
		$(".error").remove();

		//quantity
		if (quantity<1 || quantity>100) {
			flag=false;
			$("#updateQuantityAward").focus();
			console.log("quantity false");
		} else {
			$("#updateQuantityAward").addClass("was-validated");
			console.log("quantity true");
		}

		//description
		if (description.length<1 || description.length>200) {
			flag= false;
			$("#updateDescriptionAward").focus();
			console.log("description false");
		} else {
			$("#updateDescriptionAward").addClass("was-validated");
			console.log("description true");
		}

		//points
		if (points<1 || points>1500) {
			flag=false;
			$("#updatePointsAward").focus();
			console.log("points false");
		} else {
			$("#updatePointsAward").addClass("was-validated");
			console.log("points true");
		}
		
		//name
		if (name.length<1 || name.length>20) {
			flag=false;
			$("#updateNameAward").focus();
			console.log("name false");
		} else {
			$("#updateNameAward").addClass("was-validated");
			console.log("name true");
		}

		
		console.log("\n-------------");
		
		if (flag) {
			form.submit();
		}
	} catch (e) {
		console.log(e);
	}
}

//Tooltip validi ed invalidi del Form dell'inserimento di un award
function validInvalidTooltipAward(updateOrInsert){
	
	//per l'insert
	if (updateOrInsert=='insertAward') {
		var points= $("#pointsAward").val();
		var quantity= $("#quantityAward").val();
	
		//tooltip quantity
		if (quantity<1 || quantity>100) {
			$('#errorQuantityAward').show();
		} else {
			$('#errorQuantityAward').hide();
		}
	
		//tooltip points
		if (points<1 || points>1500) {
			$('#errorPointsAward').show();
		} else {
			$('#errorPointsAward').hide();
		}
	}
	
	//per l'update
	if (updateOrInsert=='updateAward') {
		var updatePointsAward= $("#updatePointsAward").val();
		var updateQuantityAward= $("#updateQuantityAward").val();
	
		//tooltip quantity
		if (updateQuantityAward<1 || updateQuantityAward>100) {
			$('#updateErrorQuantityAward').show();
		} else {
			$('#updateErrorQuantityAward').hide();
		}
	
		//tooltip points
		if (updatePointsAward<1 || updatePointsAward>1500) {
			$('#updateErrorPointsAward').show();
		} else {
			$('#updateErrorPointsAward').hide();
		}
	}
	
}










