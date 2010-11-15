<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<f:view>
  <head>
    
    <title>My JSP 'index.jsp' starting page</title>
	
  </head>
  
  <body>
    This is my JSP page.<br> 
    <h:form id="login_form">
    <h:outputText value="elearner_id" />
    <h:inputText required="" value="#{elearner.id}" size="" id="elearner_id"/><br>
    <h:outputText value="elearner_password"/>
    <h:inputSecret id="elearner_password" value="#{elearner.password}" redisplay="" />
    <br>
    <h:commandButton id="submit_login" action="#{elearner.login}" value="go" />
				<h:selectOneListbox></h:selectOneListbox>
			</h:form> 
    </body>
    </f:view>
</html>
