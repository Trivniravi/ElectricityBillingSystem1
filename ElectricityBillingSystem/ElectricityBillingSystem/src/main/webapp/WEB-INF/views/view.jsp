
<html>
<%@ page import="com.major.ebs.entity.Customer" %>
<%@ include file="header.jsp" %>

<head>
    <title>View Customer</title>
    <script>
        function calculateBill() {
            // Get the values of the quantity and price input fields
            var amount = parseInt(document.getElementById("amount").value);
            var rate = parseFloat(document.getElementById("rate").value);

            // Calculate the total bill
            var total = amount / rate;
            document.getElementById("units").value = total;

            // Update the value of the total input field
            document.getElementById("total").innerHTML = total.toFixed(2)+'<i class="fa-solid txt_xs mg_l_xs"> KWH</i>' ;
        }
    </script>
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

            <div class="pd_t_md pd_h_md">
                <div class="row_between no_wrap">
                    <h4> <%= customer.getName() %> </h4>
                    <div class="row_right">
                        <form action="update" method="get">
                            <input type="hidden" name="id" value="<%= customer.getId() %>">
                            <button type="submit" class="btn btn-white mg_r_sm shaker_parent child"> <i class="fa-solid fa-pen shake_once_child"></i></button>
                        </form>
                        <form action="delete" method="post">
                            <input type="hidden" name="id" value="<%= customer.getId() %>">
                            <button type="submit" class="btn btn-white shaker_parent"> <i class="fa-solid fa-trash shake_once_child"></i></button>
                        </form>
                    </div>
                </div>
                <p class="txt_sm mg_t_sm text-secondary"><i class="fa-solid txt_sm fa-location-dot mg_r_xs" ></i> <%= customer.getAddress() %> </p>
                <p class="txt_sm mg_t_sm text-primary"><i class="fa-solid txt_mn <%= typeIcon %> mg_r_xs" ></i> <%= customer.getType() %> </p>
            </div>

            <hr/>

            <div class="row_right w100">
            </div>

            <div class="row pd_md">
                <div class="col-12 ">
                    <div class="rounded-1 bg-ligh col_center items_center mg_r_xs pd_h_sm pd_v_md">
                        <h1> <%= String.format("%.2f",customer.getUnits()) %> <span><i class="txt_xs fa-solid">KWH</i></span> </h1>
                        <p>AVAILABLE UNITS</p>
                    </div>
                </div>
                <%--<div class="col-6">
                    <div class="bg-ligh rounded-1 col_center items_center mg_l_xs pd_h_sm pd_v_md">
                        <h1> $ <%= customer.() %> </h1>
                        <p>OWING</p>
                    </div>
                </div>--%>
            </div>

            <div class="w100 pd_h_md">
                <form method="post" action="pay">
                    <div class="mb-3">
                        <label for="amount" class="form-label">Amount</label>
                        <input name="" class="form-control" id="amount" type="number" onchange="calculateBill()">
                        <input name="units" id="units" type="hidden" value="">
                        <input name="id" value="<%=  customer.getId() %>" id="id" type="hidden">
                    </div>
                    <div class="row_between items_center">
                        <button type="submit" class="btn btn-outline-primary">
                            <i class="fa-solid fa-sack-dollar mg_r_xs"></i> Process payment
                        </button>
                        <div class="mg_l_lg col_top">
                            <input type="hidden" id="rate" value="<%= customer.getRate() %>">
                            <p class="text-secondary txt_xs">ESTIMATED UNITS</p>
                            <h5 id="total"> </h5>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
