import javax.servlet.*;
import javax.swing.*;

import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Random;

public class NewAccount extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");

        PrintWriter out = res.getWriter();

        // Retrieve form data from the request
        String username = req.getParameter("username");
        String aadhar = req.getParameter("aadharno");
        String email = req.getParameter("emailname");
        String mobile = req.getParameter("mobileno");
        String fatherName = req.getParameter("fathername");
        String balance = req.getParameter("balanceno");
        String accountType = req.getParameter("accounttype");
        String gender = req.getParameter("gendername");
        String update = "";

        // Generate random 11-digit account number
        Random random = new Random();
        String s = "1234567890";
        char[] otp = new char[11];
        for (int i = 0; i < 11; i++) {
            otp[i] = s.charAt(random.nextInt(s.length()));
        }

        // Build the account number string
        String strArray[] = new String[otp.length];
        for (int i = 0; i < otp.length; i++) {
            strArray[i] = String.valueOf(otp[i]);
        }
        
        String s1 = Arrays.toString(strArray);
        String res1 = "";
        for (String num : strArray) {
            res1 += num;
        }

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establish database connection (Update with your Oracle database details)
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE", "system", "system"); 

            // Prepare SQL statement for inserting new account details
            PreparedStatement ps = con.prepareStatement(
    "INSERT INTO account_details (username, accountno, aadharno, email, mobileno, fathername, balance, accounttype, gender, updateinfo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
);

            // Set parameters in the SQL query
            ps.setString(1, username);
            ps.setString(2, res1);
            ps.setString(3, aadhar);
            ps.setString(4, email);
            ps.setString(5, mobile);
            ps.setString(6, fatherName);
            ps.setString(7, balance);
            ps.setString(8, accountType);
            ps.setString(9, gender);
            ps.setString(10, update);
            
            ps.executeUpdate(); // Execute the insert statement

            // Retrieve and display the account details
            PreparedStatement ps1 = con.prepareStatement("SELECT * FROM account_details WHERE accountno ='" + res1 + "'");
            ResultSet rs = ps1.executeQuery();

            while (rs.next()) {
                out.println("<br>username=" + rs.getString(1));
                out.println("<br>accountno=" + rs.getString(2));
                out.println("<br>aadharno=" + rs.getString(3));
                out.println("<br>email=" + rs.getString(4));
                out.println("<br>mobileno=" + rs.getString(5));
                out.println("<br>fathername=" + rs.getString(6));
                out.println("<br>balance=" + rs.getString(7));
                out.println("<br>accounttype=" + rs.getString(8));
                out.println("<br>gender=" + rs.getString(9));
            }

            // Dispatch request to user home page
            RequestDispatcher disp = req.getRequestDispatcher("newAccount.html");
            disp.include(req, res);
            out.println("<br>Registered successfully");

        } catch (Exception e) {
            out.println(e);
        }
        
        out.close();
    }
}
