package com.php.Quagram.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.php.Quagram.database.DatabaseClass;

@WebServlet("/LobbyServlet")
public class LobbyTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/event-stream");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter printwriter = null;
		
		response.setIntHeader("Refresh", 3);
		
		try {
			double randomNumber = Math.random() * 10000;
			
			printwriter = response.getWriter();
			printwriter.print("data: " + "[next server time check event in " + Math.round(randomNumber/1000) + "seconds]\n");
			printwriter.println("data: " + "Number of User in Lobby:: " + DatabaseClass.getLobbyUsers().size() + "\n");
			//printwriter.print("data: " + "Time: " + Calendar.getInstance().getTime() + "\n\n");
			
			response.flushBuffer();
		} catch (IOException e) {
			printwriter.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
