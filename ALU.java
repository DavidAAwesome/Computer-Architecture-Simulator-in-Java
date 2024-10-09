
public class ALU {

	
	public Word op1;
	public Word op2;
	public Word result;
	
	public ALU(Word op1, Word op2)
	{
		this.op1 = op1;
		this.op2 = op2;
		result = new Word();
	}
	//change to public void and fix how it breaks JUnits
	public void doOperation(Bit[] operation)
	{
		Processor.currentClockCycle += 2;//Adds 2 to the currentClockCycle for the time for any operation
		if(operation[0].getValue() && !operation[1].getValue() && !operation[2].getValue() && !operation[3].getValue())//1000
		{
			//and
			result.copy(op1.and(op2));
		}
		else if(operation[0].getValue() && !operation[1].getValue() && !operation[2].getValue() && operation[3].getValue())//1001
		{
			//or
			result.copy(op1.or(op2));
		}
		else if(operation[0].getValue() && !operation[1].getValue() && operation[2].getValue() && !operation[3].getValue())//1010
		{
			//xor
			result.copy(op1.xor(op2));
		}
		else if(operation[0].getValue() && !operation[1].getValue() && operation[2].getValue() && operation[3].getValue())//1011
		{
			//not
			result.copy(op1.not());
		}
		else if(operation[0].getValue() && operation[1].getValue() && !operation[2].getValue() && !operation[3].getValue())//1100
		{
			//leftshift
			result.copy(op1);
			result.copy(result.leftShift((int)op2.getUnsigned()));
		}
		else if(operation[0].getValue() && operation[1].getValue() && !operation[2].getValue() && operation[3].getValue())//1101
		{
			//rightshift
			result.copy(op1);
			result.copy(result.rightShift((int)op2.getUnsigned()));
		}
		else if(operation[0].getValue() && operation[1].getValue() && operation[2].getValue() && !operation[3].getValue())//1110
		{
			//add
			result.copy(add2(op1,op2));
		}
		else if(operation[0].getValue() && operation[1].getValue() && operation[2].getValue() && operation[3].getValue())//1111
		{
			//subtract
			Word op2Inverse = op2.not();
			op2Inverse.addTrueToEnd(op2Inverse);
			result.copy(add2(op1,op2Inverse));
		}
		else if(!operation[0].getValue() && operation[1].getValue() && operation[2].getValue() && operation[3].getValue())//0111
		{
			//multiply
			Processor.currentClockCycle += 8;//adds 8 plus the 2 added above to add 10 clock cycles in total
			result.copy(multiply(op1,op2));
		}
		else if(!operation[0].getValue() && !operation[1].getValue() && !operation[2].getValue() && !operation[3].getValue())//0000
		{
			//equals
			Word op2Inverse = op2.not();
			op2Inverse.addTrueToEnd(op2Inverse);
			result.copy(add2(op1,op2Inverse));
			if(result.getUnsigned() == 0)
				result.setBit(0, new Bit(true));
			else
				result.setBit(0, new Bit(false));
		}
		else if(!operation[0].getValue() && !operation[1].getValue() && !operation[2].getValue() && operation[3].getValue())//0001
		{
			//not equals
			Word op2Inverse = op2.not();
			op2Inverse.addTrueToEnd(op2Inverse);
			result.copy(add2(op1,op2Inverse));
			if(result.getUnsigned() != 0)
				result.setBit(0, new Bit(true));
			else
				result.setBit(0, new Bit(false));
		}
		else if(!operation[0].getValue() && !operation[1].getValue() && operation[2].getValue() && !operation[3].getValue())//0010
		{
			//less than
			Word op2Inverse = op2.not();
			op2Inverse.addTrueToEnd(op2Inverse);
			result.copy(add2(op1,op2Inverse));
			if(result.getSigned() < 0)
				result.setBit(0, new Bit(true));
			else
				result.setBit(0, new Bit(false));
		}
		else if(!operation[0].getValue() && !operation[1].getValue() && operation[2].getValue() && operation[3].getValue())//0011
		{
			//greater than or equals
			Word op2Inverse = op2.not();
			op2Inverse.addTrueToEnd(op2Inverse);
			result.copy(add2(op1,op2Inverse));
			if(result.getSigned() >= 0)
				result.setBit(0, new Bit(true));
			else
				result.setBit(0, new Bit(false));
		}
		else if(!operation[0].getValue() && operation[1].getValue() && !operation[2].getValue() && !operation[3].getValue())//0100
		{
			//greater than
			Word op2Inverse = op2.not();
			op2Inverse.addTrueToEnd(op2Inverse);
			result.copy(add2(op1,op2Inverse));
			if(result.getSigned() > 0)
				result.setBit(0, new Bit(true));
			else
				result.setBit(0, new Bit(false));
		}
		else if(!operation[0].getValue() && operation[1].getValue() && !operation[2].getValue() && operation[3].getValue())//0101
		{
			//less than or equals
			Word op2Inverse = op2.not();
			op2Inverse.addTrueToEnd(op2Inverse);
			result.copy(add2(op1,op2Inverse));
			if(result.getSigned() <= 0)
				result.setBit(0, new Bit(true));
			else
				result.setBit(0, new Bit(false));
		}
		else
			throw new IllegalArgumentException();
	}
	
	private Word add2(Word first, Word second)
	{
		Word sum = new Word();
		Bit carry = new Bit(false);
		
		for(int i = 31; i >= 0; i --)
		{
			switch(i)
			{
			case(31)://Two different cases are needed for when there is no carry created yet
				sum.setBit(i, first.getBit(i).xor(second.getBit(i)));
				carry = first.getBit(i).and(second.getBit(i));
				break;
			default:
				sum.setBit(i, carry.xor(first.getBit(i).xor(second.getBit(i))));//S = X XOR Y XOR Cin
				carry = (first.getBit(i).and(second.getBit(i))).or(carry.and(first.getBit(i).xor(second.getBit(i))));
				break;//Cout = X AND Y OR (( X XOR Y) AND Cin)
			}
		}
		return sum;
	}
	
	public Word add4(Word first, Word second, Word third, Word forth)
	{
		Word sum = new Word();
		Word carry = new Word();
		Word newCarry = new Word();
		int num = 0;
		for(int i = 31; i >= 0; i --)
		{
			switch(i)
			{
			case(31)://Two different cases are needed for when there is no carry or newcarry created yet
				sum.setBit(i, (first.getBit(i).xor(second.getBit(i))).xor(third.getBit(i)).xor(forth.getBit(i)));
				if(first.getBit(i).getValue())
					num++;
				if(second.getBit(i).getValue())
					num++;
				if(third.getBit(i).getValue())
					num++;
				if(forth.getBit(i).getValue())
					num++;
				carry.set(num/2);
				carry = carry.leftShift(1);
				num = 0;
				break;
			default://S = X XOR Y XOR Cin
				sum.setBit(i, carry.getBit(i).xor(first.getBit(i).xor(second.getBit(i))).xor(third.getBit(i)).xor(forth.getBit(i)));
				if(first.getBit(i).getValue())
					num++;
				if(second.getBit(i).getValue())
					num++;
				if(third.getBit(i).getValue())
					num++;
				if(forth.getBit(i).getValue())
					num++;
				if(carry.getBit(i).getValue())
					num++;
				newCarry.set(num/2);
				newCarry = newCarry.leftShift(32-i);//(number of ones)/2 to find the new carry and leftshift to the correct position
				num = 0;
				
				if(i-2>=0)//Created for finding the correct value at i-2
					carry.setBit(i-2, new Bit((carry.getBit(i-1).and(newCarry.getBit(i-1)).or(newCarry.getBit(i-2)).getValue())));
				if(i-1>=0)//Created for finding the correct value at i-1
					carry.setBit(i-1, new Bit((carry.getBit(i-1).xor(newCarry.getBit(i-1))).getValue()));
				
				break;
			}
		}
		return sum;
	}
	
	public Word multiply(Word first, Word second)
	{
		Word[] words = new Word[32];
		for(int i = 31; i >= 0; i --)
		{
			if(second.getBit(i).getValue())//if statement is used to copy bit if it is true
			{
				words[i] = new Word();
				words[i].copy(first);
				words[i] = words[i].leftShift(31-i);
			}
			else
			{//makes a 0 word if the current bit in the second word is false
				words[i] = new Word();
				for(int j = 0; j < 32; j ++)
					words[i].setBit(j, new Bit(false));
			}
		}
		
		for(int i = 0; i < 32; i += 4)//incremented by 4 to place the result of addition once every 4 words
			words[i] = add4(words[i],words[i+1],words[i+2],words[i+3]);
		for(int i = 0; i < 32; i += 16)//incremented by 16 to add the words created previously and places the result once every 16 words
			words[i] = add4(words[i],words[i+4],words[i+8],words[i+12]);
		words[0] = add2(words[0],words[16]);//this is the final addition necessary
		
		return words[0];
	}
	
}
