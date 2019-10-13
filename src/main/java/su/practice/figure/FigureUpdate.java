package su.practice.figure;

import org.json.JSONException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "su.practice.linestring.FigureUpdate", urlPatterns = {"/figureupdate"})
public class FigureUpdate extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException {

        try {
            FigureDAO figureDAO = new FigureDAO();
            Figure figure = new Figure();
           String newwkt = request.getParameterValues("new")[0];
           String oldwkt = request.getParameterValues("old")[0];

           figure.setId(figureDAO.getId(oldwkt));
           figure.setWkt(newwkt);
           figureDAO.update(figure);
           System.out.println("---data updated---");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.getWriter().print(ex);
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
        return "Short description";
    }

}
