/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;


/**
 *
 * @author zhangqian
 */
@WebServlet(urlPatterns = {"/GetResults"})
public class GetResults extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out1 = response.getWriter();
        String jt = request.getParameter("jt");
        String q = request.getParameter("q");
        String sort = request.getParameter("sort");
        String start = request.getParameter("start");
        String limit = request.getParameter("limit");
        String l = request.getParameter("l");
      
            try{
              
                String URL="http://api.indeed.com/ads/apisearch?v=2&useragent=Mozilla/%2F4.0%28Firefox%29&format=json&callback=?&st=jobsite&publisher=5788115038535202&jt"+jt+"&q="+q+"&sort="+sort+"&start="+start+"&limit="+limit+"&l="+l+"&userip=1.2.3.4";                       
                
                System.out.println(URL);
                
                String a = getJsonString(URL);
                JSONObject joresult = new JSONObject(a);
                JSONArray resarr=joresult.getJSONArray("results");
                
                for (int i = 0; i < resarr.length(); i++) {
                    JSONObject row = resarr.getJSONObject(i);
                    String comname=row.getString("company");
                    String comurl="http://api.glassdoor.com/api/api.htm?v=1&format=json&t.p=62289&t.k=h6nXqOo7VRC&action=employers&userip=127.0.0.1&q="+URLEncoder.encode(comname, "UTF-8");
                    //String encodedcomurl=java.net.URLEncoder.encode(comurl,"UTF-8");
                    String comjson = getJsonString(comurl);
                    JSONObject comnamejo = new JSONObject(comjson);
                    
                     String imgurl="https://media.glassdoor.com/sql/238230/rdx-squarelogo-1424263255509.png" ; 
                     String cultureAndValuesRating="N/A";
                     String compensationAndBenefitsRating="N/A";
                     String seniorLeadershipRating="N/A";
                     String careerOpportunitiesRating="N/A";
                    if(comnamejo.getJSONObject("response").getJSONArray("employers").length()!=0){
                    if(comnamejo.getJSONObject("response").getJSONArray("employers").getJSONObject(0).getString("squareLogo").length()!=0)
                            imgurl=comnamejo.getJSONObject("response").getJSONArray("employers").getJSONObject(0).getString("squareLogo");
                    cultureAndValuesRating=comnamejo.getJSONObject("response").getJSONArray("employers").getJSONObject(0).getString("cultureAndValuesRating");
                    seniorLeadershipRating=comnamejo.getJSONObject("response").getJSONArray("employers").getJSONObject(0).getString("seniorLeadershipRating");
                    careerOpportunitiesRating=comnamejo.getJSONObject("response").getJSONArray("employers").getJSONObject(0).getString("careerOpportunitiesRating");
                    compensationAndBenefitsRating=comnamejo.getJSONObject("response").getJSONArray("employers").getJSONObject(0).getString("compensationAndBenefitsRating");
                    }
                   row.put("imgurl", imgurl);
                   row.put("cultureAndValuesRating", cultureAndValuesRating);
                   row.put("seniorLeadershipRating", seniorLeadershipRating);
                   row.put("careerOpportunitiesRating", careerOpportunitiesRating);
                   row.put("compensationAndBenefitsRating", compensationAndBenefitsRating);
                   

            }
                

                out1.println(joresult.toString()); 

        } 
        catch(Exception e){
            System.out.println("error");
        }

            out1.close();


  }
    public String getJsonString(String urlPath) throws Exception {  
        URL url = new URL(urlPath);  
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
        connection.connect();  
        InputStream inputStream = connection.getInputStream();  
      
        Reader reader = new InputStreamReader(inputStream, "UTF-8");  
        BufferedReader bufferedReader = new BufferedReader(reader);  
        String str = null;  
        StringBuffer sb = new StringBuffer();  
        while ((str = bufferedReader.readLine()) != null) {  
            sb.append(str);  
        }  
        //System.out.println(sb.toString());
        reader.close();  
        connection.disconnect();  
        return sb.toString();  
    }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
