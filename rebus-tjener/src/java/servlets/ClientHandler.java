/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import db.Game;
import db.GameDBAdapter;
import db.GuestDBAdapter;
import db.UserDBAdapter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
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
        //dersom mode ikke satt opp returnerer feil
        if(request.getParameter("mode") == null) {
            response.sendError(1045, "mode undefined");
            return;
        } 
        //dersom clinet har lyst på spill list
        if(request.getParameter("mode").equals("gamelist")) {
            getGameList(request, response);//returnerer navn klokkeslett og varighet i binaryformat
        }
        //Dersowm bruker vil laste ned valgte spill
        else if(request.getParameter("mode").equals("getgame")) {//returnerer valgt spill
            getGameById(request, response);
        }
        else {
            response.sendError(1047, "ukjent mode");
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
        GameDBAdapter gdb = new GameDBAdapter();
        ServletOutputStream sos = response.getOutputStream();
        ArrayList<Game> gameList = (ArrayList<Game>) gdb.getAllGames();
        for(Game g: gameList) {
            response.setContentType("application/octet-stream");
            sos.write(g.getId().byteValue());
            sos.write(" ".getBytes());
            sos.write(g.getAuthorName().getBytes());
            sos.write(" ".getBytes());
            sos.write(g.getName().getBytes());
            sos.write(" ".getBytes());
            sos.write(String.valueOf(g.getVarighet()).getBytes());
            sos.write(" ".getBytes());
            sos.write(g.isIsOpen() ? "true".getBytes() : "false".getBytes());
            sos.write(" ".getBytes());
            sos.write(String.valueOf(g.getStartDate()).getBytes());
            sos.write(System.getProperty("line.separator").getBytes());
            sos.flush();
        }  
    }
    
    /**
     * 
     * @param request
     * @param response
     * @throws IOException 
     */
    public void getGameById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        //sjekker om bruker satt opp game id
        if(request.getParameter("gameid") == null) {
            response.sendError(1042, "Game id undefined");
            return;
        }
        
        long gameId = Long.valueOf(request.getParameter("gameid"));
        //skaper GameDBhandler
        GameDBAdapter gdb = new GameDBAdapter();
        //get systemtime
        long currentTime = System.currentTimeMillis();
        //get oppstartstid
        long gameStart = gdb.getGameById(gameId).getStartDate();
        //sjekker om det er rett tid å laste ned spill, (spill kan nedlastes kun etter start)
        if(gameStart > currentTime) {
            response.sendError(1043, "Game starts at" + new Date(gameStart).toString());
            return;            
        }
        
        //sjekker om det er bruker eller gest vil laste ned spill
        if(request.getParameter("userid") != null & request.getParameter("guestid") != null){
            response.sendError(1042, "Sorry, but you can't be user and guest at the same time");
            return;
        }
        //håndterer brukere
        if(request.getParameter("userid") != null) {
            UserDBAdapter udb = new UserDBAdapter();
            long userId = Long.valueOf(request.getParameter("userid"));
            if(!gdb.getGameById(gameId).getAllPartisipants().contains(userId)) {
                response.sendError(1047, "You have not been registred in this game");
                return;                
            }  
        }
        //håndterer gester
        if(request.getParameter("guestid") != null) {
            GuestDBAdapter guestDB = new GuestDBAdapter();
            String guestId = request.getParameter("guestid");
            if(!guestDB.getGuestById(guestId).isRegistred(gameId)) {
                response.sendError(1047, "You have not been registred in this game");
                return;                 
            }
        }
        //dersom alt er ok, sender løp til stream output
        Game g = gdb.getGameById(gameId);
        response.setContentType("application/octet-stream");
        ServletOutputStream sos = response.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(sos);
        
        oos.writeObject(g);
        oos.flush();
        oos.close();
        sos.flush();
        sos.close();
    }
    
    //send result
    public void sendResults(HttpServletRequest request, HttpServletResponse response) {
        
    }
    //get results
}
