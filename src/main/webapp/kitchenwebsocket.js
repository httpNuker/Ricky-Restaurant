
var socket = new WebSocket("ws://localhost:8080/kitchenManagement");
socket.onmessage = onMessage;

function onMessage(event) {

    var order = JSON.parse(event.data);  
    if (order.action == "add") {
        displayOrder(order);
    }
    if (order.action == "remove") {
        removeOrder(order);
    }
}

function displayOrder(order) {
    var content = document.getElementById("content");
    var div = document.createElement("div");
    div.setAttribute("id", order.id);
    
    var html = "<h3>Order " + order.id + "</h3>";
    html += "<p>Last update :" + order.update + "</p>";
    html += "<p>Current status : " + order.status + "</p>";
    html += "<p>Contents : " + order.content + "</p>";
    
    html += "<form id='form" + order.id + "'>";
    html+= "<input type='hidden' name='id' value	='" + order.id + "' />";
	html+= "<select name='status'>";
	html+= "<option value='order accepted' selected>order accepted</option>";
	html+= "<option value='payment received'>payment received</option>";
	html+= "<option value='being prepared'>being prepared</option>";
	html+= "<option value='ready for collection'>ready for collection</option>"; 
	html+= "</select><input type='button' value='Update' onClick='sendUpdate(\"" + order.id + "\");'/></form>";

    div.innerHTML= html;
    
    content.appendChild(div);
}

function removeOrder(order) {
 
    var div = document.getElementById(order.id);
    div.remove();
}

function sendUpdate(id) {
	var form = document.getElementById("form"+id);
    var status = form.elements["status"].value;
    var message = { 
    		"id" : id,
    		"status" : status
    };
    socket.send(JSON.stringify(message));
}