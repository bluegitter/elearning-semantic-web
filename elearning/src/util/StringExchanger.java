package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringExchanger {
	
	
	/*******************************************************************************
	 * exchange the String from the Sparql type into the common type we use.
	 * Eg: cmp.cf9.001^^<http://www.w3.org/2001/XMLSchema#string>
	 *   -->  cmp.cf9.001
	 * @param Sparql string result
	 * @return Common type string
	 *******************************************************************************/
	
	public static String getCommonString(String s){
		char [] c= s.toString().toCharArray();
		StringBuffer sb = new StringBuffer();
		int i =0;
		while(c[i]!='^'){
			sb.append(c[i]);
			i++;
		}
		return sb.toString();
	}
	public static String getCommonFloat(String s){
		char [] c= s.toString().toCharArray();
		StringBuffer sb = new StringBuffer();
		int i =0;
		while(c[i]!='^'){
			sb.append(c[i]);
			i++;
		}
		return sb.toString();
	}
	/*******************************************************************************
	 * exchange the String from the Sparql type into the common type we use.
	 * Eg: "cmp.cf9.001"^^<http://www.w3.org/2001/XMLSchema#string>
	 *   -->  cmp.cf9.001
	 * @param Sparql string result
	 * @return Common type string
	 *******************************************************************************/
	public static String getCommonString2(String s){
		char [] c = s.toCharArray();
		StringBuffer sb = new StringBuffer();
		int i =0;
		while(c[i]!= '"'){
			i++;
		}
		if(c[i]=='"'){
			i++;
			while(c[i]!='"'){
				sb.append(c[i]);
				i++;
			}
		}
		return sb.toString().trim();
	}
	/*******************************************************************************
//	 * exchange the String from the Common type into the Sparql type we use.
	 * Eg: cmp.cf9.001 --> 
	 *    "cmp.cf9.001\"^^<http://www.w3.org/2001/XMLSchema#string>
	 * @param Common string result
	 * @return Sparql type string
	 *******************************************************************************/
	//when you don't choose en as the language, the data type of the string is as what postfix shows
	//However, when you want to use language limitation, the postfix should be like (xml:lang="en")
	//rather than (rdf:datatype="http://www.w3.org/2001/XMLSchema#string")
	public static String getSparqlString(String s){
		String postfix = "^^<http://www.w3.org/2001/XMLSchema#string>";
		StringBuffer sb = new StringBuffer();
		sb.append('"');
		sb.append(s);
		sb.append('"');
		sb.append(postfix);
		return sb.toString();
	}
	public static String getSparqlFloat(String s){
		String postfix = "^^<http://www.w3.org/2001/XMLSchema#float>";
		StringBuffer sb = new StringBuffer();
		sb.append('"');
		sb.append(s);
		sb.append('"');
		sb.append(postfix);
		return sb.toString();
	}
	/******************************************************
	 * parse the string like "2010-12-02T14:34:53" into a date
	 * "2010-12-02T14:34:53" -->"2010-12-02 14:34:53" --> Date
	 * @param s
	 * @return
	 ****************************************************/
	public static Date parse(String dateString){
		String s = dateString.replace('T', ' ');
		//String s = "2010-12-02 14:34:53";
		DateFormat dateFormat =getDateFormat();
		Date date = null;
		try {
			date = dateFormat.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	public static String parseDateToString(Date date){
		DateFormat dateFormat =getDateFormat();
		String s = dateFormat.format(date);
		char c[] = s.toCharArray();
		c[10]='T';
		return new String(c);
	}
	public static DateFormat getDateFormat(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	public static void main(String [] args){
		
	}
}
