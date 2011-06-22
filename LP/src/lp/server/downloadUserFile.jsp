<%@ page contentType="text/html;charset=utf-8" 
         import="java.io.*" %>
<%
String name = request.getParameter("elearner_id");
 OutputStream outputStream = response.getOutputStream();
 InputStream inputStream = new FileInputStream(application.getRealPath("/")+"owl/"+name+".owl");
 byte[] buffer = new byte[1024];
 int i = -1;
 while ((i = inputStream.read(buffer)) != -1) {
  outputStream.write(buffer, 0, i);
  }
 outputStream.flush();
 outputStream.close();
 inputStream.close();
 outputStream = null;
%>
