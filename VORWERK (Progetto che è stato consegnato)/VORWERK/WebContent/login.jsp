<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String user = (String) session.getAttribute("user");
Boolean admin = (Boolean) session.getAttribute("admin");
Boolean account = (Boolean) session.getAttribute("account");

//controllo se è già loggato ed ha i permessi admin
if (user != null && !user.equals("") && admin == true) {
	response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/home.jsp"));
	return;
}

//controllo se è già loggato ed è un account
if (user != null && !user.equals("") && account == true) {
	response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/account/account.jsp"));
	return;
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="author" content="Vincenzo Marrazzo">
<link rel="icon" type="image/png"
	href="<%=request.getContextPath()%>/images/Seraph.png">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
	crossorigin="anonymous">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Login VORWERK</title>
</head>
<body class="text-center">

	<!-- login mio -->
	<h1>Login Page</h1>

	<!-- controllo messaggio -->
	<%
		String errorMessage = (String) request.getServletContext().getAttribute("errorMessage");

	if (errorMessage != null && !errorMessage.equals("")) {
	%>
	<div class="flexbox-containerMessage" style="margin-top: 100px;"
		align="center">
		<div id="flexbox-itemMessage flexbox-itemMessage-2"
			style="width: 500px;">
			<div class="alert alert-danger alert-dismissible fade show"
				role="alert">
				<%=errorMessage%>
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		</div>
	</div>

	<%
		request.getServletContext().removeAttribute("errorMessage");
	}
	%>
	<form action="<%=request.getContextPath()%>/Login?action=login"
		method="post" class="form-signin" style="margin: 10%;">
		<a href="<%=request.getContextPath()%>/account/account.jsp"><img
			class="mb-4"
			src="<%=request.getContextPath() + "/images/Seraph.png"%>" alt=""
			width="72" height="72"></a>
		<h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>

		<label for="username">Username</label> <input type="text"
			name="username" id="username" placeholder="Tommy" required><br>
		<label for="password">Password</label> <input type="password"
			name="password" id="password" placeholder="Password" required><br>
		<input type="submit" value="login"> <input type="reset">

	</form>

	<footer>
		&copy;Informazioni segrete<br> Autore: Vincenzo Marrazzo
	</footer>

	<!-- importi Javascript-->
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/scripts/jquery.js"></script>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
		integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
		integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
		crossorigin="anonymous"></script>
</body>
</html>