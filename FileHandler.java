import java.io.*;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
public class FileHandler {
	
	private String content;
	private int index;
	
	public FileHandler(String filename)
	{
		Path myPath = Paths.get(filename);
		try // Try is used to throw an exception in case there the file does not exist.
		{
			content = new String(Files.readAllBytes(myPath));
		}
		catch(Exception e)
		{
			System.err.println("Error: Reading from file");
			System.exit(1);
		}
	}
	
	public FileHandler(String code, boolean test)
	{
		content = code;
	}
	
	public char Peek(int i)
	{
		if(isDone())
			throw new IndexOutOfBoundsException("Cannot peek any further into the string");
		return content.charAt(index + i);
	}
	
	public String PeekString(int i)
	{
		if(isDone())
			throw new IndexOutOfBoundsException("Cannot peek any further into the string");
		
		String next = "";
		for(int j = index; j <= index + i; j ++)
			next += content.charAt(j);
		
		return next;
	}
	
	public char GetChar()
	{
		if(index >= content.length())
			throw new IndexOutOfBoundsException("Cannot get character because this is the end of the string");
		return content.charAt(index++); // index++ is used to return the character, and increment
	}
	
	public void Swallow(int i)
	{
		index += i;
	}
	
	public boolean isDone()
	{
		if(index >= content.length())
			return true;
		return false;
	}
	
	public String Remainder()
	{
		String remainder ="";
		for(int i = index + 1; i < content.length(); i ++)
		{
			remainder += content.charAt(i);
		}
		return remainder;
	}
	
	
}
