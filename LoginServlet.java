import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class LoginServlet extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        // Get parameters from the login form
        String type = req.getParameter("type");
        String user = req.getParameter("username");
        String password = req.getParameter("password");

        // Create a session
        HttpSession session = req.getSession();
        session.setAttribute("name", user);

        try {
            int flag = 0;

            // Load Oracle JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establish connection to the Oracle database
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "system");

            // Create statement object
            Statement s = con.createStatement();

            if (type.equals("user")) {
                // Query the database to check for valid user credentials
                ResultSet rs = s.executeQuery("SELECT username, password FROM employee");

                while (rs.next()) {
                    // Validate the username and password
                    if (user.equals(rs.getString(1)) && password.equals(rs.getString(2))) {
                        out.println("Welcome " + user);

                        // Redirect to AfterLogin.html
                        RequestDispatcher dispatcher = req.getRequestDispatcher("AfterLogin.html");
                        dispatcher.include(req, res);
                        flag = 1;
                        break;
                    }
                }

                if (flag == 0) {
                    // Invalid credentials
                    out.println("<script>alert('Invalid username or password');</script>");
                    RequestDispatcher dispatcher = req.getRequestDispatcher("index.html");
                    dispatcher.include(req, res);
                }
            } else {
                // Admin login
                if (user.equals("ashhar") && password.equals("admin")) {
                    out.println("Welcome " + user);

                    // Redirect to Acc.html
                    RequestDispatcher dispatcher1 = req.getRequestDispatcher("accountDetails.html");
                    dispatcher1.forward(req, res);
                } else {
                    // Invalid admin credentials
                    out.println("<script>alert('Invalid username or password');</script>");
                    RequestDispatcher dispatcher = req.getRequestDispatcher("index.html");
                    dispatcher.include(req, res);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        }
    }
}
