


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


//fetch the product info in the data base ,return and print out in the index
@WebServlet(urlPatterns = {"/PutFav"})
public class PutFav extends HttpServlet {
    
    private Connection conn;
    private Statement st;
    private ResultSet rs=null;
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, JSONException, SQLException {
        try {
        response.setContentType("text/html;charset=UTF-8");
        String id = request.getParameter("id");
        String jobtitle = request.getParameter("jobtitle");
        String company = request.getParameter("company");
        String url = request.getParameter("url");
        String imgurl = request.getParameter("imgurl");
        String jobdetail = request.getParameter("jobdetail");
        
        PrintWriter out = response.getWriter();


            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String connectionURL = "jdbc:derby://localhost:1527/ebizshopping";
            conn = DriverManager.getConnection(connectionURL, "test", "test");
            st = conn.createStatement();
            
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rstn = dbmd.getTables(null, null, "fav".toUpperCase(),null);
            if(!rstn.next()) st.execute("create table  fav(id varchar(80) PRIMARY KEY, jobtitle varchar(80), company varchar(80),url varchar(80),imgurl varchar(80),jobdetail varchar(255))");

             st.executeUpdate("insert into fav values (\""+id+"\",\""+jobtitle+"\",\""+company+"\",\""+url+"\",\""+imgurl+"\",\""+jobdetail+"\") "); 

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PutFav.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
        } catch (JSONException ex) {
            Logger.getLogger(PutFav.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PutFav.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
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
        } catch (JSONException ex) {
            Logger.getLogger(PutFav.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PutFav.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
