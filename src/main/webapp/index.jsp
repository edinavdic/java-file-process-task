<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <!-- OVO BI MOGAO BITI I HTML SAMO OVISI VIDJECEMO(usteda na brzini)-->
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>File Upload</title>
    </head>

    <body>
        <div>

            <h3> Choose .txt File to Upload in Server </h3>
            <form action="txtfileprocess" method="post" enctype="multipart/form-data">
                <input type="file" name="file"/>
                <input type="submit" value="upload .txt"/>
            </form>

        </div>
    </body>

</html>
