package dev.httpNuker.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dev.httpNuker.data.MenuDao;
import dev.httpNuker.data.MenuDaoFactory;
import dev.httpNuker.domain.Order;

@WebServlet(value="/kitchenAsyncServlet", asyncSupported = true)
public class KitchenAsyncServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		AsyncContext asyncContext = request.startAsync(request, response);
		KitchenAsyncTask task = new KitchenAsyncTask();
		task.setAsyncContext(asyncContext);
		asyncContext.start(task);

	}
	
}
