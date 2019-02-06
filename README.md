# java-file-process-task

Technologies used:

IntelliJ IDEA Ultimate 

Glassfish Server 5.0

JDK 8

Apache Maven 3.6.0


Maven used throughout development for solving dependencies, compiling, building and project packaging.
Project created from Maven template maven-archetype-webapp. 
Java technologies I used in project are Servlets & JSP from Java EE specifications.
File format extension .comp is used for saving CRC code and compressed data. 
Through main.jsp user can upload either .txt or .comp file and call the corresponding servlet for processing.

<img src="https://i.imgur.com/BvTm2MP.png" width="400" height="300">
<img src="https://i.imgur.com/CWaaOkK.jpg" width="500" height="200">
Download link is shown to user after compression/decompression is finished successfully.

CRC is calculated using the java.util.zip.CRC32 class. 
Source code for arithmetic coding implementation is obtained from https://github.com/nayuki/Reference-arithmetic-coding 
in which I tweaked ArithmeticCompress and ArithmeticDecompress classes to include/read CRC first.
