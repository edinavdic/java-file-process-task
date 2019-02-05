package com.edo;

import com.edo.arithmetic.ArithmeticCompress;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.ByteBuffer;

@SuppressWarnings("Duplicates")
//        The location attribute does not support a path relative
//        to the application context!
//        This location is used to store files temporarily while
//        the parts are processed or when the size of the file exceeds the
//        specified fileSizeThreshold setting. The default location is "".
@MultipartConfig( location = "/tmp",
        fileSizeThreshold = 1024 * 1024 * 10,   // 10 MB
        maxFileSize = 1024 * 1024 * 50,         // 50 MB
        maxRequestSize = 1024 * 1024 * 90)      // 90 MB
@WebServlet(name = "FileTxtProcessServlet", urlPatterns = "/txtfileprocess")
public class FileTxtProcessServlet extends FileBaseProcessServlet {
    private static final long serialVersionUID = 1L;


    // This method is called by the servlet container to process a 'POST' request
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        FileDetails fileDetails = new FileDetails();
        final Part part = request.getPart("file");

        // ** file name and extension ** //
        fileDetails.setFileName(extractFileName(part));
        System.out.println("File name--->" + fileDetails.getFileName());

        if(!fileNameValidation(fileDetails, ".txt")){
            forward(request, response, fileDetails);
            return;
        }

        fileDetails.setFileNameNew(fileDetails.getFileName().replace(".txt", ".comp"));
        System.out.println("File name new--->" + fileDetails.getFileNameNew());
        // ** file name and extension ** //


        // ** original file upload ** //
        if(!processUpload(part, fileDetails)){
            forward(request, response, fileDetails);
            return;
        }
        // ** original file upload ** //


        // ** get crc ** //
        fileDetails.setCrc32(calculateCRC32(getUploadPath()+ File.separator + fileDetails.getFileName()));
        System.out.println("File crc--->" + fileDetails.getCrc32()); // 8 bytes
        if(fileDetails.getCrc32() == -1){
            fileDetails.setStatusString("Originalni fajl spasen. Greska u crc racunanju!");
            forward(request, response, fileDetails);
            return;
        }
        // ** get crc ** //


        // *** ARITHMETIC CODING *** //
        ArithmeticCompress.run(getUploadPath() + File.separator + fileDetails.getFileName(),
                getUploadPath() + File.separator + fileDetails.getFileNameNew(),
                longToBytes(fileDetails.getCrc32()));
        // *** ARITHMETIC CODING *** //


        fileDetails.setStatusString("Uploaded at: " + getUploadPath());
        fileDetails.setProcessStatus(FileProcessStatus.SUCCESS);
        forward(request, response, fileDetails);
    }

    private byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }
}
