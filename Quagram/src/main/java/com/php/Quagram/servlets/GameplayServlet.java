package com.php.Quagram.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.php.Quagram.exceptions.MatchSessionIDNotFoundException;
import com.php.Quagram.service.GameplayService;

@WebServlet("/GameplayServlet")
public class GameplayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//content type must be set to text/event-stream
		response.setContentType("text/event-stream");	
		
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Connection", "keep-alive");

		//encoding must be set to UTF-8
		response.setCharacterEncoding("UTF-8");
		
		response.setIntHeader("Refresh", 3);
		
		String sessionID = request.getParameter("sessionID");
		String matchSessionID = request.getParameter("matchSessionID");
		
		try {
			JSONObject gameplayRoundJSON = new GameplayService().getGameplayRoundJSON(sessionID, matchSessionID); 
			
			PrintWriter printwriter = null;

			printwriter = response.getWriter();
			
			printwriter.print("data: { \"data\": " + gameplayRoundJSON + " }" + "\n\n");
			response.flushBuffer();
			printwriter.close();
		} catch (NullPointerException e) {
			System.out.println("##GameplayServlet##GameplayServlet GameplayServlet ## GameplayServlet# ## GameplayServlet");
			throw new MatchSessionIDNotFoundException("Hey :)");
		}
	}

}
