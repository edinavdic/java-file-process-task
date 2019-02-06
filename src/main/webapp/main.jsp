<%@ page import="com.edo.FileDetails" %>
<%@ page import="com.edo.FileProcessStatus" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>File Download Navigation</title>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">

    <style>
        body {background: linear-gradient(to right, rgba(179,220,237,1) 0%, rgba(117,215,245,1) 22%, rgba(117,215,245,1) 79%, rgba(188,224,238,1) 100%);}
        h1 {font-family: Bahnschrift SemiCondensed; font-size: 50px; horiz-align: center; }
        h2 {font-family: Bahnschrift; font-weight: 400; horiz-align: center; font-size: 18px;}
        h3 {font-family: Bahnschrift; font-weight: 600; horiz-align: center;}
        #downloadLink{color: darkblue; font-family: Bahnschrift;}
        .main{text-align: center; align-content: center; padding-top: 2vh;}
        .box-item{text-align: center; align-content: center; padding: 30px;}
        .box-upload {border: 3px solid white;border-radius: 5px;display: inline-block; padding-top: 5px; padding-bottom: 15px; margin: 35px; width: 35%; align-content: center;}
        .button1 {border-radius: 8px; font-size: 14px;}
        .button2 {border-radius: 8px; font-size: 14px;}
    </style>

</head>

<body>
    <div class="main">
        <h1>FILE PROCESS JAVA WEBAPP</h1>

    <%
        FileDetails fileDetails = (FileDetails) request.getAttribute("fileDetails");
    %>

    <h3> <% out.println(fileDetails.getStatusString()); %> </h3>
    <div class="box-item">

        <% if(fileDetails.getProcessStatus() == FileProcessStatus.SUCCESS) { %>

            <i style="font-size: 40px" class="fas fa-file"></i>
            <h2><%out.println("Crc: " + fileDetails.getCrc32());%> </h2>
            <h2><%out.println("Original file size: " + fileDetails.getOriginalFileSize() + " KB");%></h2>

            <td align="center">
                <span id="fileDownload">
                    <a id="downloadLink" class="hyperLink" href="<%=request.getContextPath()%>/download?fileName=<%= fileDetails.getFileNameNew() %>">Download</a>
                </span>
            </td>

        <% } %>
    </div>
        <div class="box-upload">
            <h3> Choose .comp File to Upload in Server </h3>
            <form action="compressedfileprocess" method="post" enctype="multipart/form-data">
                <input class="button1"  type="file" name="file"/>
                <input class="button2"  type="submit" value="upload .comp"/>
            </form>
        </div>
        <div class="box-upload">
            <h3> Choose .txt File to Upload in Server </h3>
            <form action="txtfileprocess" method="post" enctype="multipart/form-data">
                <input class="button1" type="file" name="file"/>
                <input class="button2" type="submit" value="upload .txt"/>
            </form>
        </div>
</div>
</body>

</html>
