import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;

@WebServlet("/delete")
public class FileDeleteServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "C:/uploads";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filename = request.getParameter("filename");
        if (filename != null && !filename.isEmpty()) {
            File file = new File(UPLOAD_DIR + File.separator + filename);
            if (file.exists()) {
                file.delete();
                response.getWriter().println("File deleted successfully: " + filename);
            } else {
                response.getWriter().println("File not found: " + filename);
            }
        } else {
            response.getWriter().println("No filename provided.");
        }
    }
}
