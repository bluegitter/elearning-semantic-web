/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lp.log.polynomial;

/**
 *
 * @author t
 */
public class MyPolyomial {
	public int a;
	public int b;
	public int c;
	public int d;
	public int result;
	public char s1;
	public char s2;
	public char s3;
	private char s[]={'+','-','*','/'};
	//5？7？9？4=7吧
	//+ - +
	//
	public static void main(String [] args){
		MyPolyomial m= new MyPolyomial(5,7,9,4,7,'+','-','+');
		System.out.println(m.isAnswer());
		MyPolyomial m2= new MyPolyomial(5,7,9,4,7,'+','*','+');
		System.out.println(m2.isAnswer());
		MyPolyomial m3= new MyPolyomial(5,7,9,4,7,'+','*','/');
		System.out.println(m3.isAnswer());

	}
        public MyPolyomial(){
            
        }
	public MyPolyomial(int a,int b ,int c ,int d, int result,char s1,char s2,char s3){
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.result = result;
		this.s1 = s1;
		this.s2 = s2;
		this.s3 = s3;
	}
	public int getDivision(int a, int b){
		if(b==0){
			return Integer.MAX_VALUE;
		}
		if(a/b*b==a){
			return a/b;
		}
		return Integer.MAX_VALUE;
	}
	public int getResult(int a, char s,int b){
		switch(s){
			case '+': return a+b;
			case '-': return a-b;
			case '*': return a*b;
			case '/': return getDivision(a,b);
		}
		return 0;
	}
	public int getResult(){
		int result = 0;
		if(s1=='+'||s1=='-'){
			if(s2=='+'||s2=='-'){
				if(s3 =='+'||s3=='-'){
					int temp1 = getResult(a,s1,b);
					int temp2 = getResult(temp1,s2,c);
					result = getResult(temp2,s3,d);
				}else{
					int temp1 = getResult(c,s3,d);
					int temp2 = getResult(a,s1,b);
					result = getResult(temp2,s2,temp1);
				}
			}else{
				int temp1 = getResult(b,s2,c);
				if(s3 =='+'||s3=='-'){
					int temp2 = getResult(a,s1,temp1);
					result = getResult(temp2,s3,d);
				}else{
					int temp2 = getResult(temp1,s3,d);
					result = getResult(a,s1,temp2);
				}
			}
		}else{
			int temp1= getResult(a,s1,b);
			if(s2=='+'||s2=='-'){
				if(s3 =='+'||s3=='-'){
					int temp2 = getResult(temp1,s2,c);
					result = getResult(temp2,s3,d);
				}else{
					int temp2 = getResult(c,s3,d);
					result = getResult(temp1,s2,temp2);
				}
			}else{
				int temp2 = getResult(temp1,s2,c);
				result = getResult(temp2,s3,d);
			}
		}
		return result;
	}
	public boolean isAnswer(){
		if(getResult()==result){
			System.out.println("result:"+result);
				return true;
		}
		return false;
	}
}
