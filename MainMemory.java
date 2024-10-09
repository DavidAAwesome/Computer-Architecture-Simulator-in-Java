
public class MainMemory {

	private static Word[] DRAM = new Word[1024];
	
	public static Word read(Word address)
	{
		int addressInt = (int)address.getUnsigned();
		if(DRAM[addressInt] == null)
			DRAM[addressInt] = new Word("00000000000000000000000000000000");//Initialized to zero so that when read, null bits aren't returned.
		
		Word reading = new Word();
		reading.copy(DRAM[addressInt]);
		return reading;
	}
	
	public static Word cacheRead(Word address)
	{
		int addressInt = (int)address.getUnsigned();
		if(DRAM[addressInt] == null)
			DRAM[addressInt] = new Word("00000000000000000000000000000000");//Initialized to zero so that when read, null bits aren't returned.
		
		return DRAM[addressInt];
	}
	
	public static void write(Word address, Word value)
	{
		int addressInt = (int)address.getUnsigned();
		if(DRAM[addressInt] == null)
			DRAM[addressInt] = new Word();
		
		DRAM[addressInt].copy(value);
	}
	
	public static void load(String[] data)
	{
		clear();
		for(int i = 0; i < data.length; i ++)
		{
			if(DRAM[i] == null)
				DRAM[i] = new Word();
			for(int j = 31; j >= 0; j --)
			{
				switch(data[i].charAt(j))
				{
				case('0'):
					DRAM[i].setBit(j, new Bit(false));
					break;
				case('1'):
					DRAM[i].setBit(j, new Bit(true));
					break;
				default:
					throw new IllegalArgumentException("Only accpets 0s and 1s in String.");
				}
			}
		}
		if(data.length < 1024)
		{
			for(int i = data.length; i < 1024; i ++)
				DRAM[i] = new Word("00000000000000000000000000000000");
			// creates new words till the end of the program to make sure that the old data is cleared.
		}
	}
	
	public static void clear()
	{
		for(int i = 0; i < DRAM.length; i ++)
		{
			DRAM[i] = null;
		}
	}
	
	
}
