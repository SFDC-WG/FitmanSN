package gsn.http;

import gsn.acquisition2.server.SafeStorageController;
import gsn.acquisition2.server.SafeStorageServer;
import gsn.http.ac.User;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class stopGSNHandler implements RequestHandler {

	@Override
	public boolean isValid(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return true;
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setStatus(HttpServletResponse.SC_OK);
		String reqName = request.getParameter("name");

		// Added by Behnaz
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "no-cache");

		System.out.println("The stop handle was called");

		response.getWriter().write(
				performGSNStop(Integer.parseInt(request.getParameter("port"))));

	}

	private String performGSNStop(int safeStorageControllerPort) {

		try {
			// Socket socket = new
			// Socket(InetAddress.getLocalHost().getLocalHost(),
			// gsn.GSNController.GSN_CONTROL_PORT);
//			Socket socket = new Socket(InetAddress.getByName("localhost"),
//					gsnControllerPort);
//			PrintWriter writer = new PrintWriter(new BufferedWriter(
//					new OutputStreamWriter(socket.getOutputStream())));
//			writer.println(gsn.GSNController.GSN_CONTROL_SHUTDOWN);
//			writer.flush();
			
			
			      Socket socket = new Socket(InetAddress.getByName("localhost"), safeStorageControllerPort);
			      PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
			      writer.println(SafeStorageController.SAFE_STORAGE_SHUTDOWN);
			      writer.flush();
			      System.out.println("[Done]");
			      
			      SafeStorageServer sss= new SafeStorageServer(safeStorageControllerPort);
			      sss.shutdown();
			  
			return ("[Done]");
		} catch (Exception e) {
			System.out.println("FAILED::"+e.getMessage());
			return ("[Failed]");
		}
	}
}
