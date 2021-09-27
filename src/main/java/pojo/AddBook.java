package pojo;

public class AddBook {
	
	private String aisle;
	private String isbn;
	private String name;
	private String author;
	
	public void setAisle(String aisle)
	{
		this.aisle = aisle;
	}
	
	public String getAisle()
	{
		return aisle;
	}
	
	public void setIsbn(String isbn)
	{
		this.isbn = isbn;
	}
	
	public String getIsbn()
	{
		return isbn;
	}
	
	public void setBook(String book)
	{
		this.name = book;
	}
	
	public String getBook()
	{
		return name;
	}
	
	public void getAuthor(String author)
	{
		this.author = author;
	}
	
	public String getAuthor()
	{
		return author;
	}

}
