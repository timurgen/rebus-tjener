
package servlets;

import db.UserDBAdapter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 04.05.2012
 * @author 490501
 * @version 1.0.0
 */
@WebServlet(name = "AutentificateClient", urlPatterns = {"/android"})
public class AutentificateClient extends HttpServlet {
    

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
        request.getSession().setMaxInactiveInterval(3600*3);
        response.setContentType("text/plain");
        PrintWriter out = null;
        try {
            
            if(request.getParameter("guest")!= null && request.getParameter("guest").equals("true")) {
                //out = response.getWriter();
                //out.println("you will be handled as guest");
                response.setContentType("application/octet-stream");
                request.getSession().setAttribute("guestid", request.getParameter("guestid"));
                byte[] sessionId = request.getSession().getId().getBytes();
                response.setContentLength(sessionId.length);
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(sessionId);
                outputStream.flush();
            }
            else if(request.getParameter("name") != null && request.getParameter("pass") != null) {
                String name = request.getParameter("name");
                String pass = request.getParameter("pass");
                UserDBAdapter udb = new UserDBAdapter();
                try {
                    if(udb.autentificate(name, pass)) {                       
                        ServletOutputStream outputStream = response.getOutputStream();
                        byte[] userName = udb.getUserByName(name).getName().getBytes();
                        byte[] userId = String.valueOf(udb.getUserByName(name).getId()).getBytes();
                        byte[] sessionId = request.getSession().getId().getBytes();
                        request.getSession().setAttribute("userid", udb.getUserByName(name).getId());
                        request.getSession().setAttribute("username", udb.getUserByName(name).getName());
                        response.setContentType("application/octet-stream");
                        response.setContentLength(userName.length+userId.length+sessionId.length+" ".length()+" ".length());
                        outputStream.write(userName);
                        outputStream.write(" ".getBytes());
                        outputStream.write(sessionId);
                        outputStream.write(" ".getBytes());
                        outputStream.write(userId);
                        outputStream.flush();
                        //out = response.getWriter();
                        //out.write("hui");
                        //out.close();
                }
                    else {
                        response.sendError(1, "Wrong username or password");
                    }
                    
                }
                catch(Exception e) {
                    response.sendError(4030, e.getMessage());
                }

                
            }
            else {
                //out.println("ebat, domething går weird");
            }
        } finally {
            
            //out.close();
            
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
