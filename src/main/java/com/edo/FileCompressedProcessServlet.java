package com.edo;

import com.edo.arithmetic.ArithmeticDecompress;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

@SuppressWarnings("Duplicates")
@MultipartConfig( location = "/tmp",
        fileSizeThreshold = 1024 * 1024 * 10,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 90)
@WebServlet(name = "FileCompressedProcessServlet", urlPatterns = "/compressedfileprocess")
public class FileCompressedProcessServlet extends FileBaseProcessServlet {
    private static final long serialVersionUID = 1L;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        FileDetails fileDetails = new FileDetails();
        final Part part = request.getPart("file");

        // ** file name and extension ** //
        fileDetails.setFileName(extractFileName(part));
        System.out.println("File name--->" + fileDetails.getFileName());

        if(!fileNameValidation(fileDetails, ".comp")){
            forward(request, response, fileDetails);
            return;
        }

        fileDetails.setFileNameNew(fileDetails.getFileName().replace(".comp", ".txt"));
        System.out.println("File name new--->" + fileDetails.getFileNameNew());
        // ** file name and extension ** //

        // ** original file upload ** //
        if(!processUpload(part, fileDetails)){
            forward(request, response, fileDetails);
            return;
        }
        // ** original file upload ** //


        // *** ARITHMETIC DECOMPRESSION *** //
        byte[] sentCrc = ArithmeticDecompress.run(getUploadPath() + File.separator + fileDetails.getFileName(),
                getUploadPath() + File.separator + fileDetails.getFileNameNew());
        // *** ARITHMETIC DECOMPRESSION *** //


        // ** crc check ** //
        fileDetails.setCrc32(bytesToLong(sentCrc));
        System.out.println("Procitani CRC: " + fileDetails.getCrc32());

        long newCrc = calculateCRC32(getUploadPath() + File.separator + fileDetails.getFileNameNew());
        System.out.println("Izracunati CRC: " + newCrc);

        if(newCrc != fileDetails.getCrc32()){
            fileDetails.setStatusString("Izracunati CRC ne podudara se sa poslanim.");
            forward(request, response, fileDetails);
            return;
        }
        // ** crc check ** //


        fileDetails.setStatusString("Uploaded at: " + getUploadPath());
        fileDetails.setProcessStatus(FileProcessStatus.SUCCESS);
        forward(request, response, fileDetails);
    }

    private long bytesToLong(byte[] b) {
        long result = 0;
        for (int i = 0; i < 8; i++) {
            result <<= 8;
            result |= (b[i] & 0xFF);
        }
        return result;
    }
}
