import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Optional;

public class Parser {
	
	public static void main(String[] args) throws IOException
	{
		Lexer lexer = new Lexer("src/testFile.txt");
		LinkedList<Token> tokens = lexer.lex();
		for(var token : tokens)
			System.out.print(token + " ");
		
		System.out.println();
		
		Lexer lexer2 = new Lexer("src/20IntegersArraySum.txt");
		Parser parser = new Parser(lexer2);
		String[] strings = parser.parse();
		PrintWriter printWriter = new PrintWriter(new FileWriter("src/outputFile.txt"));
		for(var string : strings)
		{
			System.out.println(string);
			printWriter.println(string);
		}
		printWriter.close();
		
		MainMemory.load(strings);
		Processor processor = new Processor();
		processor.run();
	}

	private LinkedList<Token> tokens;
	private Lexer lexer;
	private int registerNumber;
	
	public Parser(Lexer lexer)
	{
		this.lexer = lexer;
		tokens = lexer.lex();
	}
	
	public String[] parse() throws FileNotFoundException
	{
		String[] code = new String[lexer.getLineNumber()];
		for(int i = 0; i < code.length; i ++)
		{
			if(tokens.isEmpty())
			{
				code[i] = "00000000000000000000000000000000";
				break;
			}
			code[i] = parseStatement();
			if(!AcceptSeperators())
				throw new IllegalArgumentException();
		}
		
		return code;
			
	}
	
	private String parseStatement()
	{
		updateRegisterNumber();
		String instructionType = "00";
		switch(registerNumber)
		{
		case 0: 
			instructionType = "00";
			break;
		case 1:
			instructionType = "01";
			break;
		case 2:
			instructionType = "11";
			break;
		case 3:
			instructionType = "10";
		}
		String instruction = "000";
		
		
		var halt = MatchAndRemove(Token.Types.HALT);
		if(halt.isPresent())
			instruction = "000";
		
		var copy = MatchAndRemove(Token.Types.COPY);
		if(copy.isPresent())
			instruction = "000";
		
		var math = MatchAndRemove(Token.Types.MATH);
		if(math.isPresent())
			instruction = "000";
		
		var jump = MatchAndRemove(Token.Types.JUMP);
		if(jump.isPresent())
			instruction = "001";
		
		var branch = MatchAndRemove(Token.Types.BRANCH);
		if(branch.isPresent())
			instruction = "001";
		
		var call = MatchAndRemove(Token.Types.CALL);
		if(call.isPresent())
			instruction = "010";
		
		var push = MatchAndRemove(Token.Types.PUSH);
		if(push.isPresent())
			instruction = "011";
		
		var returnKeyWord = MatchAndRemove(Token.Types.RETURN);
		if(returnKeyWord.isPresent())
			instruction = "100";
		
		var load = MatchAndRemove(Token.Types.LOAD);
		if(load.isPresent())
			instruction = "100";
		
		var store = MatchAndRemove(Token.Types.STORE);
		if(store.isPresent())
			instruction = "101";
		
		var pop = MatchAndRemove(Token.Types.POP);
		if(pop.isPresent())
			instruction = "110";
		
		var peek = MatchAndRemove(Token.Types.PEEK);
		if(peek.isPresent())
			instruction = "110";
		
		return parseRegisters() + instruction + instructionType;
	}
	
	private boolean AcceptSeperators()
	{
		int seperators = 0;
		while(!tokens.isEmpty())
		{
			if(MatchAndRemove(Token.Types.ENDOFLINE).isPresent())
				seperators++;
			else
				break;
		}
		if(seperators > 0)
			return true;
		return false;
	}
	
	private void updateRegisterNumber()
	{
		int index = 0;
		registerNumber = 0;
		while(!tokens.isEmpty())
		{
			if(tokens.get(index).getType() == Token.Types.ENDOFLINE)
				break;
			if(tokens.get(index).getType() == Token.Types.REGISTER)
				registerNumber++;
			index++;
		}
	}
	
	private String parseRegisters()
	{
		String function;
		Optional<Token> rd;
		Optional<Token> rs2;
		Optional<Token> rs1;
		String rdString;
		String rs2String;
		String rs1String;
		Word temp;
		switch(registerNumber)
		{
		case(0):
			return parseImmediate();
		case(1):
			function = parseFunction();
		
			rd = MatchAndRemove(Token.Types.REGISTER);
			if(rd.isEmpty())
				throw new IllegalArgumentException();
			temp = new Word();
			temp.set(Integer.parseInt(rd.get().getValue()));
			rdString = WordtoString(temp, 27);
			return parseImmediate() + function + rdString;
		case(2):
			function = parseFunction();
			
			rs2 = MatchAndRemove(Token.Types.REGISTER);
			if(rs2.isEmpty())
				throw new IllegalArgumentException();
			temp = new Word();
			temp.set(Integer.parseInt(rs2.get().getValue()));
			rs2String = WordtoString(temp, 27);
			
			rd = MatchAndRemove(Token.Types.REGISTER);
			if(rd.isEmpty())
				throw new IllegalArgumentException();
			temp = new Word();
			temp.set(Integer.parseInt(rd.get().getValue()));
			rdString = WordtoString(temp, 27);
			
			return parseImmediate() + rs2String + function + rdString;
		case(3):
			function = parseFunction();
			
			rs1 = MatchAndRemove(Token.Types.REGISTER);
			if(rs1.isEmpty())
				throw new IllegalArgumentException();
			temp = new Word();
			temp.set(Integer.parseInt(rs1.get().getValue()));
			rs1String = WordtoString(temp, 27);
			
			rs2 = MatchAndRemove(Token.Types.REGISTER);
			if(rs2.isEmpty())
				throw new IllegalArgumentException();
			temp = new Word();
			temp.set(Integer.parseInt(rs2.get().getValue()));
			rs2String = WordtoString(temp, 27);
			
			rd = MatchAndRemove(Token.Types.REGISTER);
			if(rd.isEmpty())
				throw new IllegalArgumentException();
			temp = new Word();
			temp.set(Integer.parseInt(rd.get().getValue()));
			rdString = WordtoString(temp, 27);
		
			return parseImmediate() + rs1String + rs2String + function + rdString;
		}
		return null;
		
	}
	
	private String parseFunction()
	{
		var and = MatchAndRemove(Token.Types.AND);
		if(and.isPresent())
			return "1000";
		
		var or = MatchAndRemove(Token.Types.OR);
		if(or.isPresent())
			return "1001";
		
		var xor = MatchAndRemove(Token.Types.XOR);
		if(xor.isPresent())
			return "1010";
		
		var not = MatchAndRemove(Token.Types.NOT);
		if(not.isPresent())
			return "1011";
		
		var left = MatchAndRemove(Token.Types.LEFT);
		if(left.isPresent())
			return "1100";
		
		var right = MatchAndRemove(Token.Types.RIGHT);
		if(right.isPresent())
			return "1101";
		
		var add = MatchAndRemove(Token.Types.ADD);
		if(add.isPresent())
			return "1110";
		
		var subtract = MatchAndRemove(Token.Types.SUBTRACT);
		if(subtract.isPresent())
			return "1111";
		
		var multiply = MatchAndRemove(Token.Types.MULTIPLY);
		if(multiply.isPresent())
			return "0111";
		
		var equals = MatchAndRemove(Token.Types.EQUAL);
		if(equals.isPresent())
			return "0000";
		
		var notequals = MatchAndRemove(Token.Types.UNEQUAL);
		if(notequals.isPresent())
			return "0001";
		
		var lessthan = MatchAndRemove(Token.Types.LESS);
		if(lessthan.isPresent())
			return "0010";
		
		var greaterOrEqual = MatchAndRemove(Token.Types.GREATEROREQUAL);
		if(greaterOrEqual.isPresent())
			return "0011";
		
		var greater = MatchAndRemove(Token.Types.GREATER);
		if(greater.isPresent())
			return "0100";
		
		var lessOrEqual = MatchAndRemove(Token.Types.LESSOREQUAL);
		if(lessOrEqual.isPresent())
			return "0101";
		
		return "0000";
	}
	
	private String WordtoString(Word word, int endPoint)
	{
		String stringOutput = "";
		for(int i = 31; i >= endPoint; i --)
		{
			if(word.getBit(i).getValue())
				stringOutput = "1" + stringOutput;
			else
				stringOutput = "0" + stringOutput;
		}
		return stringOutput;
	}
	
	private String parseImmediate()
	{
		
		int index = 0;
		String value = "0";
		var immediate = MatchAndRemove(Token.Types.NUMBER);
		if(immediate.isPresent())
			value = immediate.get().getValue();
		
		Word word = new Word();
		word.set(Integer.parseInt(value));
		
		switch(registerNumber)
		{
		case(0):
			return WordtoString(word,5);
		case(1):
			return WordtoString(word,14);
		case(2):
			return WordtoString(word,19);
		case(3):
			return WordtoString(word,24);
		}
		
		return null;
		
	}
	
	
	private Optional<Token> MatchAndRemove(Token.Types keyWord)
	{
		if(tokens.isEmpty())
			return Optional.empty();
		if(keyWord.equals(tokens.getFirst().getType()))
			return Optional.of(tokens.remove());
		return Optional.empty();
	}
	
	
}
