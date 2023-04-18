<html>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.major.ebs.entity.Customer" %>
<%@ include file="header.jsp" %>
<body>
<div class="row_between pd_h_md pd_v_sm">
    <div>
        <p class="genos txt_md"><i class="fa-solid fa-bolt txt_sm mg_r_sm"></i>Electricity Billing<span class="text-danger"></span></p>
    </div>
    <div>
        <button class="btn fw500">Logout <i class="mg_l_sm fa-solid fa-arrow-right-from-bracket "></i></button>
    </div>
</div>

<hr class="mg_0">

<div class="container-lg pd_h_md mg_t_sm">

    <p>Hello,</p>
    <h3 class="">${name}</h3>

</div>

<div class="row_left">
    <% ArrayList<Customer> customers = (ArrayList<Customer>) request.getAttribute("customers"); %>
</div>

<div class="container-lg pd_h_md mg_t_lg">
    <form class="col_top w100">
        <label for="search" class="form-label">Search customers</label>
        <div class=" row_between w100">
            <input type="search" class="form-control fillup" id="search" aria-describedby="search">
            <button type="submit" class="btn mg_l_sm btn-primary">Search</button>
        </div>
    </form>
    <form action="new">
        <button class="btn btn-outline-primary"><i class="fa-solid fa-plus mg_r_sm"></i>New Customer</button>
    </form>
</div>
<div class="container-lg pd_h_md">
    <table class="table">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Customer</th>
        <th scope="col">ID</th>
        <th scope="col">Units</th>
        <th scope="col"></th>
    </tr>
    </thead>
    <tbody>
    <% for(int i = 0; i < customers.size(); i++) {
        Customer customer = customers.get(i);

        String typeIcon;
        if(customer.getType().equals("Domestic"))
            typeIcon = "fa-home";
        else if(customer.getType().equals("Commercial"))
            typeIcon = "fa-store";
        else
            typeIcon = "fa-gavel";
    %>
    <tr class="shaker_parent shower_parent" >
        <th scope="row"><%= i+1 %></th><td>
        <div>
            <p><%= customer.getName() %></p>
            <p class="mg_t_xs txt_mn fw700 text-secondary"><i class="fa-solid <%= typeIcon %> txt_mn mg_r_xs"></i><%= customer.getType() %></p>
        </div>
    </td>
        <td class="" ><p> <%= customer.getId() %> </p></td>
        <td class="" ><p><%= String.format("%.2f",customer.getUnits()) %> <span class="mg_l_xs fw700 txt_mn mg_b_xs">KWH</span></p></td>
        <td class="" >
            <div class="col_center h100">
                <div class="row_right no_wrap shower_child items_center">

                    <form action="view" method="get">
                        <input type="hidden" value="<%= customer.getId() %>" name="id" >
                        <button class="btn" > <i class="fa-solid fa-ellipsis-vertical"></i></button>
                    </form>

                    <form action="delete" method="post">
                        <input type="hidden" value="<%= customer.getId() %>" name="id" >
                        <button class="btn shake_once_child" > <i class="fa-solid fa-trash text-danger"></i></button>
                    </form>
                </div>
            </div>

        </td>
    </tr>
    <% } %>
    </tbody>
</table>

</div>



<%@ include file="scripts.jsp" %>

</body>