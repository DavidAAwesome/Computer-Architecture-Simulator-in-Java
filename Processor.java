
public class Processor {

	private Word ProgramCounter = new Word("00000000000000000000000000000000");
	private Word StackPointer = new Word("00000000000000000000010000000000");
	private Word currentInstruction = new Word();
	private Bit isHalted = new Bit(false);
	
	private ALU alu;
	private Word[] registers = new Word[32];
	
	private Word opCode;
	private int instructionType;
	private Word rd;
	private int rdnumber;
	private Bit[] function = new Bit[4];
	private Word rs1;
	private int rs1number;
	private Word rs2;
	private int rs2number;
	private Word immediate;
	
	private Word toStore;
	
	private Locations storeLocation;
	private enum Locations
	{
		HALT, UNUSED, RD, PC, MEM
	}
	
	private Word memoryLocation;
	
	public static int currentClockCycle = 0;
	
	public Word[] getRegisters()
	{
		return registers;
	}
	
	public Processor()
	{
		for(int i = 0; i < registers.length; i ++)
			registers[i] = new Word("00000000000000000000000000000000");
	}
	
	public void run()
	{
		while(!isHalted.getValue())
		{
			fetch();
			decode();
			execute();
			store();
		}
	}
	
	private void fetch() 
	{
		currentInstruction.copy(InstructionCache.read(ProgramCounter));
		ProgramCounter.increment();
	}
	
	private void decode()
	{
		opCode = mask(31, 27);
		
		if(opCode.getBit(30).getValue() && opCode.getBit(31).getValue())//2R
		{
			instructionType = 3;
			rd = mask(26, 22);
			rdnumber = getRegisterNumber(rd);
			function = createBitArray(21,18);
			rs2 = mask(17, 13);
			rs2number = getRegisterNumber(rs2);
			immediate = ImmediateMask(12,0);
		}
		else if(opCode.getBit(30).getValue() && !opCode.getBit(31).getValue())//3R
		{
			instructionType = 2;
			rd = mask(26, 22);
			rdnumber = getRegisterNumber(rd);
			function = createBitArray(21,18);
			rs2 = mask(17, 13);
			rs2number = getRegisterNumber(rs2);
			rs1 = mask(12, 8);
			rs1number = getRegisterNumber(rs1);
			immediate = ImmediateMask(7,0);
		}
		else if(!opCode.getBit(30).getValue() && opCode.getBit(31).getValue())//1R
		{
			instructionType = 1;
			rd = mask(26, 22);
			rdnumber = getRegisterNumber(rd);
			function = createBitArray(21,18);
			immediate = ImmediateMask(17,0);
		}
		else if(!opCode.getBit(30).getValue() && !opCode.getBit(31).getValue())//0R
		{
			instructionType = 0;
			immediate = ImmediateMask(26,0);
		}
	}
	
	private void execute() 
	{
		currentClockCycle += 300;
		if(!opCode.getBit(27).getValue() && !opCode.getBit(28).getValue() && !opCode.getBit(29).getValue())//Math
		{
			switch(instructionType)
			{
			case 1://1R
				toStore = immediate;
				storeLocation = Locations.RD;
				break;
			case 2://3R
				alu = new ALU(registers[rs1number],registers[rs2number]);
				alu.doOperation(function);
				toStore = alu.result;
				storeLocation = Locations.RD;
				break;
			case 3://2R
				alu = new ALU(registers[rdnumber],registers[rs2number]);
				alu.doOperation(function);
				toStore = alu.result;
				storeLocation = Locations.RD;
				break;
			default://0R
				storeLocation = Locations.HALT;
				isHalted = new Bit(true);
				InstructionCache.clear();
				break;
			}
		}
		else if(!opCode.getBit(27).getValue() && !opCode.getBit(28).getValue() && opCode.getBit(29).getValue())//Branch
		{
			switch(instructionType)
			{
			case 1://1R
				alu = new ALU(ProgramCounter,immediate);
				//adding
				alu.doOperation(new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(false)});
				toStore = alu.result;
				storeLocation = Locations.PC;
				break;
			case 2://3R
				alu = new ALU(registers[rs1number],registers[rs2number]);
				//Boolean Operation
				alu.doOperation(function);
				if(alu.result.getBit(0).getValue())//true
				{
					alu = new ALU(ProgramCounter,immediate);
					//adding
					alu.doOperation(new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(false)});
					toStore = alu.result;
				}
				else
					toStore = ProgramCounter;
				storeLocation = Locations.PC;
				break;
			case 3://2R
				alu = new ALU(registers[rs2number],registers[rdnumber]);
				//Boolean Operation
				alu.doOperation(function);
				if(alu.result.getBit(0).getValue())//true
				{
					alu = new ALU(ProgramCounter,immediate);
					//adding
					alu.doOperation(new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(false)});
					toStore = alu.result;
				}
				else
					toStore = ProgramCounter;
				storeLocation = Locations.PC;
				break;
			default://0R
				toStore = immediate;
				storeLocation = Locations.PC;
				break;
			}
		}
		else if(!opCode.getBit(27).getValue() && opCode.getBit(28).getValue() && !opCode.getBit(29).getValue())//Call
		{
			switch(instructionType)
			{
			case 1://1R
				StackPointer.decrement();
				L2Cache.write(StackPointer, ProgramCounter);
				alu = new ALU(registers[rdnumber],immediate);
				//adding
				alu.doOperation(new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(false)});
				toStore = alu.result;
				storeLocation = Locations.PC;
				break;
			case 2://3R
				alu = new ALU(registers[rs1number],registers[rs2number]);
				//Boolean Operation
				alu.doOperation(function);
				if(alu.result.getBit(0).getValue())//true
				{
					StackPointer.decrement();
					L2Cache.write(StackPointer, ProgramCounter);
					alu = new ALU(registers[rdnumber],immediate);
					//adding
					alu.doOperation(new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(false)});
					toStore = alu.result;
				}
				else
					toStore = ProgramCounter;
				storeLocation = Locations.PC;
				break;
			case 3://2R
				alu = new ALU(registers[rdnumber],registers[rs2number]);
				//Boolean Operation
				alu.doOperation(function);
				if(alu.result.getBit(0).getValue())//true
				{
					StackPointer.decrement();
					L2Cache.write(StackPointer, ProgramCounter);
					alu = new ALU(ProgramCounter,immediate);
					//adding
					alu.doOperation(new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(false)});
					toStore = alu.result;
				}
				else
					toStore = ProgramCounter;
				storeLocation = Locations.PC;
				break;
			default://0R
				StackPointer.decrement();
				L2Cache.write(StackPointer, ProgramCounter);
				toStore = immediate;
				storeLocation = Locations.PC;
				break;
			}
		}
		else if(!opCode.getBit(27).getValue() && opCode.getBit(28).getValue() && opCode.getBit(29).getValue())//Push
		{
			switch(instructionType)
			{
			case 1://1R
				alu = new ALU(registers[rdnumber],immediate);
				alu.doOperation(function);
				toStore = alu.result;
				StackPointer.decrement();
				memoryLocation = StackPointer;
				storeLocation = Locations.MEM;
				break;
			case 2://3R
				alu = new ALU(registers[rs1number],registers[rs2number]);
				alu.doOperation(function);
				toStore = alu.result;
				StackPointer.decrement();
				memoryLocation = StackPointer;
				storeLocation = Locations.MEM;
				break;
			case 3://2R
				alu = new ALU(registers[rdnumber],registers[rs2number]);
				alu.doOperation(function);
				toStore = alu.result;
				StackPointer.decrement();
				memoryLocation = StackPointer;
				storeLocation = Locations.MEM;
				break;
			default://0R
				storeLocation = Locations.UNUSED;
				break;
			}
		}
		else if(opCode.getBit(27).getValue() && !opCode.getBit(28).getValue() && !opCode.getBit(29).getValue())//Load
		{
			switch(instructionType)
			{
			case 1://1R
				alu = new ALU(registers[rdnumber],immediate);
				//adding
				alu.doOperation(new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(false)});
				toStore = L2Cache.read(alu.result);
				storeLocation = Locations.RD;
				break;
			case 2://3R
				alu = new ALU(registers[rs1number],registers[rs2number]);
				//adding
				alu.doOperation(new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(false)});
				toStore = L2Cache.read(alu.result);
				storeLocation = Locations.RD;
				break;
			case 3://2R
				alu = new ALU(registers[rs2number],immediate);
				//adding
				alu.doOperation(new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(false)});
				toStore = L2Cache.read(alu.result);
				storeLocation = Locations.RD;
				break;
			default://0R
				toStore = L2Cache.read(StackPointer);
				StackPointer.increment();
				storeLocation = Locations.PC;
				break;
			}
		}
		else if(opCode.getBit(27).getValue() && !opCode.getBit(28).getValue() && opCode.getBit(29).getValue())//Store
		{
			switch(instructionType)
			{
			case 1://1R
				toStore = immediate;
				memoryLocation = registers[rdnumber];
				storeLocation = Locations.MEM;
				break;
			case 2://3R
				toStore = registers[rs2number];
				alu = new ALU(registers[rdnumber],registers[rs1number]);
				//adding
				alu.doOperation(new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(false)});
				memoryLocation = alu.result;
				storeLocation = Locations.MEM;
				break;
			case 3://2R
				toStore = registers[rs2number];
				alu = new ALU(registers[rdnumber],immediate);
				//adding
				alu.doOperation(new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(false)});
				memoryLocation = alu.result;
				storeLocation = Locations.MEM;
				break;
			default://0R
				storeLocation = Locations.UNUSED;
				break;
			}
		}
		else//Pop/Interrupt
		{
			switch(instructionType)
			{
			case 1://1R
				toStore = L2Cache.read(StackPointer);
				StackPointer.increment();
				storeLocation = Locations.RD;
				break;
			case 2://3R
				alu = new ALU(registers[rs1number],registers[rs2number]);
				//adding
				alu.doOperation(new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(false)});
				alu = new ALU(StackPointer,alu.result);
				//adding
				alu.doOperation(new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(false)});
				toStore = L2Cache.read(alu.result);
				storeLocation = Locations.RD;
				break;
			case 3://2R
				alu = new ALU(registers[rs2number],immediate);
				//adding
				alu.doOperation(new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(false)});
				alu = new ALU(StackPointer,alu.result);
				//adding
				alu.doOperation(new Bit[] {new Bit(true),new Bit(true),new Bit(true),new Bit(false)});
				toStore = L2Cache.read(alu.result);
				storeLocation = Locations.RD;
				break;
			default://0R
				storeLocation = Locations.UNUSED;
				break;
			}
		}
	}
	
	private void store()
	{
		switch(storeLocation)
		{
		case RD:
			if(rdnumber != 0)//Doesn't change the value of R0
			{
				registers[rdnumber].copy(toStore);
				System.out.println("Register " + rdnumber + " stored "+  toStore.getSigned());
			}
			else
				System.out.println("Can't change register 0");
			break;
		case PC:
			ProgramCounter.copy(toStore);
			System.out.println("ProgramCounter stored " + toStore.getSigned());
			break;
		case MEM:
			L2Cache.write(memoryLocation, toStore);
			System.out.println("MainMemory[" + memoryLocation.getSigned() + "] stored " + toStore.getSigned());
			break;
		case HALT:
			System.out.println("Clock Cycle " + currentClockCycle);
			System.out.println("Halted!");
			break;
		case UNUSED:
			System.out.println("Unused!");
			break;
		}
		
			
	}
	
	private Word mask(int start, int end)
	{
		Word mask = new Word("00000000000000000000000000000000");
		for(int i = start; i >= end; i --)
			mask.setBit(i, new Bit(true));
		return currentInstruction.and(mask).rightShift(31 - start);
	}
	
	private Word ImmediateMask(int start, int end)//Mask method for immediate because it can be negative
	{
		Word mask = new Word("00000000000000000000000000000000");
		for(int i = start; i >= end; i --)
			mask.setBit(i, new Bit(true));
		if(currentInstruction.getBit(end).getValue())
		{
			Word masked = currentInstruction.and(mask).rightShift(31 - start);
			for(int i = 31 - start - 1; i >= 0; i --)
				masked.setBit(i, new Bit(true));
			return masked;
		}
		return currentInstruction.and(mask).rightShift(31 - start);
	}
	
	private Bit[] createBitArray(int start, int end)
	{
		int length = start - end + 1;
		Bit[] bits = new Bit[length];
		for(int i = length - 1; i >= 0; i --)
			bits[i] = currentInstruction.getBit(start - (length - i - 1));
		
		return bits;
	}
	
	private int getRegisterNumber(Word word)//Useful for finding the index of the array of the register
	{
		int index = 0;
		int power = 0;
		
		for(int i = 31; i >= 27; i --)
		{
			if(word.getBit(i).getValue())
				index += Math.pow(2,power);
			power++;
		}
		return index;
		
	}
	
	
}
