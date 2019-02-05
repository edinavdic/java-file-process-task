<%@ page import="com.edo.FileDetails" %>
<%@ page import="com.edo.FileProcessStatus" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>File Download Navigation</title>
</head>

<body>
<div>

    <%
        FileDetails fileDetails = (FileDetails) request.getAttribute("fileDetails");
    %>

    <h3> <% out.println(fileDetails.getStatusString()); %> </h3>

    <% if(fileDetails.getProcessStatus() == FileProcessStatus.SUCCESS) {
            out.println("Crc: " + fileDetails.getCrc32());
            out.println("Original file size: " + fileDetails.getOriginalFileSize() + " KB");
    %>
    <td align="center">
        <span id="fileDownload">
            <a id="downloadLink" class="hyperLink" href="<%=request.getContextPath()%>/download?fileName=<%= fileDetails.getFileNameNew() %>">Download</a>
        </span>
    </td>
    <% } %>
    <h3> Choose .comp File to Upload in Server </h3>
    <form action="compressedfileprocess" method="post" enctype="multipart/form-data">
        <input type="file" name="file"/>
        <input type="submit" value="upload .comp"/>
    </form>
    <h3> Choose .txt File to Upload in Server </h3>
    <form action="txtfileprocess" method="post" enctype="multipart/form-data">
        <input type="file" name="file"/>
        <input type="submit" value="upload .txt"/>
    </form>

</div>
</body>

</html>
