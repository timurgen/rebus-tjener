package servlets;

import db.Game;
import db.GameDBAdapter;
import db.GamePunkt;
import java.io.IOException;
import java.util.Enumeration;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that provide "add game" functionality
 * @date 04 mai 2012
 * @author 490501
 * @version 1.0.0
 */
@WebServlet(name = "AddGame", urlPatterns = {"/addgame"})
public class AddGame extends HttpServlet {

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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    //PrintWriter out = response.getWriter();
    GameDBAdapter gdb = new GameDBAdapter();
    Game game;
    try {
        Enumeration paramNames = request.getParameterNames();
        String gameName = request.getParameterValues((String)paramNames.nextElement())[0];
        String varighet = request.getParameterValues((String)paramNames.nextElement())[0];
        String startDate = request.getParameterValues((String)paramNames.nextElement())[0];
        boolean isOpenGame;
        if(request.getParameter("gameIsOpenBox") != null) {
            paramNames.nextElement();
            isOpenGame = true; 
        }        
        else
            isOpenGame = false;
        game = new Game(request.getSession().getAttribute("username").toString(), gameName, Integer.valueOf(varighet), isOpenGame, startDate);
        String[] pointName = request.getParameterValues((String)paramNames.nextElement());
        String[] pointText = request.getParameterValues((String)paramNames.nextElement());
        String[] pointLocation = request.getParameterValues((String)paramNames.nextElement());
        String[] pointRadius = request.getParameterValues((String)paramNames.nextElement());
        for(int i = 0; i < pointName.length; i++) {
            String latLng = pointLocation[i].replace("(", "").replace(")", "");
            StringTokenizer st = new StringTokenizer(latLng, ",");
            double lat = Double.valueOf(st.nextToken());
            double lng = Double.valueOf(st.nextToken());
            GamePunkt gp = new GamePunkt(lat, lng, Integer.valueOf(pointRadius[i]), pointName[i], pointText[i]);
            game.addPoint(gp);
        }
        try {
            gdb.persistGame(game);
            response.sendRedirect("index.jsp");
            
        }
        catch(Exception e) {
            log(e.getMessage());
            e.printStackTrace();
        }
 //out.println("</table>");
   
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
