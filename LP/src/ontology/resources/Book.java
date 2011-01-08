package ontology.resources;

import ontology.people.People;

public class Book extends ISCB_Resource{
	private String bookName;
	private String bookIsbn;
	private People bookAuthor;
	private String bookPublisher;
	public Book(){
		
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getBookIsbn() {
		return bookIsbn;
	}
	public void setBookIsbn(String bookIsbn) {
		this.bookIsbn = bookIsbn;
	}
	public People getBookAuthor() {
		return bookAuthor;
	}
	public void setBookAuthor(People bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
	public String getBookPublisher() {
		return bookPublisher;
	}
	public void setBookPublisher(String bookPublisher) {
		this.bookPublisher = bookPublisher;
	}
	
}
