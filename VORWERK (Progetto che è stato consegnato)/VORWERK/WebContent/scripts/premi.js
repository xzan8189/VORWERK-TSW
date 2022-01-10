/**
 * 
 */

function areYouSureToBuyAward(key) { //la chiave del premio è il nome del premio

	if (confirm("Sei sicuro di voler comprare il Premio " + key + "?")) {
		location.href = encodeURI("RegisteredAccountController?action=buyAward&codeAward=" + key);
		console.log("Premio comprato");
	} else {
		console.log("Premio non comprato");
	}
}