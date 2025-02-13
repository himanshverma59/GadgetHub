package in.gadgethub.servlet;

import in.gadgethub.dao.impl.UserDaoImpl;
import in.gadgethub.pojo.UserPojo;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String cpassword = request.getParameter("cpassword");
        String address = request.getParameter("address");
        String userEmail = request.getParameter("useremail");
        String mobile = request.getParameter("mobile");
        String status;

        // Validate pincode input
        int pincode;
        try {
            pincode = Integer.parseInt(request.getParameter("pincode"));
        } catch (NumberFormatException e) {
            status = "Invalid Pincode!";
            request.setAttribute("message", status);
            RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
            rd.forward(request, response);
            return;
        }

        // Check if passwords match
        if (!password.equals(cpassword)) {
            status = "Confirm password did not match!";
            request.setAttribute("message", status);
            RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
            rd.forward(request, response);
            return;
        }

        // Register user
        UserDaoImpl userDao = new UserDaoImpl();
        UserPojo user = new UserPojo(userName, userEmail, mobile, address, password, pincode);
        String isRegistered = userDao.registerUser(user);

        if (isRegistered.equals("Registration successful")) {
            // Registration successful, forward to login page
            RequestDispatcher rd=request.getRequestDispatcher("login.jsp?message="+isRegistered);
                rd.include(request,response);
        } else {
            // Registration failed, stay on register page with message
            status = "Registration failed! Please try again.";
            request.setAttribute("message", status);
            RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
            rd.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Handles user registration";
    }
}
