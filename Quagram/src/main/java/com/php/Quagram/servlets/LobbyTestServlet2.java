package com.php.Quagram.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.php.Quagram.database.DatabaseQuagramUsers;
import com.php.Quagram.model.User;
import com.php.Quagram.service.JSONClientOutput;


@WebServlet("/LobbyServlet2")
public class LobbyTestServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//content type must be set to text/event-stream
		response.setContentType("text/event-stream");	
		
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Connection", "keep-alive");

		//encoding must be set to UTF-8
		response.setCharacterEncoding("UTF-8");

		PrintWriter printwriter = null;

		printwriter = response.getWriter();
		
		ArrayList<User> users = new DatabaseQuagramUsers().getLobbyUsers();
		
		JSONClientOutput test = new JSONClientOutput();
		JSONArray hello = test.doIt(users);
		
		System.out.println(hello);
		
		printwriter.print("data: { \"data\": " + hello + " }" + "\n\n");
		//printwriter.println("data: " + "Number of User in Lobby:: " + DatabaseQuagramSingleton.sharedInstance.getLobbyUsers().size() + "\n");
		response.flushBuffer();
		printwriter.close();
	}

}
