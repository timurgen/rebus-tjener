/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import db.Game;
import db.GameDBAdapter;
import db.UserDBAdapter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 490501
 */
@WebServlet(name = "ClientHandler", urlPatterns = {"/client"})
public class ClientHandler extends HttpServlet {

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
//        if(request.getParameter("username") == null) {
//            response.sendError(1043, "user name did not found");
//            return;
//        }
//        if(request.getParameter("userid") == null) {
//            response.sendError(1044, "user id did not found");
//            return;
//        }
            
        
        if(request.getParameter("mode") != null) {//sjekker om parameter gamemmode satt opp
            if(request.getParameter("mode").equals("gamelist")) {//sjekker om brukeren vil plukke ut gamelist
                getGameList(request, response);//returnerer navn klokkeslett og varighet i binaryformat
            }
            else if(request.getParameter("mode").equals("getgame")) {//returnerer valgt spill

                
             }
            else {
                System.out.println("p");
            }
        }
        else {
            response.sendError(1045, "mode undefined");
            return;
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
    
    
    public void getGameList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //sjekker om session id stemmer
        if(!request.getParameter("sid").equals(request.getSession().getId()))
            response.sendError(1041, "Session invalid");
        GameDBAdapter gdb = new GameDBAdapter();
        ServletOutputStream sos = response.getOutputStream();
        ArrayList<Game> gameList = (ArrayList<Game>) gdb.getAllGames();
        for(Game g: gameList) {
            response.setContentType("application/octet-stream");
            sos.write(g.getName().getBytes());
            sos.write(" ".getBytes());
            sos.write(String.valueOf(g.getStartDate()).getBytes());
            sos.write(" ".getBytes());
            sos.write(String.valueOf(g.getVarighet()).getBytes());
            sos.write(System.getProperty("line.separator").getBytes());
        }
        
    }
    
    public void getGameById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(request.getParameter("gameid") == null) {
            response.sendError(1042, "Game id not undefined");
            return;
        }
        //sjekker om bruker ble registrert 
        GameDBAdapter gdb = new GameDBAdapter();
        long gameid = Long.valueOf(request.getParameter("gameid"));
        Game g = gdb.getGameById(gameid);
        UserDBAdapter udb = new UserDBAdapter();
        long userId = Long.valueOf(request.getParameter("name"));
        response.setContentType("application/octet-stream");
        ServletOutputStream sos = response.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(sos);
        
        oos.writeObject(g);
        oos.flush();
        oos.close();
        sos.flush();
        sos.close();
    }
}
