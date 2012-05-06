
package servlets;

import db.User;
import db.UserDBAdapter;
import java.io.IOException;
import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet that provide user autentification and registration of new users 
 * @author 490501
 * @version 1.0.0
 * 
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        UserDBAdapter u = new UserDBAdapter();
        
        if(request.getParameter("mode").equals("login")) {//noen vil logge på
            String name = request.getParameter("name");
            String pass = request.getParameter("pass");
            try {
                if(u.autentificate(name, pass)) {
                   //setter opp session
                   HttpSession session = request.getSession();
                   session.setAttribute("username", name);
                   session.setAttribute("userid", u.getUserByName(name).getId());
                   response.sendRedirect("index.jsp"); //sender tilbake til main side
                }
            }
            catch(PersistenceException pe) {//dersom noe går feil sender tilbake med feilmelding
                log(pe.getMessage());
                pe.printStackTrace();
                response.sendRedirect("login.jsp?wrongmessage="+pe.getMessage());
            }
        }
        else if(request.getParameter("mode").equals("register")) {//noen vil bli registrert
            User user = new User(request.getParameter("name"), request.getParameter("pass"));
            if(u.persistUser(user))
                response.sendRedirect("login.jsp?wrongmessage=\"Registration completed you can log in now\"");
            else
                response.sendRedirect("login.jsp?wrongmessage=\"Username exists");
        }
        else if(request.getParameter("mode").equals("logout")) {//bruker vil logge av
            //terminate session
            request.getSession().invalidate();
            response.sendRedirect("index.jsp");
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
