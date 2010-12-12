package util;

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
	public static void main(String [] args){
		String s = "\"cmp.cf9.001\"^^<http://www.w3.org/2001/XMLSchema#string>";
		String t = getCommonString(s);
		String o = getSparqlString(t);
		System.out.println(t);
		System.out.println(o);
	}
}
