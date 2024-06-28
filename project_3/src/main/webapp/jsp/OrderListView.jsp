

<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.dto.OrderDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.model.ModelFactory"%>
<%@page import="in.co.rays.project_3.model.RoleModelInt"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.controller.OrderListCtl"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title></title>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>
	
	<script type="text/javascript">
	function numberLength(input) {
		if (input.value.length > 10) {
			input.value = input.value.slice(0, 10);
		}
	}
</script>
<script>
	function validateNumberKey(event, validationMessageId) {
		// Allow only digits, minus sign, and dot for latitude and longitude input
		if (!/[0-9\.\-]/.test(event.key)) {
			// Show validation message
			var validationMsg = document.getElementById(validationMessageId);
			validationMsg.style.display = 'inline';
			event.preventDefault(); // Prevent typing the invalid character
		} else {
			// Hide validation message if input is valid
			var validationMsg = document.getElementById(validationMessageId);
			validationMsg.style.display = 'none';
		}
	}
</script>
<script>
	function validateStringKey(event, validationMessageId) {
		// Allow only alphabetic characters (a-z, A-Z) for string input
		if (!/[a-zA-Z]/.test(event.key)) {
			// Show validation message
			var validationMsg = document.getElementById(validationMessageId);
			validationMsg.style.display = 'inline';
			event.preventDefault(); // Prevent typing the invalid character
		} else {
			// Hide validation message if input is valid
			var validationMsg = document.getElementById(validationMessageId);
			validationMsg.style.display = 'none';
		}
	}
</script>

<style>

.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/rain.jpg');
	background-size: cover;
	background-repeat: no-repeate;
	padding-top: 6%;
}

.p1 {
	padding: 4px;
	width: 200px;
	font-size: bold;
}

.text {
	text-align: center;
}
</style>
</head>

<body class="hm">
	<%@include file="Header.jsp"%>
	<%@include file="calendar.jsp"%>
	<div></div>
	<div>
		<form class="pb-5" action="<%=ORSView.ORDER_LIST_CTL%>" method="post">
			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.OrderDTO"
				scope="request"></jsp:useBean>
		

			<%
				int pageNo = ServletUtility.getPageNo(request);
	        	HashMap map =	(HashMap)request.getAttribute("map");
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

				List list = ServletUtility.getList(request);

				Iterator<OrderDTO> it = list.iterator();
				if (list.size() != 0) {
			%>
			<center>
				<h1 class="text-primary font-weight-bold pt-3">
					<u>Order List</u>
				</h1>
			</center>
			<div class="row">
				<div class="col-md-4"></div>
				<%
					if (!ServletUtility.getSuccessMessage(request).equals("")) {
				%>

				<div class="col-md-4 alert alert-success alert-dismissible"
					style="background-color: #80ff80">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<h4>
						<font color="#008000"><%=ServletUtility.getSuccessMessage(request)%></font>
					</h4>
				</div>
				<%
					}
				%>
				<div class="col-md-4"></div>
			</div>
			<div class="row">
				<div class="col-md-4"></div>

				<%
					if (!ServletUtility.getErrorMessage(request).equals("")) {
				%>
				<div class=" col-md-4 alert alert-danger alert-dismissible">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<h4>
						<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
					</h4>
				</div>
				<%
					}
				%>
				<div class="col-md-4"></div>
			</div>

			<div class="row">



				<div class="col-sm-2">
					<input type="text" name="CustomerName" placeholder="Enter Name"
						class="form-control"
						value="<%=ServletUtility.getParameter("CustomerName", request)%>"
						onkeypress="validateStringKey(event,'CustomerNameValidationMsg')">
						<span id="CustomerNameValidationMsg" style="display: none; color: red;">please enter only alphabetic character</span>
				</div>
				<div class="col-sm-2">
					<input type="text" name="phoneNo" placeholder="Enter phone"
						class="form-control" oninput="numberLength(this)" 
						value="<%=ServletUtility.getParameter("phoneNo", request)%>" 
						onkeypress="validateNumberKey(event,'phoneNo-validation-message')">
						<span id="phoneNo-validation-message" style="display: none; color : red;">invalid character for phone numer</span>
				</div>


				<div class="col-sm-2">
					<input type="text" name="address" placeholder="Enter  Address"
						class="form-control"
						value="<%=ServletUtility.getParameter("address", request)%>">
				</div>


				<div class="col-sm-2">
					<input type="text" name="version" placeholder=" Enter version "
						class="form-control"
						value="<%=ServletUtility.getParameter("version", request)%>" 
						onkeypress="validateNumberKey(event,'version-validation-message')">
						<span id="version-validation-message" style="display: none; color: red;">Invalid character for version</span>
						
				</div>

				<div class="col-sm-2">
					<input type="text" name="amount" placeholder=" Enter amount "
						class="form-control"
						value="<%=ServletUtility.getParameter("amount", request)%>" onkeypress="validateNumberKey(event,'amount-validation-messaage')">
				<span id="amount-validation-messaage" style="display: none; color: red;"> Invalild charater for amount</span>
				</div>


				<div class="col-sm-2"><%=HTMLUtility.getList("product", dto.getProduct(), map)%></div>
				&emsp;



				<div align="center">
					<input type="submit" class="btn btn-primary btn-md"
						style="font-size: 15px" name="operation"
						value="<%=OrderListCtl.OP_SEARCH%>"> &emsp; <input
						type="submit" class="btn btn-dark btn-md" style="font-size: 15px"
						name="operation" value="<%=OrderListCtl.OP_RESET%>">
				</div>


				<div class="col-sm-2"></div>
			</div>

			</br>
			<div style="margin-bottom: 20px;" class="table-responsive">
				<table class="table table-bordered table-dark table-hover">
					<thead>
						<tr style="background-color: blue;">

							<th width="10%"><input type="checkbox" id="select_all"
								name="Select" class="text"> Select All</th>
							<th width="5%" class="text">S.NO</th>
							<th width="15%" class="text">Customer</th>
							<th width="15%" class="text">PhoneNo</th>
							<th width="15%" class="text">Address</th>
							<th width="20%" class="text">Product</th>
							<th width="5%" class="text">Version</th>
							<th width="5%" class="text">Amount</th>

							<th width="5%" class="text">Edit</th>
						</tr>
					</thead>
					<%
						while (it.hasNext()) {
								dto = it.next();

					%>
					<tbody>
						<tr>
							<td align="center"><input type="checkbox" class="checkbox"
								name="ids" value="<%=dto.getId()%>"></td>
							<td class="text"><%=index++%></td>
							<td class="text"><%=dto.getCustomerName()%></td>
							<td class="text"><%=dto.getPhoneNo()%></td>
							<td class="text"><%=dto.getAddress()%></td>
							<td class="text"><%=dto.getProduct()%></td>
							<td class="text"><%=dto.getVersion()%></td>
							<td class="text"><%=dto.getAmount()%></td>

							<td class="text"><a href="OrderCtl?id=<%=dto.getId()%>">Edit</a></td>
						</tr>
					</tbody>
					<%
						}
					%>
				</table>
			</div>
			<table width="100%">
				<tr>
					<td><input type="submit" name="operation"
						class="btn btn-warning btn-md" style="font-size: 17px"
						value="<%=OrderListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td><input type="submit" name="operation"
						class="btn btn-primary btn-md" style="font-size: 17px"
						value="<%=OrderListCtl.OP_NEW%>"></td>

					<td><input type="submit" name="operation"
						class="btn btn-danger btn-md" style="font-size: 17px"
						value="<%=OrderListCtl.OP_DELETE%>"></td>

					<td align="right"><input type="submit" name="operation"
						class="btn btn-warning btn-md" style="font-size: 17px"
						style="padding: 5px;" value="<%=OrderListCtl.OP_NEXT%>"
						<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
				</tr>
				<tr></tr>
			</table>

			<%
				}
				if (list.size() == 0) {
			%>
			
			</br>
			<div class="row">
				<div class="col-md-4"></div>

				<%
					if (!ServletUtility.getErrorMessage(request).equals("")) {
				%>
				<div class=" col-md-4 alert alert-danger alert-dismissible">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<h4>
						<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
					</h4>
				</div>
				<%
					}
				%>




				<%
					if (!ServletUtility.getSuccessMessage(request).equals("")) {
				%>

				<div class="col-md-4 alert alert-success alert-dismissible"
					style="background-color: #80ff80">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<h4>
						<font color="#008000"><%=ServletUtility.getSuccessMessage(request)%></font>
					</h4>
				</div>
				<%
				
					}
				%>
				<div style="padding-left: 48%;">
					<input type="submit" name="operation"
						class="btn btn-primary btn-md" style="font-size: 17px"
						value="<%=OrderListCtl.OP_BACK%>">
				</div>

				<div class="col-md-4"></div>
			</div>

			<%
				}
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
		</form>


	</div>


</body>
<%@include file="FooterView.jsp"%>
</html>
