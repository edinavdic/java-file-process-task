package com.edo;

import java.io.Serializable;


public class FileDetails implements Serializable {
    private static final long serialVersionUID = 1L;

    private long crc32;
    private long originalFileSize;
    private String fileName;
    private String fileNameNew;
    private String statusString;
    private FileProcessStatus processStatus;

    public FileDetails() {
        crc32 = 0;
        originalFileSize = 0;
        fileName = "";
        fileNameNew = "";
        statusString = "";
        processStatus = FileProcessStatus.FAILURE;
    }

    public long getCrc32() {
        return crc32;
    }

    public void setCrc32(long crc32) {
        this.crc32 = crc32;
    }

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public long getOriginalFileSize() {
        return originalFileSize;
    }

    public void setOriginalFileSize(long originalFileSize) {
        this.originalFileSize = originalFileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileNameNew() {
        return fileNameNew;
    }

    public void setFileNameNew(String fileNameNew) {
        this.fileNameNew = fileNameNew;
    }

    public FileProcessStatus getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(FileProcessStatus processStatus) {
        this.processStatus = processStatus;
    }
}
