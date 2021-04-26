package dev.httpNuker.websockets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.websocket.Session;

import org.json.JSONObject;

import dev.httpNuker.data.MenuDao;
import dev.httpNuker.data.MenuDaoFactory;
import dev.httpNuker.domain.Order;

public class KitchenDisplaySessionHandler {

	private List<Session> sessions = new ArrayList<Session>();
	
	public void addSession(Session session) {
		sessions.add(session);
		sendAllOrders(session);
	}
	
	public void removeSession(Session session) {
		sessions.remove(session);
	}
	
	private void sendMessage(JSONObject message) {
		for (Session session : sessions) {
			try {
				session.getBasicRemote().sendText(message.toString());
			} catch (IOException e) {
				removeSession(session);
			}
		}
	}
	
	private void sendMessage(JSONObject message, Session session) {
		try {
			session.getBasicRemote().sendText(message.toString());
		} catch (IOException e) {
			removeSession(session);
		}
	}
	
	public JSONObject generateJSONForOrder(Order order) {
		JSONObject json = new JSONObject();
		json.append("id", order.getId().toString());
		json.append("status", order.getStatus());
		json.append("content", order.toString());
		json.append("action", "add");
		json.append("update", new Date().toString());
		return json;
	}
	
	public void newOrder(Order order) {
		sendMessage(generateJSONForOrder(order));
	}

	public void amendOrder(Order order) {
		JSONObject json = new JSONObject();
		json.append("id", order.getId().toString());
		json.append("action", "remove");
		sendMessage(json);
		if (!order.getStatus().equals("ready for collection"))
		newOrder(order);
	}
	
	private void sendAllOrders(Session session) {
		MenuDao dao = MenuDaoFactory.getMenuDao();
		List<Order> orders = dao.getAllOrders();
		for (Order order : orders) {
			if (!order.getStatus().equals("ready for collection"))
			sendMessage(generateJSONForOrder(order), session);
		}
	}
	
}
