/**
 * 
 */

//Controllo del Form dell'inserimento di un robot
function controlRobotForm(form) {
	try {
		var flag=true;
		
		var name= $("#nameProduct").val();
		var price= $("#priceProduct").val();
		var description= $("#description").val();
		var quantity= $("#quantityProduct").val();
			
		$(".error").remove();

		//quantity
		if (quantity<1 || quantity>500) {
			flag=false;
			$("#quantityProduct").focus();
			console.log("quantity false");
		} else {
			$("#quantityProduct").addClass("was-validated");
			console.log("quantity true");
		}

		//description
		if (description.length<1 || description.length>200) {
			flag= false;
			$("#description").focus();
			console.log("description false");
		} else {
			$("#description").addClass("was-validated");
			console.log("description true");
		}

		//price
		if (price<1 || price>1000) {
			flag=false;
			$("#priceProduct").focus();
			console.log("price false");
		} else {
			$("#priceProduct").addClass("was-validated");
			console.log("price true");
		}
		
		//name
		if (name.length<1 || name.length>10) {
			flag=false;
			$("#nameProduct").focus();
			console.log("name false");
		} else {
			$("#nameProduct").addClass("was-validated");
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

//Controllo del Form dell'update di un robot
function controlUpdateRobotForm(form) {
	try {
		var flag=true;
		
		var name= $("#updateNameRobot").val();
		var price= $("#updatePriceRobot").val();
		var description= $("#updateDescriptionRobot").val();
		var quantity= $("#updateQuantityRobot").val();
			
		$(".error").remove();

		//quantity
		if (quantity<1 || quantity>500) {
			flag=false;
			$("#updateQuantityRobot").focus();
			console.log("quantity false");
		} else {
			$("#updateQuantityRobot").addClass("was-validated");
			console.log("quantity true");
		}

		//description
		if (description.length<1 || description.length>200) {
			flag= false;
			$("#updateDescriptionRobot").focus();
			console.log("description false");
		} else {
			$("#updateDescriptionRobot").addClass("was-validated");
			console.log("description true");
		}

		//price
		if (price<1 || price>1000) {
			flag=false;
			$("#updatePriceRobot").focus();
			console.log("price false");
		} else {
			$("#updatePriceRobot").addClass("was-validated");
			console.log("price true");
		}
		
		//name
		if (name.length<1 || name.length>10) {
			flag=false;
			$("#updateNameRobot").focus();
			console.log("name false");
		} else {
			$("#updateNameRobot").addClass("was-validated");
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

//Tooltip validi ed invalidi del Form dell'inserimento e dell'update di un robot
function validInvalidTooltipRobot(updateOrInsert){
	
	
	if (updateOrInsert=='insertRobot') {
		var priceRobot= $("#priceProduct").val();
		var quantityRobot= $("#quantityProduct").val();
	
		//tooltip quantity
		if (quantityRobot<1 || quantityRobot>500) {
			$('#errorQuantity').show();
		} else {
			$('#errorQuantity').hide();
		}
	
		//tooltip price
		if (priceRobot<1 || priceRobot>1000) {
			$('#errorPrice').show();
		} else {
			$('#errorPrice').hide();
		}
		
	}
	
	if (updateOrInsert=='updateRobot') {
		var updatePriceRobot= $("#updatePriceRobot").val();
		var updateQuantityRobot= $("#updateQuantityRobot").val();
	
		//tooltip quantity
		if (updateQuantityRobot<1 || updateQuantityRobot>500) {
			$('#updateErrorQuantity').show();
		} else {
			$('#updateErrorQuantity').hide();
		}
	
		//tooltip price
		if (updatePriceRobot<1 || updatePriceRobot>1000) {
			$('#updateErrorPrice').show();
		} else {
			$('#updateErrorPrice').hide();
		}
		
	}
	
}