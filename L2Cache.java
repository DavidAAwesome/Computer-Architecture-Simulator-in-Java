
public class L2Cache {

	private static Word L2Cache1FirstAddress;
	private static Word[] L2Cache1 = new Word[8];
	
	private static Word L2Cache2FirstAddress;
	private static Word[] L2Cache2 = new Word[8];
	
	private static Word L2Cache3FirstAddress;
	private static Word[] L2Cache3 = new Word[8];
	
	private static Word L2Cache4FirstAddress;
	private static Word[] L2Cache4 = new Word[8];
	
	private static int cache1AddressDifference = 0;
	private static int cache2AddressDifference = 0;
	private static int cache3AddressDifference = 0;
	private static int cache4AddressDifference = 0;
	
	public static Word[] fillInstructionCache(Word address)
	{
		switch(findAddressArray(address))
		{
		case 1:
			InstructionCache.instructionCacheFirstWordAddress.copy(L2Cache1FirstAddress);
			Processor.currentClockCycle += 50; //Added for filling instruction cache
			return L2Cache1;
		case 2:
			InstructionCache.instructionCacheFirstWordAddress.copy(L2Cache2FirstAddress);
			Processor.currentClockCycle += 50; //Added for filling instruction cache
			return L2Cache2;
		case 3:
			InstructionCache.instructionCacheFirstWordAddress.copy(L2Cache3FirstAddress);
			Processor.currentClockCycle += 50; //Added for filling instruction cache
			return L2Cache3;
		case 4:
			InstructionCache.instructionCacheFirstWordAddress.copy(L2Cache4FirstAddress);
			Processor.currentClockCycle += 50; //Added for filling instruction cache
			return L2Cache4;
		default:
			Word current = changeFarthest(address);
			InstructionCache.instructionCacheFirstWordAddress.copy(current);
			Processor.currentClockCycle += 50; //Added for filling instruction cache
			if(current.getSigned() == L2Cache1FirstAddress.getSigned())
			{
				return L2Cache1;
			}
			if(current.getSigned() == L2Cache2FirstAddress.getSigned())
			{
				return L2Cache2;
			}
			if(current.getSigned() == L2Cache3FirstAddress.getSigned())
			{
				return L2Cache3;
			}
			if(current.getSigned() == L2Cache4FirstAddress.getSigned())
			{
				return L2Cache4;
			}
			throw new IllegalArgumentException();
		}
	}
	
	public static int findAddressArray(Word address)
	{
		if(L2Cache1FirstAddress == null)
		{
			Word position = new Word();
			position.copy(address);
			Processor.currentClockCycle += 350; //Added for filling one of the arrays
			L2Cache1FirstAddress = new Word();
			L2Cache1FirstAddress.copy(position);
			for(int i = 0; i < 8; i ++)
			{
				if(position.getSigned() >= 1024 || position.getSigned() < 0)
					break;
				L2Cache1[i] = MainMemory.cacheRead(position);
				position.increment();
			}
			return 1;
		}
		cache1AddressDifference = address.getSigned() - L2Cache1FirstAddress.getSigned();
		if( cache1AddressDifference < 8 && cache1AddressDifference >= 0)//Checks if the address is in this cache's position
		{
			return 1;
		}
		
		if(L2Cache2FirstAddress == null)
		{
			Word position = new Word();
			position.copy(address);
			Processor.currentClockCycle += 350; //Added for filling one of the arrays
			L2Cache2FirstAddress = new Word();
			L2Cache2FirstAddress.copy(position);
			for(int i = 0; i < 8; i ++)
			{
				if(position.getSigned() >= 1024 || position.getSigned() < 0)
					break;
				L2Cache2[i] = MainMemory.cacheRead(position);
				position.increment();
			}
			return 2;
		}
		cache2AddressDifference = address.getSigned() - L2Cache2FirstAddress.getSigned();
		if( cache2AddressDifference < 8 && cache2AddressDifference >= 0)//Checks if the address is in this cache's position
		{
			return 2;
		}
		
		if(L2Cache3FirstAddress == null)
		{
			Word position = new Word();
			position.copy(address);
			Processor.currentClockCycle += 350; //Added for filling one of the arrays
			L2Cache3FirstAddress = new Word();
			L2Cache3FirstAddress.copy(position);
			for(int i = 0; i < 8; i ++)
			{
				if(position.getSigned() >= 1024 || position.getSigned() < 0)
					break;
				L2Cache3[i] = MainMemory.cacheRead(position);
				position.increment();
			}
			return 3;
		}
		cache3AddressDifference = address.getSigned() - L2Cache3FirstAddress.getSigned();
		if( cache3AddressDifference < 8 && cache3AddressDifference >= 0)//Checks if the address is in this cache's position
		{
			return 3;
		}
		
		if(L2Cache4FirstAddress == null)
		{
			Word position = new Word();
			position.copy(address);
			Processor.currentClockCycle += 350; //Added for filling one of the arrays
			L2Cache4FirstAddress = new Word();
			L2Cache4FirstAddress.copy(position);
			for(int i = 0; i < 8; i ++)
			{
				if(position.getSigned() >= 1024 || position.getSigned() < 0)
					break;
				L2Cache4[i] = MainMemory.cacheRead(position);
				position.increment();
			}
			return 4;
		}
		cache4AddressDifference = address.getSigned() - L2Cache4FirstAddress.getSigned();
		if( cache4AddressDifference < 8 && cache4AddressDifference >= 0)//Checks if the address is in this cache's position
		{
			return 4;
		}
		
		return 0; // if it is none of the arrays
		
		
	}
	
	public static Word read(Word address)
	{
		switch(findAddressArray(address))
		{
		case 1:
			Processor.currentClockCycle += 50; //Added for read cost
			return L2Cache1[address.getSigned() - L2Cache1FirstAddress.getSigned()];
		case 2:
			Processor.currentClockCycle += 50; //Added for read cost
			return L2Cache2[address.getSigned() - L2Cache2FirstAddress.getSigned()];
		case 3:
			Processor.currentClockCycle += 50; //Added for read cost
			return L2Cache3[address.getSigned() - L2Cache3FirstAddress.getSigned()];
		case 4:
			Processor.currentClockCycle += 50; //Added for read cost
			return L2Cache4[address.getSigned() - L2Cache4FirstAddress.getSigned()];
		default:
			Processor.currentClockCycle += 50; //Added for read cost
			Word current = changeFarthest(address);
			if(current.getSigned() == L2Cache1FirstAddress.getSigned())
			{
				return L2Cache1[0];
			}
			if(current.getSigned() == L2Cache2FirstAddress.getSigned())
			{
				return L2Cache2[0];
			}
			if(current.getSigned() == L2Cache3FirstAddress.getSigned())
			{
				return L2Cache3[0];
			}
			if(current.getSigned() == L2Cache4FirstAddress.getSigned())
			{
				return L2Cache4[0];
			}
			throw new IllegalArgumentException();
		}
	}
	
	public static void write(Word address, Word value)
	{
		switch(findAddressArray(address))
		{
		case 1:
			Processor.currentClockCycle += 50; //Added for write cost
			MainMemory.write(address,value);
			break;
		case 2:
			Processor.currentClockCycle += 50; //Added for write cost
			MainMemory.write(address,value);
			break;
		case 3:
			Processor.currentClockCycle += 50; //Added for write cost
			MainMemory.write(address,value);
			break;
		case 4:
			Processor.currentClockCycle += 50; //Added for write cost
			MainMemory.write(address,value);
			break;
		default:
			Processor.currentClockCycle += 50; //Added for write cost
			changeFarthest(address); 
			MainMemory.write(address, value);
		}
	}
	
	public static void clear()
	{
		L2Cache1FirstAddress = null;
		L2Cache1 = new Word[8];
		L2Cache2FirstAddress = null;
		L2Cache2 = new Word[8];
		L2Cache3FirstAddress = null;
		L2Cache3 = new Word[8];
		L2Cache4FirstAddress = null;
		L2Cache4 = new Word[8];
	}
	
	private static Word changeFarthest(Word address)
	{
		/**
		 * Finds the maximum difference to figure out the one farthest from our current position, which therefore is less likely
		 * to be needed and replacing it with one that matches what we need
		 */
		int maxDifference = Math.max(Math.max(Math.abs(cache1AddressDifference), Math.abs(cache2AddressDifference)), Math.max(Math.abs(cache3AddressDifference),Math.abs(cache4AddressDifference)));
		
		if(maxDifference == Math.abs(cache1AddressDifference))
		{
			Word position = new Word();
			position.copy(address);
			Processor.currentClockCycle += 350; //Added for filling one of the arrays
			L2Cache1FirstAddress = new Word();
			L2Cache1FirstAddress.copy(position);
			for(int i = 0; i < 8; i ++)
			{
				if(position.getSigned() >= 1024 || position.getSigned() < 0)
					break;
				L2Cache1[i] = MainMemory.cacheRead(position);
				position.increment();
			}
			return L2Cache1FirstAddress;
		}
		
		if(maxDifference == Math.abs(cache2AddressDifference))
		{
			Word position = new Word();
			position.copy(address);
			Processor.currentClockCycle += 350; //Added for filling one of the arrays
			L2Cache2FirstAddress = new Word();
			L2Cache2FirstAddress.copy(position);
			for(int i = 0; i < 8; i ++)
			{
				if(position.getSigned() >= 1024 || position.getSigned() < 0)
					break;
				L2Cache2[i] = MainMemory.cacheRead(position);
				position.increment();
			}
			return L2Cache2FirstAddress;
		}
		
		if(maxDifference == Math.abs(cache3AddressDifference))
		{
			Word position = new Word();
			position.copy(address);
			Processor.currentClockCycle += 350; //Added for filling one of the arrays
			L2Cache3FirstAddress = new Word();
			L2Cache3FirstAddress.copy(position);
			for(int i = 0; i < 8; i ++)
			{
				if(position.getSigned() >= 1024 || position.getSigned() < 0)
					break;
				L2Cache3[i] = MainMemory.cacheRead(position);
				position.increment();
			}
			return L2Cache3FirstAddress;
		}
		
		if(maxDifference == Math.abs(cache4AddressDifference))
		{
			Word position = new Word();
			position.copy(address);
			Processor.currentClockCycle += 350; //Added for filling one of the arrays
			L2Cache4FirstAddress = new Word();
			L2Cache4FirstAddress.copy(position);
			for(int i = 0; i < 8; i ++)
			{
				if(position.getSigned() >= 1024 || position.getSigned() < 0)
					break;
				L2Cache4[i] = MainMemory.cacheRead(position);
				position.increment();
			}
			return L2Cache4FirstAddress;
		}
		
		throw new IllegalArgumentException();//Exception thrown because if gotten this far, an error must have occured.
	}
	
}
