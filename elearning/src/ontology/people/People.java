package ontology.people;

import exception.IllegallPersonException;

public class People {
	public People(){
		
	}
	protected String id;
	protected String name;
	protected String password;
	public boolean login(People person) throws IllegallPersonException{
		if(person.id==null||person.name==null){
			throw new IllegallPersonException("Person is not illegal");
		}
		//validation code ......
		
		
		return false;
	}
}
