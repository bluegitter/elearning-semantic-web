<%@ page contentType="text/html;charset=utf-8" 
import="com.jspsmart.upload.*" %><%
		// 新建一个SmartUpload对象
	SmartUpload su = new SmartUpload();
		// 初始化
	su.initialize(pageContext);
		// 设定contentDisposition为null以禁止浏览器自动打开文件，
		//保证点击链接后是下载文件。若不设定，则下载的文件扩展名为
		//doc时，浏览器将自动用word打开它。扩展名为pdf时，
		//浏览器将用acrobat打开。
	su.setContentDisposition(null);
	String name = request.getParameter("elearner_id");
	// 下载文件
	su.downloadFile("/owl/"+name+".owl");
	//su.downloadFile("/owl/el001.owl");
%>
 