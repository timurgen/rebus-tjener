/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import db.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 490501
 * @version 1.0.1
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception {      
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
        //dersom en vil sende resultat etter å ha gjennomgått løp
        else if(request.getParameter("mode").equals("sendresult")) {//returnerer valgt spill
            sendResults(request, response);
        }
        //dersom en vil ha resultater på telefon
        else if(request.getParameter("mode").equals("getresult")) {//returnerer valgt spill
            getResults(request, response);
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        //response.setContentLength(9999);
        for(Game g: gameList) {
            response.setContentType("application/octet-stream");
            sos.write(String.valueOf(g.getId()).getBytes());
            sos.write(",".getBytes());
            sos.write(g.getAuthorName().getBytes());
            sos.write(",".getBytes());
            sos.write(g.getName().getBytes());
            sos.write(",".getBytes());
            sos.write(String.valueOf(g.getVarighet()).getBytes());
            sos.write(",".getBytes());
            sos.write(g.isIsOpen() ? "true".getBytes() : "false".getBytes());
            sos.write(",".getBytes());
            sos.write(String.valueOf(g.getStartDate()).getBytes());
            sos.write(",".getBytes());
            

        }  
        sos.flush();
        sos.close();
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
        //ToSend g = new ToSend(2, 3, "hui");
        response.setContentType("application/octet-stream");
        ServletOutputStream sos = response.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(sos);
        
        oos.writeObject(g);
        oos.flush();
        oos.close();
        sos.flush();
        sos.close();
    }
    
    /**
     * 
     * @param request
     * @param response
     * @throws IOException
     * @throws Exception 
     */
    public void sendResults(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
        System.out.println("methode sendResult from " +request.getRemoteAddr());
        if(request.getSession().getAttribute("userid") == null & request.getSession().getAttribute("guest") == null) {
            response.sendError(666, "mangler brukerid");
            System.out.println("mangler brukerid");
            return;
        }
        if(request.getSession().getAttribute("userid") != null & request.getSession().getAttribute("guest") != null) {
            response.sendError(666, "you can't be user and guest at the same time");
            return;
        }
        if(request.getParameter("gameid") == null) {
            response.sendError(666, "mangler gameid");
            return;            
        }
        if(request.getParameter("result") == null) {
            response.sendError(666, "mangler result time");
            return;            
        }
         if(request.getParameter("quantity") == null) {
            response.sendError(666, "mangler punktantall");
            return;            
        }
        if(request.getSession().getAttribute("userid") != null) {
            //håndterer bruker
            //long userId = Long.valueOf(request.getParameter("userid"));
            String userName = (String)request.getSession().getAttribute("username");
            long gameId = Long.valueOf(request.getParameter("gameid"));
            long result = Long.valueOf(request.getParameter("result"));
            int points = Integer.valueOf(request.getParameter("quantity"));
            //PrintWriter out = response.getWriter();
            ServletOutputStream sos = response.getOutputStream();
            try {
                System.out.println("prøver å lagre resultat"); //debugg
                //sjekker om resultat allerede lagret
                GameDBAdapter gdb = new GameDBAdapter();
                ArrayList<Result> r = gdb.getGameById(gameId).getResults();
                Iterator<Result>  it = r.iterator();
                while(it.hasNext()) {
                    if(it.next().getUserName().equals(userName)) {
                        response.sendError(666, "Result exists");
                        return;
                    }
                }

                //gdb.addResultToGame(gameId, userId, points, result);
                gdb.addResultToGame(gameId, userName, points, result);
                response.setContentType("text/html");
                response.setContentLength("results saved successfully".length());
                //out.write("results saved successfully");
                sos.write("Hui pizda!".getBytes());
            }
            catch(Exception e) {
                //out.write(e.getMessage());
                sos.write(e.getMessage().getBytes());
            }
            finally {
                //out.flush();
                //out.close();
                sos.flush();
                sos.close();
            }
        }
        else if(request.getSession().getAttribute("guest") != null) {
            //ellers håndterer gest
            String userName = (String)request.getSession().getAttribute("guest");
            long gameId = Long.valueOf(request.getParameter("gameid"));
            long result = Long.valueOf(request.getParameter("result"));
            int points = Integer.valueOf(request.getParameter("quantity"));
            PrintWriter out = response.getWriter();
            try {
                GameDBAdapter gdb = new GameDBAdapter();
                //gdb.addResultToGame(gameId, userId, points, result);
                gdb.addResultToGame(gameId, userName, points, result);
                response.setContentType("text/html");
                out.write("results saved successfully");
            }
            catch(Exception e) {
                out.write(e.getMessage());
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, e);
            }
            finally {
                out.flush();
                out.close();                
            }
        } 
    }
    
    /**
     * 
     * @param request
     * @param response
     * @throws IOException 
     */
    public void getResults(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //sjekker om bruker satt opp game id
        if(request.getParameter("gameid") == null) {
            response.sendError(1042, "Game id undefined");
            return;
        }
        GameDBAdapter gdb = new GameDBAdapter();
        ServletOutputStream sos = response.getOutputStream();
        Game g = gdb.getGameById(Long.valueOf(request.getParameter("gameid")));
        ArrayList<Result> results = g.getResults();
        
        for(Result s : results) {
            sos.write(Long.toString(s.getGamerId()).getBytes());
            sos.write(",".getBytes());
            sos.write(Long.toString(s.getResult()).getBytes());
            sos.write(System.getProperty(";").getBytes());
        }
        sos.flush();
        sos.close();
        
    }
}
