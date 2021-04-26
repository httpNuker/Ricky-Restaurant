package dev.httpNuker.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dev.httpNuker.data.MenuDao;
import dev.httpNuker.data.MenuDaoFactory;
import dev.httpNuker.domain.Order;
import dev.httpNuker.websockets.KitchenDisplaySessionHandler;
import dev.httpNuker.websockets.KitchenDisplaySessionHandlerFactory;

@WebServlet("/processorder.html")
public class ProcessOrderServlet extends HttpServlet{
	
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException { 
	MenuDao menuDao = MenuDaoFactory.getMenuDao();
	List<Order> orders;
	orders = menuDao.getAllOrders();
	request.setAttribute("orders", orders);
	
	List<String> statuses = new ArrayList<String>();
	statuses.add("order accepted");
	statuses.add("payment received");
	statuses.add("being prepared");
	statuses.add("ready for collection");
	
	request.setAttribute("statuses", statuses);

	ServletContext context = getServletContext();
	RequestDispatcher dispatch = context.getRequestDispatcher("/processorder.jsp");
	dispatch.forward(request,response);
	}
	
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException { 
		MenuDao menuDao = MenuDaoFactory.getMenuDao();
		Long id = Long.valueOf(request.getParameter("id"));
		String status =  request.getParameter("status");
		System.out.println(id + " : " + status);
		menuDao.updateOrderStatus(id,status);
		
		Order order = menuDao.getOrder(id);
		KitchenDisplaySessionHandler handler = KitchenDisplaySessionHandlerFactory.getHandler();
		handler.amendOrder(order);
		
		doGet(request,response);
		}
}
