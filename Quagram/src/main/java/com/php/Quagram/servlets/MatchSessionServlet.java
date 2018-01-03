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

@WebServlet("/MatchSessionServlet")
public class MatchSessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//content type must be set to text/event-stream
		response.setContentType("text/event-stream");	
		
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Connection", "keep-alive");

		//encoding must be set to UTF-8
		response.setCharacterEncoding("UTF-8");
		
		String matchSessionID = request.getParameter("matchSessionID");
		
		PrintWriter printwriter = null;

		printwriter = response.getWriter();
		
		ArrayList<User> matchSessionUsers = new DatabaseQuagramUsers().getUserForMatchSessionID(matchSessionID);
		JSONClientOutput jsonOutput = new JSONClientOutput();
		JSONArray invitationJSONArray = jsonOutput.parseUserArrayListToJSON(matchSessionUsers, "");
		
		printwriter.print("data: { \"data\": " + invitationJSONArray + " }" + "\n\n");
		response.flushBuffer();
		printwriter.close();
	}

}
