import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@WebServlet("/upload")
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "C:/uploads";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Create upload directory if it doesn't exist
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        // Get the file part from the request
        Part filePart = request.getPart("file");
        if (filePart != null) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String fileExtension = getFileExtension(fileName);
            
            // Validate file type
            if (isValidFileType(fileExtension)) {
                File file = new File(UPLOAD_DIR + File.separator + fileName);
                filePart.write(file.getAbsolutePath());
                response.getWriter().println("File uploaded successfully: " + fileName);
            } else {
                response.getWriter().println("Invalid file type: " + fileExtension);
            }
        } else {
            response.getWriter().println("No file uploaded.");
        }
    }

    private boolean isValidFileType(String fileExtension) {
        String[] validExtensions = {"jpg", "jpeg", "png", "gif", "mp4", "avi", "mov", "mp3", "wav"};
        for (String ext : validExtensions) {
            if (fileExtension.equalsIgnoreCase(ext)) {
                return true;
            }
        }
        return false;
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}
