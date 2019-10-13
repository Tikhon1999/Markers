package su.practice.figure;

import org.json.JSONException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "su.practice.figure.FigureInsert", urlPatterns = {"/figureinsert"})
public class FigureInsert extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException {


    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            FigureDAO figureDAO = new FigureDAO();
            Figure figure = new Figure();
            String wkt = request.getParameterValues("wkt")[0];
            figure.setWkt(wkt);
            figureDAO.save(figure);
            System.out.println("---data saved---");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.getWriter().print(ex);
        }
        processRequest(request, response);

    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
