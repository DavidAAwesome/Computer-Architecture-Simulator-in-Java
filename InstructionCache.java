
public class InstructionCache {

	public static Word instructionCacheFirstWordAddress = new Word();
	private static Word[] instructions = new Word[8];
	
	public static Word read(Word address)
	{
		if(address.getSigned() != 0)
		{
			int addressDifference = address.getSigned() - instructionCacheFirstWordAddress.getSigned();
			if( addressDifference < 8 && addressDifference >= 0)
			{
				Processor.currentClockCycle += 10;
				return instructions[addressDifference];
			}
		}
		
		Word[] newInstructions = L2Cache.fillInstructionCache(address);
		
		for(int i = 0; i < 8; i++)
		{
			instructions[i] = newInstructions[i];
		}
		return instructions[address.getSigned() - instructionCacheFirstWordAddress.getSigned()];
	}
	
	public static void clear()//Used for clearing the cache after a program is done to not cause issues
	{
		instructionCacheFirstWordAddress = new Word();
		instructions = new Word[8];
		L2Cache.clear();
	}
	
	
	
	
}
