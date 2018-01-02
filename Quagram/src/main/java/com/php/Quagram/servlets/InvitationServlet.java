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

import com.php.Quagram.database.DatabaseQuagramInvitations;
import com.php.Quagram.model.Invitation;
import com.php.Quagram.service.JSONClientOutput;


@WebServlet("/InvitationServlet")
public class InvitationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//content type must be set to text/event-stream
		response.setContentType("text/event-stream");	
		
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Connection", "keep-alive");

		//encoding must be set to UTF-8
		response.setCharacterEncoding("UTF-8");
		
		String sessionID = request.getParameter("sessionID");
		
		PrintWriter printwriter = null;

		printwriter = response.getWriter();
		
		ArrayList<Invitation> userInvitations = new DatabaseQuagramInvitations().getInvitationsForUser(sessionID);
		JSONClientOutput jsonOutput = new JSONClientOutput();
		JSONArray invitationJSONArray = jsonOutput.parseInvitationListToJSON(userInvitations);
		
		printwriter.print("data: { \"data\": " + invitationJSONArray + " }" + "\n\n");
		response.flushBuffer();
		printwriter.close();
	}

}
