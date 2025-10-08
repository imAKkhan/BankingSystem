import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;

public class LogoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        // Invalidate the session
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Generate HTML response for successful logout
        out.println("<html>");
        out.println("<head><title>Logout - SBI Account Dashboard</title></head>");
        out.println("<body>");
        out.println("<h2>You have successfully logged out!</h2>");
        out.println("<a href='login.html' class='btn'>Go to Login</a>");
        out.println("</body>");
        out.println("</html>");

        out.close();
    }
}
