package su.practice.figure;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "su.practice.figure.FigureSelect", urlPatterns = {"/figureselect"})
public class FigureSelect extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");


    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            FigureDAO figureDAO = new FigureDAO();
            ArrayList<String> coords = figureDAO.getCoordinates();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("coords", coords.toArray());
            response.getWriter().print(jsonObject);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        processRequest(request, response);

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);

    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
