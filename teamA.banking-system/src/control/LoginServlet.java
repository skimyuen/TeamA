package control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CustomerDao;
import dao.UserDao;
import model.Customer;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	RequestDispatcher ref;
	UserDao userDao;
	HttpSession session;
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession(true);
		// to authenticate login
		if(userDao==null){
			userDao = new UserDao();
		}
		// get parameters for login credentials
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		// get parameters for customer request unlock if any
		out = response.getWriter();
		if(request.getParameter("requestUnlock")!=null){
			if (Boolean.parseBoolean(request.getParameter("requestUnlock")))
			userDao.requestUnlock(username);
			out.println("Please wait while your request is being processed.");
			ref = request.getRequestDispatcher("login.html");
			ref.include(request, response);
		}
		if(password!=null){
			// use UserDao to authenticate user
			int login = userDao.login(username, password);
			//out.print("<h1>"+login+"</h1>");
			//response.sendRedirect("update_success.html?hi="+login);
			switch(login){
			case 11: // admin logged in
				// set admin username into session param "admin"?
				session = request.getSession();
				session.setAttribute("admin", username);
				// redirect to admin view
				ref = request.getRequestDispatcher("admin_view.jsp");
				ref.include(request, response);
				break;
			case 10: // customer logged in
				session = request.getSession();
				// retrieve customer object from UserDao 
				// set customer object into session param "customer"
				Customer customer = userDao.getCustomer();
				session.setAttribute("customer", customer);
				// set last login in database
				if(customer.getLastLogin()!=null)
					CustomerDao.setLastLogin(customer);
				// redirect to customer view
				ref = request.getRequestDispatcher("customer_view.jsp");
				ref.include(request, response);
				break;
			case 2: // customer invalid login once: two tries left before lock
				// decrease counter
				userDao.updateCounter(username, login);
				// print message and include page login.html
				triesLeft(login);
				ref = request.getRequestDispatcher("login.html");
				ref.include(request, response);
				break;
			case 1: // customer invalid login twice: one try left before lock
				// decrease counter
				userDao.updateCounter(username, login);
				// print message and include page login.html
				triesLeft(login);
				ref = request.getRequestDispatcher("login.html");
				ref.include(request, response);
				break;
			case 0: // customer invalid login thrice: call accountLock function
				// prompt "request to unlock?"
				promptUnlock(username);
				// print message and include page login.html
				ref = request.getRequestDispatcher("login.html");
				ref.include(request, response);
				break;
			default: // invalid
				out.println("Invalid username or password.");
				ref = request.getRequestDispatcher("login.html");
				ref.include(request, response);
				break;	
			}
		}
		// redirect according to user access
	}
	
	private void promptUnlock(String username){
		   out.println("<script type=\"text/javascript\">");
		   out.println("if(confirm('Customer account has been locked. Request unlock?'))");
		   out.println("location='LoginServlet?requestUnlock=true&username="+username+"'");
		   out.println("</script>");
	}
	
	private void triesLeft(int count){
		   out.println("<script type=\"text/javascript\">");
		   out.println("alert('Wrong password for customer. "+count+" try left before account gets locked.')");
		   out.println("location='login.html'");
		   out.println("</script>");
	}

}
