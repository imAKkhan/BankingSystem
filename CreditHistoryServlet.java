import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class CreditHistoryServlet extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("application/xml");
        PrintWriter out = res.getWriter();

        // Get account number from request
        String accountno = req.getParameter("accountno");

        if (accountno != null && !accountno.isEmpty()) {
            try {
                // Connect to Oracle DB
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "system");

                // Fetch credit history based on account number
                PreparedStatement stmt = con.prepareStatement(
                        "SELECT id, username, amount, transaction_date FROM credit_history WHERE accountno = ?");
                stmt.setString(1, accountno);
                ResultSet rs = stmt.executeQuery();

                // Start building XML response
                StringBuilder xmlResponse = new StringBuilder();
                xmlResponse.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                xmlResponse.append("<creditHistory>");

                // Process the result set and populate XML
                while (rs.next()) {
                    xmlResponse.append("<transaction>");
                    xmlResponse.append("<id>").append(rs.getInt("id")).append("</id>");
                    xmlResponse.append("<username>").append(rs.getString("username")).append("</username>");
                    xmlResponse.append("<amount>").append(rs.getDouble("amount")).append("</amount>");
                    xmlResponse.append("<date>").append(rs.getDate("transaction_date")).append("</date>");
                    xmlResponse.append("</transaction>");
                }

                xmlResponse.append("</creditHistory>");

                // Send XML response
                out.println(xmlResponse.toString());

            } catch (Exception e) {
                out.println("<h3>Error: " + e.getMessage() + "</h3>");
                e.printStackTrace(out);
            }
        } else {
            out.println("<h3>Please provide a valid account number.</h3>");
        }
    }
}
