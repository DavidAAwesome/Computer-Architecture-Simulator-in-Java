import java.util.HashMap;
import java.util.LinkedList;

public class Lexer {

	private FileHandler fileHandler;
	private int lineNumber;
	private int charPosition;
	private HashMap<String, Token.Types> keyWords = new HashMap<>();
	private char current;
	
	public Lexer(String filename)
	{
		fileHandler = new FileHandler(filename);
		lineNumber = 1;
		charPosition = 0;
		CreateKeyWords();
	}
	
	public Lexer(String code, boolean test)
	{
		fileHandler = new FileHandler(code,test);
		lineNumber = 1;
		charPosition = 0;
		CreateKeyWords();
	}

	private void CreateKeyWords() 
	{
		keyWords.put("MATH",Token.Types.MATH);
		keyWords.put("ADD",Token.Types.ADD);
		keyWords.put("SUBTRACT",Token.Types.SUBTRACT);
		keyWords.put("MULTIPLY", Token.Types.MULTIPLY);
		
		keyWords.put("AND",Token.Types.AND);
		keyWords.put("OR",Token.Types.OR);
		keyWords.put("NOT",Token.Types.NOT);
		keyWords.put("XOR",Token.Types.XOR);
		
		keyWords.put("COPY",Token.Types.COPY);
		keyWords.put("HALT",Token.Types.HALT);
		keyWords.put("BRANCH",Token.Types.BRANCH);
		keyWords.put("JUMP",Token.Types.JUMP);
		keyWords.put("CALL",Token.Types.CALL);
		keyWords.put("PUSH",Token.Types.PUSH);
		keyWords.put("LOAD",Token.Types.LOAD);
		keyWords.put("RETURN",Token.Types.RETURN);
		keyWords.put("STORE",Token.Types.STORE);
		
		keyWords.put("PEEK",Token.Types.PEEK);
		keyWords.put("POP",Token.Types.POP);
		keyWords.put("INTERRUPT",Token.Types.INTERRUPT);
		
		keyWords.put("EQUAL",Token.Types.EQUAL);
		keyWords.put("UNEQUAL",Token.Types.UNEQUAL);
		keyWords.put("GREATER",Token.Types.GREATER);
		keyWords.put("LESS",Token.Types.LESS);
		keyWords.put("GREATEROREQUAL",Token.Types.GREATEROREQUAL);
		keyWords.put("LESSOREQUAL",Token.Types.LESSOREQUAL);
		
		keyWords.put("SHIFT",Token.Types.SHIFT);
		keyWords.put("LEFT",Token.Types.LEFT);
		keyWords.put("RIGHT",Token.Types.RIGHT);
	}
	
	public int getLineNumber()
	{
		return lineNumber;
	}
	
	public LinkedList<Token> lex()
	{
		LinkedList<Token> tokens = new LinkedList<>();
		
		while(!fileHandler.isDone())
		{
			current = fileHandler.GetChar();
			charPosition++;
			switch(current)
			{
			case(' '),('\t'),('\r'):
				break;
			case('\n'):
				tokens.add(new Token(Token.Types.ENDOFLINE, lineNumber, charPosition-1));
				lineNumber++;
				charPosition = 0;
				break;
			case('-'):
				tokens.add(ProcessDigit());
				break;
			case('/'):
				while(current != '\n')
					current = fileHandler.GetChar();
				tokens.add(new Token(Token.Types.ENDOFLINE, lineNumber, charPosition-1));
				lineNumber++;
				charPosition = 0;
				break;
			default:
				if(Character.isLetter(current))
					tokens.add(ProcessWord());
				else if(Character.isDigit(current))
					tokens.add(ProcessDigit());
				else
					throw new IllegalArgumentException();
			}
		}
		tokens.add(new Token(Token.Types.ENDOFLINE, lineNumber, charPosition-1));
		return tokens;
	}

	private Token ProcessWord() 
	{
		String word = "";
		do
		{
			word += current;
			if(fileHandler.isDone())// Checking to see if done before getting character to prevent errors.
				break;
			current = fileHandler.GetChar();
			charPosition++;
		}while(Character.isLetter(current));
		if(Character.isDigit(current))
		{
			if(!word.equals("R"))
				throw new IllegalArgumentException();
			word = "";
			do
			{
				word += current;
				if(fileHandler.isDone())// Checking to see if done before getting character to prevent errors.
					break;
				current = fileHandler.GetChar();
				charPosition++;
			}while(Character.isDigit(current));
			return new Token(Token.Types.REGISTER, lineNumber, charPosition-1, word);
		}
			
		if(keyWords.containsKey(word))
			return new Token(keyWords.get(word), lineNumber, charPosition-1);
		return null;
	}

	private Token ProcessDigit() 
	{
		String word = "";
		do
		{
			word += current;
			if(fileHandler.isDone())// Checking to see if done before getting character to prevent errors.
				break;
			current = fileHandler.GetChar();
			charPosition++;
		}while(Character.isDigit(current));
		return new Token(Token.Types.NUMBER, lineNumber, charPosition-1, word);
	}
	
}
