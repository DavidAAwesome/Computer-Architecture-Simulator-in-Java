
public class Word {

	private Bit[] bits = new Bit[32];
	
	public Word()
	{
		
	}
	
	public Word(String word)
	{
		copyString(word);
	}
	
	public void copyString(String word)
	{
		if(word.length() != 32)
			throw new IllegalArgumentException("Word passed in must be 32 characters long.");
		for(int i = 31; i >= 0; i --)
		{
			
			switch(word.charAt(i))
			{
			case('0'):
				bits[i] = new Bit(false);
				break;
			case('1'):
				bits[i] = new Bit(true);
				break;
			default:
				throw new IllegalArgumentException("Only accepts 0s and 1s in String.");
			}
		}
	}
	
	public Bit getBit(int i)
	{
		return new Bit(bits[i].getValue());
	}
	
	public void setBit(int i, Bit value)
	{
		bits[i] = new Bit(value.getValue());
	}
	
	public Word and(Word other)
	{
		Word andOutput = new Word();
		for(int i = bits.length - 1; i >= 0; i --)
			andOutput.bits[i] = bits[i].and(other.bits[i]);
		return andOutput;
	}
	
	public Word or(Word other)
	{
		Word orOutput = new Word();
		for(int i = bits.length - 1; i >= 0; i --)
			orOutput.bits[i] = bits[i].or(other.bits[i]);
		return orOutput;
	}
	
	public Word xor(Word other)
	{
		Word xorOutput = new Word();
		for(int i = bits.length - 1; i >= 0; i --)
			xorOutput.bits[i] = bits[i].xor(other.bits[i]);
		return xorOutput;
	}
	
	public Word not()
	{
		Word notOutput = new Word();
		for(int i = bits.length - 1; i >= 0; i --)
			notOutput.bits[i] = bits[i].not();
		return notOutput;
	}
	
	public Word rightShift(int amount)
	{
		Word shiftedWord = new Word(); 
		shiftedWord.copy(this); // This word is copied so that only the new Word is changed
		for(int i = bits.length - 1; i >= 0; i --)
		{
			if(bits[i].getValue()) // This if statement looks for the true bits to shift
			{
				if(i + amount < bits.length) 
				{
					shiftedWord.bits[i] = new Bit(false);
					shiftedWord.bits[i + amount] = new Bit(true);
				}
				else
					shiftedWord.bits[i] = new Bit(false);
			}
		}
		return shiftedWord;
	}
	
	public Word leftShift(int amount)
	{
		Word shiftedWord = new Word();
		shiftedWord.copy(this); // This word is copied so that only the new Word is changed
		for(int i = 0; i < bits.length; i ++)
		{
			if(bits[i].getValue()) // This if statement looks for the true bits to shift
			{
				if(i - amount >= 0)
				{
					shiftedWord.bits[i] = new Bit(false);
					shiftedWord.bits[i - amount] = new Bit(true);
				}
				else
					shiftedWord.bits[i] = new Bit(false);
			}
		}
		return shiftedWord;
	}
	
	public String toString()
	{
		String stringOutput = bits[0] + "";
		for(int i = 1; i < bits.length; i ++)
			stringOutput += ", " + bits[i];
		return stringOutput;
	}
	
	public String toBitString()
	{
		String stringOutput = "";
		for(int i = 0; i < bits.length; i ++)
		{
			if(bits[i].getValue())
				stringOutput += "1";
			else
				stringOutput += "0";
		}
			
		return stringOutput;
	}
	
	public long getUnsigned()
	{
		long numberOutput = 0;
		int power = 0;
		int bit;
		if(bits[0].getValue()) // Useful for checking if the number is negative or not.
		{
			Word inverse = not();
			addTrueToEnd(inverse);
			for(int i = 31; i >= 0; i --)
			{
				if(inverse.getBit(i).getValue())
					bit = 1;
				else
					bit = 0;
				numberOutput += Math.pow(2,power) * bit;
				power++;
			}
		}
		else
			for(int i = bits.length -1; i >= 0; i --)
			{
				if(bits[i].getValue())
					bit = 1;
				else
					bit = 0;
				numberOutput += Math.pow(2,power) * bit;
				power++;
			}
		return numberOutput;
	}
	
	public void addTrueToEnd (Word word)
	{
		for(int i = 31; i >= 0; i --)
		{
			//Does not carry the true if value being replaced is false
			if(!word.getBit(i).getValue())
			{
				word.setBit(i, new Bit(true));
				return;
			}
			word.setBit(i, new Bit(false));
		}
	}
	
	public int getSigned()
	{
		int num = 0;
		int power = 0;
		int bit;
		if(bits[0].getValue()) // Useful for checking if the number is negative or not.
		{
			Word inverse = not();
			addTrueToEnd(inverse);
			for(int i = bits.length -1; i >= 0; i --)
			{
				if(inverse.getBit(i).getValue())
					bit = 1;
				else
					bit = 0;
				num += Math.pow(2,power) * bit;
				power++;
			}
			return -num;
		}
		else
		{
			for(int i = bits.length -1; i >= 0; i --)
			{
				if(bits[i].getValue())
					bit = 1;
				else
					bit = 0;
				num += Math.pow(2,power) * bit;
				power++;
			}
			return num;
		}
	}
	
	public void copy(Word other)
	{
		for(int i = 0; i < bits.length; i ++)
			bits[i] = new Bit(other.bits[i].getValue());
	}
	
	public void set(int value)
	{
		boolean negative = false;
		if(value < 0)
		{
			negative = true;
			// Makes value positive to find to make turning to bit easier
			value = Math.abs(value); 
		}
		
		for(int i = bits.length -1; i >= 0; i --)
		{
			if(value%2 == 1)
				bits[i] = new Bit(true);
			else
				bits[i] = new Bit(false);
			value /= 2;
		}
		
		if(negative)
		{
			Word inverse = not();
			addTrueToEnd(inverse);
			// Inverses word and adds true bit to end to turn the number negative
			copy(inverse);
		}
		
	}
	
	public void increment()
	{
		Bit addition = new Bit(true);
		for(int i = 31; i >= 0; i --)
		{
			if(bits[i].xor(addition).getValue())
			{
				bits[i] = new Bit(true);
				return;//when the xor is true, there is no more carry so the program ends
			}
			else
				bits[i] = new Bit(false);//if xor is false, the carry remains
		}
	}
	
	public void decrement()
	{
		Bit subtraction = new Bit(true);
		for(int i = 31; i >= 0; i --)
		{
			if(!bits[i].xor(subtraction).getValue())
			{
				bits[i] = new Bit(false);
				return;
			}
			else
				bits[i] = new Bit(true);
		}
	}
	
}
