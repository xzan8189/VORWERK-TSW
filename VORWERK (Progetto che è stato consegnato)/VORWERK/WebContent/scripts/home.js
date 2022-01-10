/**
 * 
 */

//La chiamo sul body e sfrutta l'attributo 'list' che viene mandato dalle due servlet, e gestisce la permanenza di una delle due liste: 'ROBOT' oppure 'PREMI'
function listOnLoadToShow(listToShow) {
	
	if (listToShow!=null && listToShow!="") {
		if (listToShow=="robotList") {
	
			//tab e liste
			$('#tab-premi').removeClass('active');
			$('#shadowAwards').hide();
			$('#tab-robot').addClass('active');
				
			$('#shadowProducts').show();
			$('#shadowProducts').addClass('show');

			//form inserimento robot
			$('#insertFormAward').hide();
			$('#insertFormRobot').show();
			
			//scroll sui details del robot
			$("html, body").animate({ scrollTop: $('#shadowProduct').height()}, 1000); //questo è animato sull'id
			//$('html, body').scrollTop($('#product').height()) questo è instantaneo sull'id
			//$("html, body").animate({ scrollTop: $(document).height() }, 1000); questo è animato fino al bot della pagina
			console.log("scroll sui details del robot");
			
		} else if (listToShow=="awardsList") {

			//tab e liste
			$('#tab-robot').removeClass('active');
			$('#shadowProducts').hide();
			$('#tab-premi').addClass('active');

			$('#shadowAwards').show();
			$('#shadowAwards').addClass('show');

			//form inserimento award
			$('#insertFormRobot').hide();
			$('#insertFormAward').show();
			
			//scroll sui details dell'award
			$("html, body").animate({ scrollTop: $('#shadowProduct').height()}, 1000); //questo è animato sull'id
			//$('html, body').scrollTop($('#product').height()) questo è instantaneo
			//$("html, body").animate({ scrollTop: $(document).height() }, 1000); //questo è animato fino al bot della pagina
			console.log("scroll sui details dell'award");
		}
		
	}
}

//La chiamo sui tab 'ROBOT' e 'PREMI', e gestisce la dissolvenza delle liste dei robot e premi
function listShow() {

	$('#tab-robot').on('click', function () {
		console.log("robot");
		//form inserimento robot
		$('#insertFormAward').hide();
		$('#insertFormRobot').show();
		
		//qui parte la dissolvena, in particolare col timeout
		$('#tab-premi').removeClass('active');
		$('#shadowAwards').removeClass('show');

		setTimeout( function() {
			$('#shadowAwards').hide();
			$('#tab-robot').addClass('active');
			
			$('#shadowProducts').show();
			$('#shadowProducts').addClass('show');
		}, 250);
		});
	
	$('#tab-premi').on('click', function () {
		console.log("premi");
		//form inserimento award
		$('#insertFormRobot').hide();
		$('#insertFormAward').show();
		
		//qui parte la dissolvena, in particolare col timeout
		$('#tab-robot').removeClass('active');
		$('#shadowProducts').removeClass('show');

		
		setTimeout( function() {
			$('#shadowProducts').hide();
			$('#tab-premi').addClass('active');
			
			$('#shadowAwards').show();
			$('#shadowAwards').addClass('show');
		}, 250);
		});
	
		
}

//Eliminazione messaggio di successo o di errore dell'inserimento di un robot
function deleteMessage() {
	
	$('.close').on('click', function() {
		
		$('.alert').removeClass('show');
		
		setTimeout( function() {
			$('.alert').hide();
		}, 250);
	});
	
}

//Conferma dell'eliminazione del robot o del premio
function areYouSure(key, typeOfProduct) {  //typeOfProduct sarà solo una stringa che può avere due valori: "robot" oppure "premio" serve a capire che tipo di prodotto stiamo eliminando
	
	if (typeOfProduct=="robot")
		if (confirm("Sei sicuro di voler eliminare il robot?")) {
			location.href= encodeURI("Controller?action=delete&code=" + key);
			console.log("Robot cancellato");
		} else {
			console.log("Robot non cancellato");
		}
	
	if (typeOfProduct=="premio")
		if (confirm("Sei sicuro di voler eliminare il premio?")) {
			location.href= encodeURI("AwardsController?action=delete&name=" + key);
			console.log("Premio cancellato");
		} else {
			console.log("Premio non cancellato");
		}
}

//Gestisce il toggle del form dell'update sia del robot e sia del premio
function buttonUpdate(button) {
	
	if (button=='updateRobot') {
		$('#buttonUpdateRobot').on('click', function() {
			$('#formUpdateRobot').toggle();
		})
	}
	
	if (button=='updateAward') {
		$('#buttonUpdateAward').on('click', function() {
			$('#formUpdateAward').toggle();
		})
	}
}




