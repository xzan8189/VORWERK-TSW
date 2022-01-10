/**
 * 
 */

//Conferma dell'eliminazione del robot dal carrello
function areYouSure(key, typeOfProduct) {  //typeOfProduct sarà solo una stringa che può avere un solo valore 'robot' ed indica il tipo di prodotto che si sta eliminando dal carrello
	
	if (typeOfProduct=="robot")
		if (confirm("Sei sicuro di voler eliminare il robot dal carrello?")) {
			location.href= encodeURI("RegisteredAccountController?action=delete&code=" + key);
			console.log("Robot eliminato dal carrello");
		} else {
			console.log("Robot non eliminato dal carrello");
		}
}