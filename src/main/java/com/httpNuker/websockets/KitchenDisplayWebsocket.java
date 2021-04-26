package dev.httpNuker.websockets;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;

import dev.httpNuker.data.MenuDao;
import dev.httpNuker.data.MenuDaoFactory;
import dev.httpNuker.domain.Order;

@ServerEndpoint("/kitchenManagement")
public class KitchenDisplayWebsocket {

	@OnOpen
	public void open(Session session) {
		KitchenDisplaySessionHandler handler = KitchenDisplaySessionHandlerFactory.getHandler();
		handler.addSession(session);
	}
	
	@OnClose
	public void close(Session session) {
		KitchenDisplaySessionHandler handler = KitchenDisplaySessionHandlerFactory.getHandler();
		handler.removeSession(session);
	}
	
	@OnError
	public void onError (Throwable error) {
		throw new RuntimeException(error);
	}
	
	@OnMessage
	public void handleMessage(String message, Session session) {
		JSONObject json = new JSONObject(message);
		Long id = json.getLong("id");
		String status = json.getString("status");
		
		MenuDao menuDao = MenuDaoFactory.getMenuDao();
		menuDao.updateOrderStatus(id,status);
		
		Order order = menuDao.getOrder(id);
		KitchenDisplaySessionHandler handler = KitchenDisplaySessionHandlerFactory.getHandler();
		handler.amendOrder(order);
		
		
	}
	
}
