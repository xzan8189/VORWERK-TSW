/**
 * 
 */

function getXmlHttpRequest() {

	var xhr = false;
	var activeXoptions = new Array("Microsoft.XmlHttp", "MSXML4.XmlHttp",
			"MSXML3.XmlHttp", "MSXML2.XmlHttp", "MSXML.XmlHttp");

	try {
		xhr = new XMLHttpRequest();
		console.log("\nGet XML http request");
	} catch (e) {
	}

	if (!xhr) {
		var created = false;
		for (var i = 0; i < activeXoptions.length && !created; i++) {
			try {
				xhr = new ActiveXObject(activeXoptions[i]);
				created = true;
				console.log("Get ActiveXObject XML http request");
			} catch (e) {
			}
		}
	}
	return xhr;
}

function getReadyStateHandler(req, responseXmlHandler, id) {
	return function() {
		
		if (req.readyState == 1) {
			console.log("Server connection");
		} else if ( req.readyState == 2 ) {
			console.log("Send request");
		} else if ( req.readyState == 3 ) {
				console.log("Receive response");
		} else if (req.readyState == 4) {
			console.log("Request finished and response is ready");
			if (req.status == 200 || req.status == 304) {
				responseXmlHandler(req.responseXML, id);
			} else {
				console.log("Response error "+ req.status + " " + req.statusText);
			}
		} else {
		}
	};
}

function ajaxCall(id, url, stateChanged, parameter) {
	var xmlHttp = getXmlHttpRequest();
	
	try {
		xmlHttp.onreadystatechange= getReadyStateHandler(xmlHttp, stateChanged, id);
		xmlHttp.open("POST", url, true);
		xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		console.log("Open and send request");
		xmlHttp.send("nameRobot=" + parameter); /*qua si mandano i paramentri, ma già li ho messi nella url*/
	} catch(e1) {
		
	}
}

function stateChanged(listXML, id) {
	try { 
		var obj1 = document.getElementById("AjaxNameRobot1");		
		var obj2 = document.getElementById("AjaxNameRobot2");	
		var obj3 = document.getElementById("AjaxNameRobot3");	
		var obj4 = document.getElementById("AjaxNameRobot4");	

		$('#AjaxNameRobot1').hide();
		$('#AjaxNameRobot2').hide();
		$('#AjaxNameRobot3').hide();
		$('#AjaxNameRobot4').hide();

		$('#hrAjaxNameRobot1').hide();
		$('#hrAjaxNameRobot2').hide();
		$('#hrAjaxNameRobot3').hide();
		
		$('#shadowAjax').hide();
		
		if(obj1 != null && obj1!='') {
			var rdfs = listXML.getElementsByTagName("nameRobot")[0].firstChild.nodeValue;
			var codeRobot = listXML.getElementsByTagName("codeRobot")[0].firstChild.nodeValue;
			document.getElementById("AjaxCodeRobot1").innerHTML=codeRobot;
			$('#AjaxNameRobot1').show();
			$('#shadowAjax').show();
			obj1.innerHTML =rdfs;
			console.log("il primo è scritto: " + listXML.getElementsByTagName("nameRobot")[0].firstChild.nodeValue);
		}

		if(obj2 != null && obj2!='') {
			var rdfs = listXML.getElementsByTagName("nameRobot")[1].firstChild.nodeValue;
			var codeRobot = listXML.getElementsByTagName("codeRobot")[1].firstChild.nodeValue;
			document.getElementById("AjaxCodeRobot2").innerHTML=codeRobot;
			$('#hrAjaxNameRobot1').show();
			$('#AjaxNameRobot2').show();
			obj2.innerHTML =rdfs;
			console.log("il secondo è scritto: " + listXML.getElementsByTagName("nameRobot")[1].firstChild.nodeValue);
		}

		if(obj3 != null && obj3!='') {
			var rdfs = listXML.getElementsByTagName("nameRobot")[2].firstChild.nodeValue; 
			var codeRobot = listXML.getElementsByTagName("codeRobot")[2].firstChild.nodeValue;
			document.getElementById("AjaxCodeRobot3").innerHTML=codeRobot;
			$('#hrAjaxNameRobot2').show();
			$('#AjaxNameRobot3').show();
			obj3.innerHTML =rdfs;
			console.log("il terzo è scritto: " + listXML.getElementsByTagName("nameRobot")[2].firstChild.nodeValue);
		}

		if(obj4 != null && obj4!='') {
			var rdfs = listXML.getElementsByTagName("nameRobot")[3].firstChild.nodeValue;
			var codeRobot = listXML.getElementsByTagName("codeRobot")[3].firstChild.nodeValue;
			document.getElementById("AjaxCodeRobot4").innerHTML=codeRobot;
			$('#hrAjaxNameRobot3').show();
			$('#AjaxNameRobot4').show();
			obj4.innerHTML =rdfs;
			console.log("il quarto è scritto: " + listXML.getElementsByTagName("nameRobot")[3].firstChild.nodeValue);
		}
		
	} catch(e1) {
		console.log('finiti i robot nel file xml');
	}

}

function productSelected(divCodeRobot) {
	if (divCodeRobot != null && divCodeRobot != "") {
		var codeRobot = divCodeRobot.textContent; //mi prendo il testo all'interno
		console.log('codeRobot selected: ' + codeRobot);
		location.href= encodeURI("AjaxProductSelectedController?action=details&codeRobot=" + codeRobot);
	} else {
		console.log('no divCodeRobot');
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	