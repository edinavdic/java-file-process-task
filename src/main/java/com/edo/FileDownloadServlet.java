package com.edo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet(name = "FileDownloadServlet", urlPatterns = "/download")
public class FileDownloadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIR = "uploadedFiles";
    private String uploadPath;

    @Override
    public void init() throws ServletException {
        String applicationPath = getServletContext().getRealPath("");
        uploadPath = applicationPath + File.separator + UPLOAD_DIR;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getParameter("fileName");
        String filePath = uploadPath + File.separator + fileName;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        File file = new File(filePath);

        if(file.exists()){

            String mimeType = "application/octet-stream";
            response.setContentType(mimeType);

            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
            response.setHeader(headerKey, headerValue);
            try{

                outputStream = response.getOutputStream();
                inputStream = new FileInputStream(file);

                int bytesRead = 0;
                final byte[] buffer = new byte[1024];

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            catch (IOException e){
                System.err.println("IOException: " + e.getMessage());
            }
            finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            }
        }
        else{
            response.setContentType("text/html");
            response.getWriter().println("Greska: Ne postoji datoteka ili direktorij, ili path ne valja!");
        }
    }
}
