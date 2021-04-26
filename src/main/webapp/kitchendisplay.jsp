<html>
	<head>
		<script>
            var connectionOpen = false;
            var nextOrder = 1;
            function getOrders(){
                if(!connectionOpen){
                	connectionOpen = true;
                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange=function(){
                        if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                        	connectionOpen = false;
                        	nextOrder++;
                            var ordersDiv = document.getElementById("orders");
                            ordersDiv.innerHTML = xmlhttp.responseText + ordersDiv.innerHTML;
                        }
                    }
                    xmlhttp.open("GET", "kitchenAsyncServlet?size=" + nextOrder, true);
                    xmlhttp.send();
                }
            }
            setInterval(getOrders, 500);
        </script>
</head>


<body>
	<h1>Ricky's Restaurant - Live orders</h1>

	<div id="orders"></div>


</body>
</html>