package com.edo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.zip.CRC32;

@SuppressWarnings("Duplicates")
@WebServlet(name = "FileBaseProcessServlet")
public abstract class FileBaseProcessServlet extends HttpServlet {
    private static final String UPLOAD_DIR = "uploadedFiles";
    private String uploadPath;


    @Override
    public void init() throws ServletException {
        String applicationPath = getServletContext().getRealPath(""); // Get the absolute path of the web application
        uploadPath = applicationPath + File.separator + UPLOAD_DIR;
        System.out.println("Upload Directory Path---->" + uploadPath);

        File fileUploadDirectory = new File(uploadPath);
        if (!fileUploadDirectory.exists()) {
            fileUploadDirectory.mkdirs();
        }
    }


    // *---- methods ----* //

    protected String getUploadPath() {
        return uploadPath;
    }

    protected  void forward(HttpServletRequest request, HttpServletResponse response, FileDetails fileDetails) throws ServletException, IOException {
        request.setAttribute("fileDetails", fileDetails);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/main.jsp");
        dispatcher.forward(request, response);
    }

    protected boolean processUpload(final Part part, FileDetails fileDetails)throws ServletException, IOException {

        boolean pass = true;
        OutputStream outputStream = null;
        InputStream filecontent = null;

        if(part != null) {
            fileDetails.setOriginalFileSize(part.getSize() / 1024);

            try{
                outputStream = new FileOutputStream(new File(getUploadPath() + File.separator + fileDetails.getFileName()));
                filecontent = part.getInputStream();

                int bytesRead = 0;
                final byte[] bytes = new byte[1024];

                while ((bytesRead = filecontent.read(bytes)) != -1) { //Reads some number of bytes from the input stream and stores them into the buffer array b. The number of bytes actually read is returned as an integer.
                    outputStream.write(bytes, 0, bytesRead);
                }

                System.out.println("Original file " + fileDetails.getFileName() + " saved at " + getUploadPath());
            }
            catch (IOException e){
                pass = false;
                fileDetails.setStatusString("IOException: processUpload");
                System.err.println("IOException: " + e.getMessage());
            }
            finally {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (filecontent != null) {
                    filecontent.close();
                }
            }
        }
        else{
            // false
            fileDetails.setStatusString("Part je null, upload greska!");
            return false;
        }

        return pass;
    }


    protected String extractFileName(Part part){
        String fileName = "";
        String contentDisposition = part.getHeader("content-disposition");
        String[] items = contentDisposition.split(";");
        for(String item : items){
            if(item.trim().startsWith("filename")){
                fileName = item.substring(item.indexOf("=") + 2, item.length() - 1);
            }
        }
        return fileName;
    }

    protected boolean fileNameValidation(FileDetails fileDetails, String end){
        String fileName = fileDetails.getFileName();
        if(!fileName.equals("")){
            if(!fileName.endsWith(end)){
                fileDetails.setStatusString("Upload greska, fajl nije " + end);
                return false;
            }
        }
        else{
            fileDetails.setStatusString("Upload greska!");
            return false;
        }
        return true;
    }

    protected long calculateCRC32(String filePath) throws IOException {
        boolean failure = false;
        InputStream inputStream = null;
        CRC32 crc32 = new CRC32();
        int nextByte = 0;
        try {
            inputStream = new FileInputStream(filePath);
            while((nextByte = inputStream.read()) != -1){
                crc32.update(nextByte);
            }
        }
        catch (IOException e){
            failure = true;
            System.err.println("Crc IOException: " + e.getMessage());
        }
        finally {
            if(inputStream != null){
                inputStream.close();
            }
        }
        return (failure) ? -1 : crc32.getValue();
    }
}
