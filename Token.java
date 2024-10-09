
public class Token {

	private Types type;
	private String value;
	private int lineNumber;
	private int charPosition;
	
	public Token(Types type, int lineNumber, int charPosition)
	{
		this.type = type;
		this.lineNumber = lineNumber;
		this.charPosition = charPosition;
	}
	
	public Token(Types type, int lineNumber, int charPosition, String value)
	{
		this.type = type;
		this.lineNumber = lineNumber;
		this.charPosition = charPosition;
		this.value = value;
	}
	
	public Types getType()
	{
		return type;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public String toString()
	{
		if(value == null)
			if(type == Types.ENDOFLINE)
				return "EndOfLine";
			else
				return "" + type;
		return type + "(" + value + ")";
	}
	
	public enum Types
	{
		//Math operations
		MATH, ADD, SUBTRACT, MULTIPLY, 
		//Bitwise operations
		AND, OR, NOT, XOR,
		//Keywords
		COPY, HALT, BRANCH, JUMP, CALL, PUSH, LOAD, RETURN, STORE,
		//Stack
		PEEK, POP, INTERRUPT, 
		//Boolean operations
		EQUAL, UNEQUAL, GREATER, LESS, GREATEROREQUAL, LESSOREQUAL, 
		//Movement bitwise operations
		SHIFT, LEFT, RIGHT, 
		
		ENDOFLINE, 
		
		REGISTER, NUMBER,
	}
	
}
