<%--
  Created by IntelliJ IDEA.
  User: SyamukonkaMoonga
  Date: 15/04/2023
  Time: 16:45
  To change this template use File | Settings | File Templates.
--%>

<html>
<%@ page import="com.major.ebs.entity.Customer" %>
<%@ include file="header.jsp" %>

<head>
    <title>View Customer</title>
</head>
<body>
<div class="container">
    <div class=" pd_sm col_center w100 mg_v_lg">
        <div>
        <% Customer customer = (Customer)request.getAttribute("customer");
            String typeIcon;
            if(customer.getType().equals("Domestic"))
                typeIcon = "fa-home";
            else if(customer.getType().equals("Commercial"))
                typeIcon = "fa-store";
            else
                typeIcon = "fa-gavel";
        %>
        </div>
        <div class="w_100 max_500 rounded-2 items_center shadow">

            <div class="pd_t_md pd_h_md row_between">
                <h4>Editing <%= customer.getName() %> </h4>
                <h4><span class="txt_xs">ID: </span><%= customer.getId() %></h4>
            </div>

            <hr/>

            <% String emsg = (String)request.getAttribute("error_message");
                if(emsg!=null) { %>
            <div class="pd_md">
                <p><i class="fa-solid fa-warning"> </i> <%= emsg %> </p>
            </div>
            <% } %>
            <% String smsg = (String)request.getAttribute("success_message");
                if(smsg!=null) { %>
            <div class="pd_md">
                <p><i class="fa-solid fa-check-circle">  </i><%= smsg %></p>
            </div>
            <% } %>

            <form action="submitUpdate" method="post" class="w100 pd_md">
                <input type="hidden" name="id" value="<%= customer.getId() %>">
                <div class="mb-3">
                    <label for="name" class="form-label">Name</label>
                    <input name="name" value="<%= customer.getName() %>" class="form-control" id="name" type="text" placeholder="Customer's full name">
                </div>
                <div class="mb-3">
                    <label for="address" class="form-label">Address</label>
                    <input type="text" value="<%= customer.getAddress() %>" name="address" class="form-control" id="address" placeholder="Home address of the customer">
                </div>
                <label class="form-label">Customer category</label>
                <div class="mb-3 form-floating">
                    <select class="form-select" id="floatingSelect" name="type" aria-label="Floating label select example">
                        <option selected value="<%= customer.getType() %>"> <%= customer.getType() %> </option>
                        <option value="Domestic">Domestic</option>
                        <option value="Commercial">Commercial</option>
                        <option value="Government">Government</option>
                    </select>
                    <label for="floatingSelect">This customer is </label>
                </div>
                <div class="row_between">
                    <a class="btn btn-dark fw600" href="view?id=<%=customer.getId()%>"><i class="fa-solid fa-xmark mg_r_xs"></i>Close</a>
                    <button type="submit" class="btn btn-primary" >Update customer</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
