package user;

import exception.IllegallPersonException;

public class Person {
	public Person(){
		
	}
	protected String id;
	protected String name;
	protected String password;
	public boolean login(Person person) throws IllegallPersonException{
		if(person.id==null||person.name==null){
			throw new IllegallPersonException("Person is not illegal");
		}
		//validation code ......
		
		
		return false;
	}
}
