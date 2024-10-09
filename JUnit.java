import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.LinkedList;

import org.junit.Assert;

class JUnit {

	@Test
	void BitSetValueTest() {
		Bit test = new Bit(true);
		test.set(false);
		Assert.assertEquals(false, test.getValue());
		test.set(true);
		Assert.assertEquals(true, test.getValue());
	}
	
	@Test
	void BitToggleTest() {
		Bit test = new Bit(true);
		test.toggle();
		Assert.assertEquals(false, test.getValue());
		test.toggle();
		Assert.assertEquals(true, test.getValue());
	}
	
	@Test
	void BitSetTest() {
		Bit test = new Bit(false);
		test.set();
		Assert.assertEquals(true, test.getValue());
	}
	
	@Test
	void BitClearTest() {
		Bit test = new Bit(true);
		test.clear();
		Assert.assertEquals(false, test.getValue());
	}
	
	@Test
	void BitAndTest() {
		Bit test = new Bit(false);
		Assert.assertEquals(false, test.and(new Bit(false)).getValue());//false and false
		test.set();
		Assert.assertEquals(false, test.and(new Bit(false)).getValue());//true and false
		test.clear();
		Assert.assertEquals(false, test.and(new Bit(true)).getValue());//false and true
		test.set();
		Assert.assertEquals(true, test.and(new Bit(true)).getValue());//true and true
	}
	
	@Test
	void BitOrTest() {
		Bit test = new Bit(false);
		Assert.assertEquals(false, test.or(new Bit(false)).getValue());//false and false
		test.set();
		Assert.assertEquals(true, test.or(new Bit(false)).getValue());//true and false
		test.clear();
		Assert.assertEquals(true, test.or(new Bit(true)).getValue());//false and true
		test.set();
		Assert.assertEquals(true, test.or(new Bit(true)).getValue());//true and true
	}
	
	@Test
	void BitXorTest() {
		Bit test = new Bit(false);
		Assert.assertEquals(false, test.xor(new Bit(false)).getValue());//false and false
		test.set();
		Assert.assertEquals(true, test.xor(new Bit(false)).getValue());//true and false
		test.clear();
		Assert.assertEquals(true, test.xor(new Bit(true)).getValue());//false and true
		test.set();
		Assert.assertEquals(false, test.xor(new Bit(true)).getValue());//true and true
	}
	
	@Test
	void BitNotTest() {
		Bit test = new Bit(false);
		Assert.assertEquals(true, test.not().getValue());
		test.set();
		Assert.assertEquals(false, test.not().getValue());
	}
	
	@Test
	void BitToStringTest() {
		Bit test = new Bit(false);
		Assert.assertEquals("f", test +"");
		test.set();
		Assert.assertEquals("t", test +"");
	}
	
	@Test
	void WordAndTest()
	{
		Word trueWord = new Word();
		for(int i = 0; i < 32; i ++)
			trueWord.setBit(i, new Bit(true));
		Word falseWord = new Word();
		for(int i = 0; i < 32; i ++)
			falseWord.setBit(i, new Bit(false));
		
		//false and false
		Assert.assertEquals(falseWord.and(falseWord).toString(), "f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f");
		//true and false
		Assert.assertEquals(trueWord.and(falseWord).toString(), "f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f");
		//false and true
		Assert.assertEquals(falseWord.and(trueWord).toString(), "f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f");
		//true and true
		Assert.assertEquals(trueWord.and(trueWord).toString(), "t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t");
		
		trueWord.setBit(20, new Bit(false));
		falseWord.setBit(10, new Bit(true));
		Assert.assertEquals(trueWord.and(falseWord).toString(), "f, f, f, f, f, f, f, f, f, f, t, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f");
		Assert.assertEquals(falseWord.and(trueWord).toString(), "f, f, f, f, f, f, f, f, f, f, t, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f");
		
	}
	
	@Test
	void WordOrTest()
	{
		Word trueWord = new Word();
		for(int i = 0; i < 32; i ++)
			trueWord.setBit(i, new Bit(true));
		Word falseWord = new Word();
		for(int i = 0; i < 32; i ++)
			falseWord.setBit(i, new Bit(false));
		
		//false and false
		Assert.assertEquals(falseWord.or(falseWord).toString(), "f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f");
		//true and false
		Assert.assertEquals(trueWord.or(falseWord).toString(), "t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t");
		//false and true
		Assert.assertEquals(falseWord.or(trueWord).toString(), "t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t");
		//true and true
		Assert.assertEquals(trueWord.or(trueWord).toString(), "t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t");
		
		trueWord.setBit(20, new Bit(false));
		falseWord.setBit(10, new Bit(true));
		Assert.assertEquals(trueWord.or(falseWord).toString(), "t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, f, t, t, t, t, t, t, t, t, t, t, t");
		Assert.assertEquals(falseWord.or(trueWord).toString(), "t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, f, t, t, t, t, t, t, t, t, t, t, t");
	}
	
	@Test
	void WordXorTest()
	{
		Word trueWord = new Word();
		for(int i = 0; i < 32; i ++)
			trueWord.setBit(i, new Bit(true));
		Word falseWord = new Word();
		for(int i = 0; i < 32; i ++)
			falseWord.setBit(i, new Bit(false));
		
		//false and false
		Assert.assertEquals(falseWord.xor(falseWord).toString(), "f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f");
		//true and false
		Assert.assertEquals(trueWord.xor(falseWord).toString(), "t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t");
		//false and true
		Assert.assertEquals(falseWord.xor(trueWord).toString(), "t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t");
		//true and true
		Assert.assertEquals(trueWord.xor(trueWord).toString(), "f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f");
		
		trueWord.setBit(20, new Bit(false));
		falseWord.setBit(10, new Bit(true));
		Assert.assertEquals(trueWord.xor(falseWord).toString(), "t, t, t, t, t, t, t, t, t, t, f, t, t, t, t, t, t, t, t, t, f, t, t, t, t, t, t, t, t, t, t, t");
		Assert.assertEquals(falseWord.xor(trueWord).toString(), "t, t, t, t, t, t, t, t, t, t, f, t, t, t, t, t, t, t, t, t, f, t, t, t, t, t, t, t, t, t, t, t");
	}
	
	@Test
	void WordNotTest()
	{
		Word trueWord = new Word();
		for(int i = 0; i < 32; i ++)
			trueWord.setBit(i, new Bit(true));
		Word falseWord = new Word();
		for(int i = 0; i < 32; i ++)
			falseWord.setBit(i, new Bit(false));
		
		Assert.assertEquals(falseWord.not().toString(), "t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t");
		Assert.assertEquals(trueWord.not().toString(), "f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f");
		
		trueWord.setBit(20, new Bit(false));
		falseWord.setBit(10, new Bit(true));
		Assert.assertEquals(trueWord.not().toString(), "f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, t, f, f, f, f, f, f, f, f, f, f, f");
		Assert.assertEquals(falseWord.not().toString(), "t, t, t, t, t, t, t, t, t, t, f, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t");
	}
	
	@Test
	void WordRightShiftTest()
	{
		Word word = new Word();
		for(int i = 0; i < 32; i ++)
			word.setBit(i, new Bit(false));
		word.setBit(29, new Bit(true));
		word.setBit(31, new Bit(true));
		word.setBit(0, new Bit(true));
		Assert.assertEquals(word.rightShift(1).toString(), "f, t, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, t, f");
		Assert.assertEquals(word.rightShift(5).toString(), "f, f, f, f, f, t, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f");
	}
	
	@Test
	void WordLeftShiftTest()
	{
		Word word = new Word();
		for(int i = 0; i < 32; i ++)
			word.setBit(i, new Bit(false));
		word.setBit(29, new Bit(true));
		word.setBit(31, new Bit(true));
		word.setBit(0, new Bit(true));
		Assert.assertEquals(word.leftShift(1).toString(), "f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, t, f, t, f");
		Assert.assertEquals(word.leftShift(5).toString(), "f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, t, f, t, f, f, f, f, f");
	}
	
	@Test
	void WordGetUnsignedTest()
	{
		Word number = new Word();
		for(int i = 0; i < 32; i ++) // 4
			number.setBit(i, new Bit(false));
		number.setBit(29, new Bit(true));
		
		Assert.assertEquals(number.getUnsigned(), 4);
		
		for(int i = 0; i < 32; i ++)// -4
			number.setBit(i, new Bit(true));
		number.setBit(31, new Bit(false));
		number.setBit(30, new Bit(false));
		Assert.assertEquals(number.getUnsigned(), 4);
	}
	
	@Test
	void WordGetSignedTest()
	{
		Word number = new Word();
		for(int i = 0; i < 32; i ++) //4
			number.setBit(i, new Bit(false));
		number.setBit(29, new Bit(true));
		Assert.assertEquals(number.getSigned(), 4);
		
		for(int i = 0; i < 32; i ++) //-4
			number.setBit(i, new Bit(true));
		number.setBit(30, new Bit(false));
		number.setBit(31, new Bit(false));
		Assert.assertEquals(number.getSigned(), -4);
	}
	
	@Test
	void WordCopy()
	{
		Word trueWord = new Word();
		for(int i = 0; i < 32; i ++)
			trueWord.setBit(i, new Bit(true));
		Word falseWord = new Word();
		for(int i = 0; i < 32; i ++)
			falseWord.setBit(i, new Bit(false));
		trueWord.copy(falseWord);
		Assert.assertEquals(trueWord.toString(), falseWord.toString());
	}
	
	@Test
	void WordSet()
	{
		Word number = new Word();
		for(int i = 0; i < 32; i ++)
			number.setBit(i, new Bit(false));
		number.setBit(29, new Bit(true));//4
		
		Word word = new Word();
		word.set(4);
		Assert.assertEquals(word.toString(), number.toString());
		
		for(int i = 0; i < 32; i ++)
			number.setBit(i, new Bit(true));
		number.setBit(31, new Bit(false));
		number.setBit(30, new Bit(false));
		word.set(-4);
		Assert.assertEquals(word.toString(), number.toString());
		
	}
	
	@Test
	void ALUadd2Test()
	{
		Word number1 = new Word();
		for(int i = 0; i < 32; i ++)
			number1.setBit(i, new Bit(false));
		number1.setBit(29, new Bit(true));//4
		
		Word number2 = new Word();
		for(int i = 0; i < 32; i ++)
			number2.setBit(i, new Bit(false));
		number2.setBit(29, new Bit(true));//4
		
		Bit[] addOperation = {new Bit(true),new Bit(true),new Bit(true),new Bit(false)};
		ALU alu = new ALU(number1,number2);
		alu.doOperation(addOperation);//4+4
		Word functionAnswer = alu.result;
				
		Word realAnswer = new Word();
		for(int i = 0; i < 32; i ++)
			realAnswer.setBit(i, new Bit(false));
		realAnswer.setBit(28, new Bit(true));//8
		
		Assert.assertEquals(realAnswer.toString(), functionAnswer.toString());//low numbers

		for(int i = 0; i < 32; i ++)
			number1.setBit(i, new Bit(false));
		number1.setBit(29, new Bit(true));
		number1.setBit(31, new Bit(true));//5
		
		for(int i = 0; i < 32; i ++)
			number2.setBit(i, new Bit(false));//0
		
		alu.doOperation(addOperation);//5+0
				
		for(int i = 0; i < 32; i ++)
			realAnswer.setBit(i, new Bit(false));
		realAnswer.setBit(29, new Bit(true));
		realAnswer.setBit(31, new Bit(true));//5
		
		Assert.assertEquals(realAnswer.toString(), functionAnswer.toString());//0
		
		for(int i = 0; i < 32; i ++)
			number1.setBit(i, new Bit(false));//0
		
		for(int i = 0; i < 32; i ++)
			number2.setBit(i, new Bit(false));//0
		
		alu.doOperation(addOperation);//0+0
				
		for(int i = 0; i < 32; i ++)
			realAnswer.setBit(i, new Bit(false));//0
		
		Assert.assertEquals(realAnswer.toString(), functionAnswer.toString());//0
		
		for(int i = 0; i < 32; i ++)
			number1.setBit(i, new Bit(true));
		number1.setBit(29, new Bit(false));//-5
		
		for(int i = 0; i < 32; i ++)
			number2.setBit(i, new Bit(false));
		number2.setBit(30, new Bit(true));
		number2.setBit(28, new Bit(true));//10
		
		alu.doOperation(addOperation);//-5+10
				
		for(int i = 0; i < 32; i ++)
			realAnswer.setBit(i, new Bit(false));
		realAnswer.setBit(29, new Bit(true));
		realAnswer.setBit(31, new Bit(true));//5
		
		Assert.assertEquals(realAnswer.toString(), functionAnswer.toString());// negative and positive numbers
		
		for(int i = 0; i < 32; i ++)
			number1.setBit(i, new Bit(true));
		number1.setBit(29, new Bit(false));//-5
		
		for(int i = 0; i < 32; i ++)
			number2.setBit(i, new Bit(true));
		number2.setBit(31, new Bit(false));
		number2.setBit(28, new Bit(false));//-10
		
		alu.doOperation(addOperation);//-5-10
				
		for(int i = 0; i < 32; i ++)
			realAnswer.setBit(i, new Bit(true));
		realAnswer.setBit(29, new Bit(false));
		realAnswer.setBit(30, new Bit(false));
		realAnswer.setBit(28, new Bit(false));//-15
		
		Assert.assertEquals(realAnswer.toString(), functionAnswer.toString());// negative numbers
		
		for(int i = 0; i < 32; i ++)
			number1.setBit(i, new Bit(false));
		number1.setBit(28, new Bit(true));
		number1.setBit(26, new Bit(true));
		number1.setBit(25, new Bit(true));
		number1.setBit(24, new Bit(true));
		number1.setBit(23, new Bit(true));
		number1.setBit(22, new Bit(true));//1000
		
		for(int i = 0; i < 32; i ++)
			number2.setBit(i, new Bit(false));
		number2.setBit(28, new Bit(true));
		number2.setBit(27, new Bit(true));
		number2.setBit(26, new Bit(true));
		number2.setBit(24, new Bit(true));
		number2.setBit(23, new Bit(true));
		number2.setBit(22, new Bit(true));
		number2.setBit(20, new Bit(true));//3000
		
		alu.doOperation(addOperation);//1000+3000
		
		for(int i = 0; i < 32; i ++)
			realAnswer.setBit(i, new Bit(false));
		realAnswer.setBit(20, new Bit(true));
		realAnswer.setBit(21, new Bit(true));
		realAnswer.setBit(22, new Bit(true));
		realAnswer.setBit(23, new Bit(true));
		realAnswer.setBit(26, new Bit(true));
		realAnswer.setBit(24, new Bit(true));//-20000
		
		Assert.assertEquals(realAnswer.toString(), functionAnswer.toString());//high numbers
		
	}
	
	@Test
	void ALUSubtractTest()
	{
		Word number1 = new Word();
		for(int i = 0; i < 32; i ++)
			number1.setBit(i, new Bit(false));
		number1.setBit(29, new Bit(true));//4
		
		Word number2 = new Word();
		for(int i = 0; i < 32; i ++)
			number2.setBit(i, new Bit(false));
		number2.setBit(29, new Bit(true));//4
		
		Bit[] addOperation = {new Bit(true),new Bit(true),new Bit(true),new Bit(true)};
		ALU alu = new ALU(number1,number2);
		alu.doOperation(addOperation);//4-4
		Word functionAnswer = alu.result;
				
		Word realAnswer = new Word();
		for(int i = 0; i < 32; i ++)
			realAnswer.setBit(i, new Bit(false));//0
		
		Assert.assertEquals(realAnswer.toString(), functionAnswer.toString());//low numbers

		for(int i = 0; i < 32; i ++)
			number1.setBit(i, new Bit(false));
		number1.setBit(30, new Bit(true));
		number1.setBit(28, new Bit(true));//10
		
		for(int i = 0; i < 32; i ++)
			number2.setBit(i, new Bit(false));
		number2.setBit(29, new Bit(true));
		number2.setBit(31, new Bit(true));//5
		
		alu.doOperation(addOperation);//10-5
				
		for(int i = 0; i < 32; i ++)
			realAnswer.setBit(i, new Bit(false));
		realAnswer.setBit(31, new Bit(true));
		realAnswer.setBit(29, new Bit(true));//5
		
		Assert.assertEquals(realAnswer.toString(), functionAnswer.toString());//low numbers
		
		for(int i = 0; i < 32; i ++)
			number1.setBit(i, new Bit(false));
		number1.setBit(30, new Bit(true));
		number1.setBit(28, new Bit(true));//10
		
		for(int i = 0; i < 32; i ++)
			number2.setBit(i, new Bit(true));
		number2.setBit(29, new Bit(false));//-5
		
		alu.doOperation(addOperation);//10-(-5)
				
		for(int i = 0; i < 32; i ++)
			realAnswer.setBit(i, new Bit(false));
		realAnswer.setBit(31, new Bit(true));
		realAnswer.setBit(30, new Bit(true));
		realAnswer.setBit(29, new Bit(true));
		realAnswer.setBit(28, new Bit(true));//15
		
		Assert.assertEquals(realAnswer.toString(), functionAnswer.toString());//negative and positive numbers
		
		for(int i = 0; i < 32; i ++)
			number1.setBit(i, new Bit(true));
		number1.setBit(31, new Bit(false));
		number1.setBit(28, new Bit(false));//-10
		
		for(int i = 0; i < 32; i ++)
			number2.setBit(i, new Bit(true));
		number2.setBit(29, new Bit(false));//-5
		
		alu.doOperation(addOperation);//-10-(-5)
				
		for(int i = 0; i < 32; i ++)
			realAnswer.setBit(i, new Bit(true));
		realAnswer.setBit(29, new Bit(false));//-5
		
		Assert.assertEquals(realAnswer.toString(), functionAnswer.toString());//negative numbers
		
		for(int i = 0; i < 32; i ++)
			number1.setBit(i, new Bit(false));
		number1.setBit(30, new Bit(true));
		number1.setBit(28, new Bit(true));//10
		
		for(int i = 0; i < 32; i ++)
			number2.setBit(i, new Bit(false));//0
		
		alu.doOperation(addOperation);//10-0
				
		for(int i = 0; i < 32; i ++)
			realAnswer.setBit(i, new Bit(false));
		realAnswer.setBit(30, new Bit(true));
		realAnswer.setBit(28, new Bit(true));//10
		
		Assert.assertEquals(realAnswer.toString(), functionAnswer.toString());//0
		
		for(int i = 0; i < 32; i ++)
			number1.setBit(i, new Bit(false));
		number1.setBit(28, new Bit(true));
		number1.setBit(26, new Bit(true));
		number1.setBit(25, new Bit(true));
		number1.setBit(24, new Bit(true));
		number1.setBit(23, new Bit(true));
		number1.setBit(22, new Bit(true));//1000
		
		for(int i = 0; i < 32; i ++)
			number2.setBit(i, new Bit(false));
		number2.setBit(28, new Bit(true));
		number2.setBit(27, new Bit(true));
		number2.setBit(26, new Bit(true));
		number2.setBit(24, new Bit(true));
		number2.setBit(23, new Bit(true));
		number2.setBit(22, new Bit(true));
		number2.setBit(20, new Bit(true));//3000
		
		alu.doOperation(addOperation);//1000-3000
		
		for(int i = 0; i < 32; i ++)
			realAnswer.setBit(i, new Bit(true));
		realAnswer.setBit(31, new Bit(false));
		realAnswer.setBit(30, new Bit(false));
		realAnswer.setBit(29, new Bit(false));
		realAnswer.setBit(28, new Bit(false));
		realAnswer.setBit(25, new Bit(false));
		realAnswer.setBit(24, new Bit(false));
		realAnswer.setBit(23, new Bit(false));
		realAnswer.setBit(22, new Bit(false));
		realAnswer.setBit(21, new Bit(false));//-20000
		
		Assert.assertEquals(realAnswer.toString(), functionAnswer.toString());//high numbers
		
		for(int i = 0; i < 32; i ++)
			number1.setBit(i, new Bit(false));//0
		
		for(int i = 0; i < 32; i ++)
			number2.setBit(i, new Bit(false));//0
		
		alu.doOperation(addOperation);//0+0
				
		for(int i = 0; i < 32; i ++)
			realAnswer.setBit(i, new Bit(false));//0
		
		Assert.assertEquals(realAnswer.toString(), functionAnswer.toString());//0
		
	}
	
	@Test
	void ALUadd4Test()
	{
		Word number1 = new Word();
		for(int i = 0; i < 32; i ++)
			number1.setBit(i, new Bit(false));
		number1.setBit(29, new Bit(true));//4
		
		Word number2 = new Word();
		for(int i = 0; i < 32; i ++)
			number2.setBit(i, new Bit(false));
		number2.setBit(30, new Bit(true));
		number2.setBit(29, new Bit(true));//6
		
		ALU alu = new ALU(number1,number2);
		Word functionAnswer = alu.add4(number1,number2,number1,number2);//4+6+4+6
					
		Word realAnswer = new Word();
		for(int i = 0; i < 32; i ++)
			realAnswer.setBit(i, new Bit(false));
		realAnswer.setBit(29, new Bit(true));
		realAnswer.setBit(27, new Bit(true));//20
		
		Assert.assertEquals(realAnswer.toString(), functionAnswer.toString());//low numbers
		
		for(int i = 0; i < 32; i ++)
			number1.setBit(i, new Bit(false));
		number1.setBit(28, new Bit(true));
		number1.setBit(26, new Bit(true));
		number1.setBit(25, new Bit(true));
		number1.setBit(24, new Bit(true));
		number1.setBit(23, new Bit(true));
		number1.setBit(22, new Bit(true));//1000
		
		for(int i = 0; i < 32; i ++)
			number2.setBit(i, new Bit(false));
		number2.setBit(28, new Bit(true));
		number2.setBit(27, new Bit(true));
		number2.setBit(26, new Bit(true));
		number2.setBit(24, new Bit(true));
		number2.setBit(23, new Bit(true));
		number2.setBit(22, new Bit(true));
		number2.setBit(20, new Bit(true));//3000
		
		functionAnswer = alu.add4(number1,number2,number1,number2);//1000+3000+1000+3000
				
		for(int i = 0; i < 32; i ++)
			realAnswer.setBit(i, new Bit(false));
		realAnswer.setBit(19, new Bit(true));
		realAnswer.setBit(20, new Bit(true));
		realAnswer.setBit(21, new Bit(true));
		realAnswer.setBit(22, new Bit(true));
		realAnswer.setBit(23, new Bit(true));
		realAnswer.setBit(25, new Bit(true));//8000
		
		Assert.assertEquals(realAnswer.toString(), functionAnswer.toString());//high numbers
		
		for(int i = 0; i < 32; i ++)
			number1.setBit(i, new Bit(true));
		number1.setBit(31, new Bit(false));
		number1.setBit(30, new Bit(false));//-4
		
		for(int i = 0; i < 32; i ++)
			number2.setBit(i, new Bit(false));
		number2.setBit(31, new Bit(true));
		number2.setBit(30, new Bit(true));//3
		
		functionAnswer = alu.add4(number1,number2,number1,number2);//-4+3-4+3
		
		for(int i = 0; i < 32; i ++)
			realAnswer.setBit(i, new Bit(true));
		realAnswer.setBit(31, new Bit(false));//-2
		
		Assert.assertEquals(realAnswer.toString(), functionAnswer.toString());//negative and positive numbers
		
		for(int i = 0; i < 32; i ++)
			number1.setBit(i, new Bit(true));
		number1.setBit(31, new Bit(false));
		number1.setBit(30, new Bit(false));//-4
		
		for(int i = 0; i < 32; i ++)
			number2.setBit(i, new Bit(true));
		number2.setBit(30, new Bit(false));//-3
		
		functionAnswer = alu.add4(number1,number2,number1,number2);//-4-3-4-3
		
		for(int i = 0; i < 32; i ++)
			realAnswer.setBit(i, new Bit(true));
		realAnswer.setBit(31, new Bit(false));
		realAnswer.setBit(29, new Bit(false));
		realAnswer.setBit(28, new Bit(false));//-14
		
		Assert.assertEquals(realAnswer.toString(), functionAnswer.toString());//negative numbers
		
		for(int i = 0; i < 32; i ++)
			number1.setBit(i, new Bit(false));//0
		
		for(int i = 0; i < 32; i ++)
			number2.setBit(i, new Bit(false));
		number2.setBit(28, new Bit(true));
		number2.setBit(27, new Bit(true));
		number2.setBit(26, new Bit(true));
		number2.setBit(24, new Bit(true));
		number2.setBit(23, new Bit(true));
		number2.setBit(22, new Bit(true));
		number2.setBit(20, new Bit(true));//3000
		
		functionAnswer = alu.add4(number1,number2,number1,number2);//0+3000+0+3000
		
		for(int i = 0; i < 32; i ++)
			realAnswer.setBit(i, new Bit(false));//0
		realAnswer.setBit(19, new Bit(true));
		realAnswer.setBit(21, new Bit(true));
		realAnswer.setBit(22, new Bit(true));
		realAnswer.setBit(23, new Bit(true));
		realAnswer.setBit(25, new Bit(true));
		realAnswer.setBit(26, new Bit(true));
		realAnswer.setBit(27, new Bit(true));//6000
		
		Assert.assertEquals(realAnswer.toString(), functionAnswer.toString());//6000
		
		for(int i = 0; i < 32; i ++)
			number1.setBit(i, new Bit(false));//0
		
		for(int i = 0; i < 32; i ++)
			number2.setBit(i, new Bit(false));//0
		
		functionAnswer = alu.add4(number1,number2,number1,number2);//0+0
				
		for(int i = 0; i < 32; i ++)
			realAnswer.setBit(i, new Bit(false));//0
		
		Assert.assertEquals(realAnswer.toString(), functionAnswer.toString());//0
		
	}
	
	@Test
	void ALUmultiplyTest()
	{
		Word number1 = new Word();
		for(int i = 0; i < 32; i ++)
			number1.setBit(i, new Bit(false));
		number1.setBit(29, new Bit(true));//4
		
		Word number2 = new Word();
		for(int i = 0; i < 32; i ++)
			number2.setBit(i, new Bit(false));
		number2.setBit(31, new Bit(true));
		number2.setBit(30, new Bit(true));//3
		
		Bit[] multiplyOperation = {new Bit(false),new Bit(true),new Bit(true),new Bit(true)};
		ALU alu = new ALU(number1,number2);
		alu.doOperation(multiplyOperation);
		Word functionAnswer = alu.result;
				
		Word realAnswer = new Word();
		for(int i = 0; i < 32; i ++)
			realAnswer.setBit(i, new Bit(false));
		realAnswer.setBit(29, new Bit(true));
		realAnswer.setBit(28, new Bit(true));//12
		
		Assert.assertEquals(realAnswer.toString(), functionAnswer.toString());//low numbers
		
		for(int i = 0; i < 32; i ++)
			number1.setBit(i, new Bit(false));
		number1.setBit(28, new Bit(true));
		number1.setBit(26, new Bit(true));
		number1.setBit(25, new Bit(true));
		number1.setBit(24, new Bit(true));
		number1.setBit(23, new Bit(true));
		number1.setBit(22, new Bit(true));//1000
		
		for(int i = 0; i < 32; i ++)
			number2.setBit(i, new Bit(false));
		number2.setBit(28, new Bit(true));
		number2.setBit(27, new Bit(true));
		number2.setBit(26, new Bit(true));
		number2.setBit(24, new Bit(true));
		number2.setBit(23, new Bit(true));
		number2.setBit(22, new Bit(true));
		number2.setBit(20, new Bit(true));//3000
		
		alu.doOperation(multiplyOperation);
		
		for(int i = 0; i < 32; i ++)
			realAnswer.setBit(i, new Bit(false));
		realAnswer.setBit(25, new Bit(true));
		realAnswer.setBit(24, new Bit(true));
		realAnswer.setBit(22, new Bit(true));
		realAnswer.setBit(21, new Bit(true));
		realAnswer.setBit(17, new Bit(true));
		realAnswer.setBit(16, new Bit(true));
		realAnswer.setBit(15, new Bit(true));
		realAnswer.setBit(13, new Bit(true));
		realAnswer.setBit(12, new Bit(true));
		realAnswer.setBit(10, new Bit(true));//30000
		
		Assert.assertEquals(realAnswer.toString(), functionAnswer.toString());//high numbers
		
		for(int i = 0; i < 32; i ++)
			number1.setBit(i, new Bit(false));//0
		
		for(int i = 0; i < 32; i ++)
			number2.setBit(i, new Bit(false));
		number2.setBit(28, new Bit(true));
		number2.setBit(27, new Bit(true));
		number2.setBit(26, new Bit(true));
		number2.setBit(24, new Bit(true));
		number2.setBit(23, new Bit(true));
		number2.setBit(22, new Bit(true));
		number2.setBit(20, new Bit(true));//3000
		
		alu.doOperation(multiplyOperation);
		
		for(int i = 0; i < 32; i ++)
			realAnswer.setBit(i, new Bit(false));//0
		
		Assert.assertEquals(realAnswer.toString(), functionAnswer.toString());//0
		
		for(int i = 0; i < 32; i ++)
			number1.setBit(i, new Bit(true));
		number1.setBit(31, new Bit(false));
		number1.setBit(30, new Bit(false));//-4
		
		for(int i = 0; i < 32; i ++)
			number2.setBit(i, new Bit(false));
		number2.setBit(31, new Bit(true));
		number2.setBit(30, new Bit(true));//3
		
		alu.doOperation(multiplyOperation);//(-4)*3
		
		for(int i = 0; i < 32; i ++)
			realAnswer.setBit(i, new Bit(true));
		realAnswer.setBit(31, new Bit(false));
		realAnswer.setBit(30, new Bit(false));
		realAnswer.setBit(28, new Bit(false));//-12
		
		Assert.assertEquals(realAnswer.toString(), functionAnswer.toString());//negative and positive numbers
		
		for(int i = 0; i < 32; i ++)
			number1.setBit(i, new Bit(true));
		number1.setBit(31, new Bit(false));
		number1.setBit(30, new Bit(false));//-4
		
		for(int i = 0; i < 32; i ++)
			number2.setBit(i, new Bit(true));
		number2.setBit(30, new Bit(false));//-3
		
		alu.doOperation(multiplyOperation);//(-4)*(-3)
		
		for(int i = 0; i < 32; i ++)
			realAnswer.setBit(i, new Bit(false));
		realAnswer.setBit(29, new Bit(true));
		realAnswer.setBit(28, new Bit(true));//12
		
		Assert.assertEquals(realAnswer.toString(), functionAnswer.toString());//negative numbers
		
		for(int i = 0; i < 32; i ++)
			number1.setBit(i, new Bit(false));//0
		
		for(int i = 0; i < 32; i ++)
			number2.setBit(i, new Bit(false));//0
		
		alu.doOperation(multiplyOperation);//0+0
				
		for(int i = 0; i < 32; i ++)
			realAnswer.setBit(i, new Bit(false));//0
		
		Assert.assertEquals(realAnswer.toString(), functionAnswer.toString());//0
		
		
		
	}
	
	@Test
	void ALUdoOperationTest()
	{
		Word number1 = new Word();
		for(int i = 0; i < 32; i ++)
			number1.setBit(i, new Bit(false));
		number1.setBit(29, new Bit(true));//4
		
		Word number2 = new Word();
		for(int i = 0; i < 32; i ++)
			number2.setBit(i, new Bit(false));
		number2.setBit(31, new Bit(true));
		number2.setBit(30, new Bit(true));//3
		
		Bit[] operation = {new Bit(true),new Bit(true),new Bit(false),new Bit(false)};//leftShift
		ALU alu = new ALU(number1,number2);
		alu.doOperation(operation);
		Word functionAnswer = alu.result;
				
		Word realAnswer = new Word();
		for(int i = 0; i < 32; i ++)
			realAnswer.setBit(i, new Bit(false));
		realAnswer.setBit(26, new Bit(true));//shifted 3 times
		
		Assert.assertEquals(realAnswer.toString(), functionAnswer.toString());
		
		for(int i = 0; i < 32; i ++)
			number1.setBit(i, new Bit(false));
		number1.setBit(31, new Bit(true));
		number1.setBit(30, new Bit(true));//3
		
		for(int i = 0; i < 32; i ++)
			number2.setBit(i, new Bit(false));
		number2.setBit(31, new Bit(true));//1
		
		operation[3] = new Bit(true);//rightShift
		alu.doOperation(operation);
				
		for(int i = 0; i < 32; i ++)
			realAnswer.setBit(i, new Bit(false));
		realAnswer.setBit(31, new Bit(true));//shifted 1 times
		
		Assert.assertEquals(realAnswer.toString(), functionAnswer.toString());
	}
	
	@Test
	void WordConstructorTest()
	{
		String test = "00000000000000000000000000000100";
		Word word = new Word(test);
		Word realAnswer = new Word();
		for(int i = 0; i < 32; i ++)
			realAnswer.setBit(i, new Bit(false));
		realAnswer.setBit(29, new Bit(true));//4
		Assert.assertEquals(realAnswer.toString(), word.toString());
	}
	
	@Test
	void WordIncrementTest()
	{
		Word word = new Word("00000000000000000000000000000000");
		word.increment();
		
		Word realAnswer = new Word("00000000000000000000000000000001");
		Assert.assertEquals(realAnswer.toString(), word.toString());//0
		
		word.set(5);
		word.increment();
		realAnswer.set(6);
		Assert.assertEquals(realAnswer.toString(), word.toString());//low number
		
		word.set(-5);
		word.increment();
		realAnswer.set(-4);
		Assert.assertEquals(realAnswer.toString(), word.toString());//negative number
		
		word.set(5000);
		word.increment();
		realAnswer.set(5001);
		Assert.assertEquals(realAnswer.toString(), word.toString());//high number
		
	}
	
	@Test
	void MainMemoryReadTest()
	{
		Word word = MainMemory.read(new Word("00000000000000000000000000010000"));
		Word realAnswer = new Word("00000000000000000000000000000000");
		
		Assert.assertEquals(realAnswer.toString(), word.toString());
	}
	
	@Test
	void MainMemoryWriteTest()
	{
		Word address = new Word();
		address.set(4);
		Word value = new Word();
		value.set(6);
		
		MainMemory.write(address,value);
		
		Assert.assertEquals(value.toString(), MainMemory.read(address).toString());
	}
	
	@Test
	void MainMemoryLoadTest()
	{
		String[] strings = new String[4];
		strings[0] = "00000000000000000000000000000100";
		strings[1] = "00000000000000000000000000000101";
		strings[2] = "00000000000000000000000000000110";
		strings[3] = "00000000000000000000000000000111";
		
		Word zero = new Word("00000000000000000000000000000000");
		Word one = new Word("00000000000000000000000000000001");
		Word two = new Word("00000000000000000000000000000010");
		Word three = new Word("00000000000000000000000000000011");
		
		MainMemory.load(strings);
		
		Word zeroWord = new Word(strings[0]);
		Word oneWord = new Word(strings[1]);
		Word twoWord = new Word(strings[2]);
		Word threeWord = new Word(strings[3]);
		
		Assert.assertEquals(zeroWord.toString(), MainMemory.read(zero).toString());
		Assert.assertEquals(oneWord.toString(), MainMemory.read(one).toString());
		Assert.assertEquals(twoWord.toString(), MainMemory.read(two).toString());
		Assert.assertEquals(threeWord.toString(), MainMemory.read(three).toString());
	}
	
	@Test
	void ProcessorHaltTest()
	{
		String[] strings = new String[6];
		strings[0] = "00000000000000000000000000000000"; // HALT
		strings[1] = "00000000000000010100000000100001"; // MATH DestOnly 5, R1
		strings[2] = "00000000000010000111100001000010"; // MATH ADD R1 R1 R2
		strings[3] = "00000000000000001011100001000011"; // MATH ADD R2 R2
		strings[4] = "00000000000010001011100001100010"; // MATH ADD R2 R1 R3
		strings[5] = "00000000000000000000000000000000"; // HALT
		MainMemory.load(strings);
		
		Processor process = new Processor();
		process.run();
		Word zero = new Word("00000000000000000000000000000000");
		
		Assert.assertEquals(zero.toString(), process.getRegisters()[1].toString());
		Assert.assertEquals(zero.toString(), process.getRegisters()[2].toString());
		Assert.assertEquals(zero.toString(), process.getRegisters()[3].toString());
	}
	
	@Test
	void ProcessorR0Test()
	{
		String[] strings = new String[2];
		strings[0] = "00000000000000010100000000000001";
		strings[1] = "00000000000000000000000000000000";
		MainMemory.load(strings);
		
		Processor process = new Processor();
		process.run();
		
		Word realAnswer = new Word("00000000000000000000000000000000");//0
		
		Assert.assertEquals(realAnswer.toString(), process.getRegisters()[0].toString());
	}
	
	@Test
	void ProcessorMathDestOnlyTest()
	{
		String[] strings = new String[2];
		strings[0] = "00000000000000010100000000100001";
		strings[1] = "00000000000000000000000000000000";
		MainMemory.load(strings);
		
		Processor process = new Processor();
		process.run();
		
		Word realAnswer = new Word("00000000000000000000000000000101");//5
		
		Assert.assertEquals(realAnswer.toString(), process.getRegisters()[1].toString());
	}
	
	@Test
	void ProcessorMath2RTest()
	{
		String[] strings = new String[4];
		strings[0] = "00000000000000010100000000100001"; // MATH DestOnly 5, R1
		strings[1] = "00000000000000010100000001000001"; // MATH DestOnly 5, R2
		strings[2] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[3] = "00000000000000000000000000000000"; // HALT
		MainMemory.load(strings);
		
		Processor process = new Processor();
		process.run();
		
		Word realAnswer = new Word("00000000000000000000000000001010");//10
		
		Assert.assertEquals(realAnswer.toString(), process.getRegisters()[2].toString());
	}
	
	@Test
	void ProcessorMath3RTest()
	{
		String[] strings = new String[4];
		strings[0] = "00000000000000010100000000100001"; // MATH DestOnly 5, R1
		strings[1] = "00000000000000010100000001000001"; // MATH DestOnly 5, R2
		strings[2] = "00000000000010001011100001100010"; // MATH ADD R2 R1 R3
		strings[3] = "00000000000000000000000000000000"; // HALT
		MainMemory.load(strings);
		
		Processor process = new Processor();
		process.run();
		
		Word realAnswer = new Word("00000000000000000000000000001010");//10
		
		Assert.assertEquals(realAnswer.toString(), process.getRegisters()[3].toString());
	}
	
	
	
	@Test
	void ProcessorMathAddTest()
	{
		String[] strings = new String[5];
		strings[0] = "00000000000000010100000000100001"; // MATH DestOnly 5, R1
		strings[1] = "00000000000010000111100001000010"; // MATH ADD R1 R1 R2
		strings[2] = "00000000000000001011100001000011"; // MATH ADD R2 R2
		strings[3] = "00000000000010001011100001100010"; // MATH ADD R2 R1 R3
		strings[4] = "00000000000000000000000000000000"; // HALT
		MainMemory.load(strings);
		
		Processor process = new Processor();
		process.run();
		
		Word realAnswer = new Word("00000000000000000000000000011001");//25
		
		Assert.assertEquals(realAnswer.toString(), process.getRegisters()[3].toString());
		
		strings = new String[5];
		strings[0] = "00000000000000000000000000100001"; // MATH DestOnly 0, R1
		strings[1] = "00000000000010000111100001000010"; // MATH ADD R1 R1 R2
		strings[2] = "00000000000000001011100001000011"; // MATH ADD R2 R2
		strings[3] = "00000000000010001011100001100010"; // MATH ADD R2 R1 R3
		strings[4] = "00000000000000000000000000000000"; // HALT
		MainMemory.load(strings);
		
		process = new Processor();
		process.run();
		
		realAnswer = new Word("00000000000000000000000000000000");//0
		
		Assert.assertEquals(realAnswer.toString(), process.getRegisters()[3].toString());//0
		
		strings = new String[5];
		strings[0] = "11111111111111101100000000100001"; // MATH DestOnly -5, R1
		strings[1] = "00000000000010000111100001000010"; // MATH ADD R1 R1 R2
		strings[2] = "00000000000000001011100001000011"; // MATH ADD R2 R2
		strings[3] = "00000000000010001011100001100010"; // MATH ADD R2 R1 R3
		strings[4] = "00000000000000000000000000000000"; // HALT
		MainMemory.load(strings);
		
		process = new Processor();
		process.run();
		
		realAnswer = new Word("11111111111111111111111111100111");//-25
		
		Assert.assertEquals(realAnswer.toString(), process.getRegisters()[3].toString());//negative numbers
	}
	
	@Test
	void WordDecrementTest()
	{
		Word word = new Word("00000000000000000000000000000000");
		word.decrement();
		
		Word realAnswer = new Word();
		realAnswer.set(-1);
		Assert.assertEquals(realAnswer.toString(), word.toString());//-1
		
		word.set(5);
		word.decrement();
		realAnswer.set(4);
		Assert.assertEquals(realAnswer.toString(), word.toString());//low number
		
		word.set(-5);
		word.decrement();
		realAnswer.set(-6);
		Assert.assertEquals(realAnswer.toString(), word.toString());//negative number
		
		word.set(5000);
		word.decrement();
		realAnswer.set(4999);
		Assert.assertEquals(realAnswer.toString(), word.toString());//high number
		
	}
	
	@Test
	void ProcessorBranchTest()
	{
		String[] strings = new String[7];
		strings[0] = "00000000000000010100000000100001"; // MATH DestOnly 5, R1
		strings[1] = "00000000000000010100000001000001"; // MATH DestOnly 5, R2
		strings[2] = "00000000000000000000000010000100"; // JUMP 4
		strings[3] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[4] = "00000000001000000100010001000111"; // BRANCH NOTEQUALS R2 R1 
		strings[5] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[6] = "00000000000000000000000000000000"; // HALT
		MainMemory.load(strings);
		
		Processor process = new Processor();
		process.run();
		
		Word realAnswer = new Word("00000000000000000000000000001010");//10
		
		Assert.assertEquals(realAnswer.toString(), process.getRegisters()[2].toString());//NOTEQUALS FALSE R2
		
		strings = new String[10];
		strings[0] = "00000000000000010000000000100001"; // MATH DestOnly 4, R1
		strings[1] = "00000000000000001000000001000001"; // MATH DestOnly 2, R2
		strings[2] = "00000000000000000000000011000100"; // JUMP 6
		strings[3] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[4] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[5] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[6] = "00000000000010000100010001000111"; // BRANCH NOTEQUALS R2 R1
		strings[7] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[8] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[9] = "00000000000000000000000000000000"; // HALT
		MainMemory.load(strings);
		
		process = new Processor();
		process.run();
		
		realAnswer.set(6);
		
		Assert.assertEquals(realAnswer.toString(), process.getRegisters()[2].toString());//NOTEQUALS TRUE R2
		
		strings = new String[6];
		strings[0] = "00000000000000010100000000100001"; // MATH DestOnly 5, R1
		strings[1] = "00000000000000011000000001000001"; // MATH DestOnly 6, R2
		strings[2] = "00000000000010000100000001000111"; // BRANCH EQUALS R2 R1
		strings[3] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[4] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[5] = "00000000000000000000000000000000"; // HALT
		MainMemory.load(strings);
		
		process = new Processor();
		process.run();
		
		realAnswer.set(16);
		
		Assert.assertEquals(realAnswer.toString(), process.getRegisters()[2].toString());//EQUALS FALSE R2
		
		strings = new String[6];
		strings[0] = "00000000000000010100000000100001"; // MATH DestOnly 5, R1
		strings[1] = "00000000000000010100000001000001"; // MATH DestOnly 5, R2
		strings[2] = "00000001000100000100000000000110"; // BRANCH EQUALS R2 R1, R0
		strings[3] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[4] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[5] = "00000000000000000000000000000000"; // HALT
		MainMemory.load(strings);
		
		process = new Processor();
		process.run();
		
		realAnswer.set(10);
		
		Assert.assertEquals(realAnswer.toString(), process.getRegisters()[2].toString());//EQUALS TRUE R3
		
		strings = new String[6];
		strings[0] = "00000000000000011000000000100001"; // MATH DestOnly 6, R1
		strings[1] = "00000000000000010100000001000001"; // MATH DestOnly 5, R2
		strings[2] = "00000001000010001000100000000110"; // BRANCH R2 < R1 , R0
		strings[3] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[4] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[5] = "00000000000000000000000000000000"; // HALT
		MainMemory.load(strings);
		
		process = new Processor();
		process.run();
		
		realAnswer.set(17);
		System.out.println(process.getRegisters()[2].getSigned());
		
		Assert.assertEquals(realAnswer.toString(), process.getRegisters()[2].toString());//LESS THAN FALSE R3
		
		strings = new String[8];
		strings[0] = "00000000000000010100000000100001"; // MATH DestOnly 5, R1
		strings[1] = "00000000000000011000000001000001"; // MATH DestOnly 6, R2
		strings[2] = "00000000000000000100000000000101"; // JUMP PC + 1
		strings[3] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[4] = "00000001000010001000100000000110"; // BRANCH R2 < R1 , R0
		strings[5] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[6] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[7] = "00000000000000000000000000000000"; // HALT
		MainMemory.load(strings);
		
		process = new Processor();
		process.run();
		
		realAnswer.set(11);
		
		Assert.assertEquals(realAnswer.toString(), process.getRegisters()[2].toString());//LESS THAN TRUE R3
		
		strings = new String[10];
		strings[0] = "00000000000000010100000000100001"; // MATH DestOnly 5, R1
		strings[1] = "00000000000000011000000001000001"; // MATH DestOnly 6, R2
		strings[2] = "00000001000010001000110000000110"; // BRANCH R1 >= R2 , R0 //FALSE
		strings[3] = "00000001000100000100110000000110"; // BRANCH R2 >= R1 , R0 //TRUE
		strings[4] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[5] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[6] = "00000000000000101100000001100001"; // MATH DestOnly 11, R3
		strings[7] = "00000001000100001100110000000110"; // BRANCH R1 >= R3 , R0 //TRUE
		strings[8] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[9] = "00000000000000000000000000000000"; // HALT
		MainMemory.load(strings);
		
		process = new Processor();
		process.run();
		
		realAnswer.set(11);
		
		Assert.assertEquals(realAnswer.toString(), process.getRegisters()[2].toString());//GREATER THAN OR EQUAL R3
		
		strings = new String[10];
		strings[0] = "00000000000000010100000000100001"; // MATH DestOnly 5, R1
		strings[1] = "00000000000000011000000001000001"; // MATH DestOnly 6, R2
		strings[2] = "00000001000010001001000000000110"; // BRANCH R1 > R2 , R0 //FALSE
		strings[3] = "00000001000100000101000000000110"; // BRANCH R2 > R1 , R0 //TRUE
		strings[4] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[5] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[6] = "00000000000000101100000001100001"; // MATH DestOnly 11, R3
		strings[7] = "00000001000100001101000000000110"; // BRANCH R1 > R3 , R0 //FALSE
		strings[8] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[9] = "00000000000000000000000000000000"; // HALT
		MainMemory.load(strings);
		
		process = new Processor();
		process.run();
		
		realAnswer.set(16);
		
		Assert.assertEquals(realAnswer.toString(), process.getRegisters()[2].toString());//GREATER THAN R3
		
		strings = new String[10];
		strings[0] = "00000000000000011000000000100001"; // MATH DestOnly 6, R1
		strings[1] = "00000000000000010100000001000001"; // MATH DestOnly 5, R2
		strings[2] = "00000001000010001001010000000110"; // BRANCH R1 <= R2 , R0 //FALSE
		strings[3] = "00000001000100000101010000000110"; // BRANCH R2 <= R1 , R0 //TRUE
		strings[4] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[5] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[6] = "00000000000000101100000001100001"; // MATH DestOnly 11, R3
		strings[7] = "00000001000100001101010000000110"; // BRANCH R1 <= R3 , R0 //TRUE
		strings[8] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[9] = "00000000000000000000000000000000"; // HALT
		MainMemory.load(strings);
		
		process = new Processor();
		process.run();
		
		realAnswer.set(11);
		
		Assert.assertEquals(realAnswer.toString(), process.getRegisters()[2].toString());//LESS THAN OR EQUAL R3
	}
	
	@Test
	void ProcessorCallTest()
	{
		String[] strings = new String[10];
		strings[0] = "00000000000000010100000000100001"; // MATH DestOnly 5, R1
		strings[1] = "00000000000000010100000001000001"; // MATH DestOnly 5, R2
		strings[2] = "00000000000000000000000010101000"; // CALL 5
		strings[3] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[4] = "00000000000000000000000000000000"; // HALT
		strings[5] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[6] = "00000000000000000000000100001000"; // CALL 8
		strings[7] = "00000000000000000000000000010000"; // RETURN
		strings[8] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[9] = "00000000000000000000000000010000"; // RETURN
		
		
		MainMemory.load(strings);
		
		Processor process = new Processor();
		process.run();
		
		Word realAnswer = new Word();
		realAnswer.set(20);
		
		Assert.assertEquals(5, process.getRegisters()[1].getSigned());
		Assert.assertEquals(20, process.getRegisters()[2].getSigned());//CALL 0R
		
		strings = new String[10];
		strings[0] = "00000000000000010100000000100001"; // MATH DestOnly 5, R1
		strings[1] = "00000000000000010100000001000001"; // MATH DestOnly 5, R2
		strings[2] = "00000000000000000000000000101001"; // CALL PC = R1 + 0
		strings[3] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[4] = "00000000000000000000000000000000"; // HALT
		strings[5] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[6] = "11111111111111111000000001001001"; // CALL PC = R2 + (-2)
		strings[7] = "00000000000000000000000000010000"; // RETURN
		strings[8] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[9] = "00000000000000000000000000010000"; // RETURN
		
		
		MainMemory.load(strings);
		
		process = new Processor();
		process.run();
		
		Assert.assertEquals(5, process.getRegisters()[1].getSigned());
		Assert.assertEquals(20, process.getRegisters()[2].getSigned());//CALL Dest Only
		
		strings = new String[10];
		strings[0] = "00000000000000010100000000100001"; // MATH DestOnly 5, R1
		strings[1] = "00000000000000010100000001000001"; // MATH DestOnly 5, R2
		strings[2] = "00000000000100001000000000101011"; // CALL R2 = R1? push PC; PC + 2
		strings[3] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[4] = "00000000000000000000000000000000"; // HALT
		strings[5] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[6] = "00000000000010001000000000101011"; // CALL R2 = R1? push PC; PC + 1
		strings[7] = "00000000000000000000000000010000"; // RETURN
		strings[8] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[9] = "00000000000000000000000000010000"; // RETURN
		
		
		MainMemory.load(strings);
		
		process = new Processor();
		process.run();
		
		Assert.assertEquals(5, process.getRegisters()[1].getSigned());
		Assert.assertEquals(15, process.getRegisters()[2].getSigned());//CALL 2R
		
		strings = new String[10];
		strings[0] = "00000000000000010100000000100001"; // MATH DestOnly 5, R1
		strings[1] = "00000000000000010100000001000001"; // MATH DestOnly 5, R2
		strings[2] = "00000110000010001000000000001010"; // CALL R1 = R2? push PC; R0 + 6
		strings[3] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[4] = "00000000000000000000000000000000"; // HALT
		strings[5] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[6] = "00001000000010001000000000001010"; // CALL R1 = R2? push PC; R0 + 8
		strings[7] = "00000000000000000000000000010000"; // RETURN
		strings[8] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[9] = "00000000000000000000000000010000"; // RETURN
		
		
		MainMemory.load(strings);
		
		process = new Processor();
		process.run();
		
		Assert.assertEquals(5, process.getRegisters()[1].getSigned());
		Assert.assertEquals(15, process.getRegisters()[2].getSigned());//CALL 3R
	}
	
	@Test
	void ProcessorPushPopTest()
	{
		String[] strings = new String[7];
		strings[0] = "00000000000000001100000000100001"; // MATH DestOnly 3, R1
		strings[1] = "00000000000000001000000001000001"; // MATH DestOnly 2, R2
		strings[2] = "00000000000000001011100000101101"; // PUSH R1 + 2
		strings[3] = "00000000000000000000000001111001"; // POP R3
		strings[4] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[5] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[6] = "00000000000000000000000000000000"; // HALT
		
		MainMemory.load(strings);
		
		Processor process = new Processor();
		process.run();
		
		Assert.assertEquals(3, process.getRegisters()[1].getSigned());
		Assert.assertEquals(8, process.getRegisters()[2].getSigned());
		Assert.assertEquals(5, process.getRegisters()[3].getSigned());//PUSH and POP Dest Only
		
		strings = new String[9];
		strings[0] = "00000000000000001100000000100001"; // MATH DestOnly 3, R1
		strings[1] = "00000000000000001000000001000001"; // MATH DestOnly 2, R2
		strings[2] = "00000000000000001011100000101111"; // PUSH R1 + R2
		strings[3] = "00000000000000001011110000101111"; // PUSH R1 - R2
		strings[4] = "11111111111100000100000001111011"; // POP R3 = MEM[SP + (R1 + (-2))]
		strings[5] = "11111111111100001000000010011011"; // POP R4 = MEM[SP + (R2 + (-2))]
		strings[6] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[7] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[8] = "00000000000000000000000000000000"; // HALT
		
		MainMemory.load(strings);
		
		process = new Processor();
		process.run();
		
		Assert.assertEquals(3, process.getRegisters()[1].getSigned());
		Assert.assertEquals(8, process.getRegisters()[2].getSigned());
		Assert.assertEquals(5, process.getRegisters()[3].getSigned());
		Assert.assertEquals(1, process.getRegisters()[4].getSigned());//PUSH and POP 2R
		
		strings = new String[10];
		strings[0] = "00000000000000001100000000100001"; // MATH DestOnly 3, R1
		strings[1] = "00000000000000001000000001000001"; // MATH DestOnly 2, R2
		strings[2] = "11111111111111111000000010100001"; // MATH DestONly -2, R5
		strings[3] = "00000000000010001011100000001110"; // PUSH R1 + R2
		strings[4] = "00000000000010001011110000001110"; // PUSH R1 - R2
		strings[5] = "00000000000010010100000001111010"; // POP R3 = MEM[SP + (R1 + R5)]
		strings[6] = "00000000000100010100000010011010"; // POP R4 = MEM[SP + (R2 + R5)]
		strings[7] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[8] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[9] = "00000000000000000000000000000000"; // HALT
		
		MainMemory.load(strings);
		
		process = new Processor();
		process.run();
		
		Assert.assertEquals(3, process.getRegisters()[1].getSigned());
		Assert.assertEquals(8, process.getRegisters()[2].getSigned());
		Assert.assertEquals(5, process.getRegisters()[3].getSigned());
		Assert.assertEquals(1, process.getRegisters()[4].getSigned());//PUSH and POP 3R
	}
	
	@Test
	void ProcessorStoreLoadTest()
	{
		String[] strings = new String[4];
		strings[0] = "00000000000000101000000000100001"; // MATH DestOnly 10, R1
		strings[1] = "00000000000000000100000000110101"; // STORE MEM[R1] = 1
		strings[2] = "00000000000000000000000000110001"; // LOAD R1 = MEM[R1+0]
		strings[3] = "00000000000000000000000000000000"; // HALT
		
		MainMemory.load(strings);
		
		Processor process = new Processor();
		process.run();
		
		Assert.assertEquals(1, process.getRegisters()[1].getSigned());//STORE and LOAD Dest Only
		
		strings = new String[5];
		strings[0] = "00000000000000001100000000100001"; // MATH DestOnly 3, R1
		strings[1] = "00000000000000001000000001000001"; // MATH DestOnly 2, R2
		strings[2] = "00000000010100001000000000110111"; // STORE MEM[R1 + 10] = R2
		strings[3] = "00000000010110001000000000110011"; // LOAD R1 = MEM[R2+11]
		strings[4] = "00000000000000000000000000000000"; // HALT
		
		MainMemory.load(strings);
		
		process = new Processor();
		process.run();
		
		Assert.assertEquals(2, process.getRegisters()[1].getSigned());
		Assert.assertEquals(2, process.getRegisters()[2].getSigned());//STORE and LOAD 2R
		
		strings = new String[6];
		strings[0] = "00000000000000001100000000100001"; // MATH DestOnly 3, R1
		strings[1] = "00000000000000001000000001000001"; // MATH DestOnly 2, R2
		strings[2] = "00000000000000010100000001100001"; // MATH DestOnly 5, R3
		strings[3] = "00000000000110001000000000110110"; // STORE MEM[R1 + R3] = R2
		strings[4] = "00000000000010001100000010010010"; // LOAD R4 = MEM[R1+R3]
		strings[5] = "00000000000000000000000000000000"; // HALT
		
		MainMemory.load(strings);
		
		process = new Processor();
		process.run();
		
		Assert.assertEquals(3, process.getRegisters()[1].getSigned());
		Assert.assertEquals(2, process.getRegisters()[2].getSigned());
		Assert.assertEquals(5, process.getRegisters()[3].getSigned());
		Assert.assertEquals(2, process.getRegisters()[4].getSigned());//STORE and LOAD 3R
	}
	
	@Test
	void ProcessorMegaTest()
	{
		String[] strings = new String[13];
		strings[0] = "00000000000000101000000000100001"; // MATH DestOnly 10, R1
		strings[1] = "00000000000000010100000001000001"; // MATH DestOnly 5, R2
		strings[2] = "00000000000010001011100001100010"; // MATH ADD R2 R1 R3
		strings[3] = "11111110000110001001000000101010"; // CALL R3>R2? PC = R1 + (-2)
		strings[4] = "00000000000110010011100000001110"; // PUSH R3 + R4 , R0
		strings[5] = "11111111111111101100000010100001"; // MATH DestOnly -5, R5
		strings[6] = "00000000000100010111100011011010"; // PEEK R6 = MEM[SP - (R2+R5)]
		strings[7] = "00000000000000000000000000000000"; // HALT
		strings[8] = "00000000000000001011100000101101"; // PUSH R1 + 2     //CALL goes here
		strings[9] = "00000000000000000000000010011001"; // POP R4
		strings[10] ="00000000000000001011100000100011"; // MATH ADD R2 R1
		strings[11] ="11111100001000001100100000000110"; // BRANCH R4 < R3? PC -= 4
		strings[12] ="00000000000000000000000000010000"; // RETURN

		MainMemory.load(strings);
		
		Processor process = new Processor();
		process.run();
		
		Assert.assertEquals(20, process.getRegisters()[1].getSigned());
		Assert.assertEquals(5, process.getRegisters()[2].getSigned());
		Assert.assertEquals(15, process.getRegisters()[3].getSigned());
		Assert.assertEquals(17, process.getRegisters()[4].getSigned());
		Assert.assertEquals(-5, process.getRegisters()[5].getSigned());
		Assert.assertEquals(32, process.getRegisters()[6].getSigned());
		
		strings = new String[12];
		strings[0] = "00000000000000000100000000100001"; // MATH DestOnly 1, R1
		strings[1] = "00000000000000000100000000010101"; // STORE MEM[R0] <= IMM
		strings[2] = "00000000000000000100000000110101"; // STORE MEM[R1] <= IMM
		strings[3] = "00000000000000001000000001000001"; // MATH DestOnly 2, R2
		strings[4] = "00000000000000010100000001100001"; // MATH DestOnly 5, R3
		strings[5] = "11111111111100001000000010010011"; // LOAD R4 = MEM[R2 - 2]
		strings[6] = "11111111111110001000000010110011"; // LOAD R5 = MEM[R2 - 1]
		strings[7] = "00000000001000010111100011000010"; // MATH R6 = R4 + R5
		strings[8] = "00000000000000011000000001010111"; // STORE MEM[R2] <= R6
		strings[9] = "00000000000000000111100001000011"; // MATH ADD R2 R1
		strings[10] ="11111111110100001001010001100111"; // BRANCH R2 <= R3? PC -= 6
		strings[11] ="00000000000000000000000000000000"; // HALT
		
		MainMemory.load(strings);
		
		process = new Processor();
		process.run();
		
		Word word = new Word();
		word.set(0);
		Assert.assertEquals(1, MainMemory.read(word).getSigned());
		word.set(1);
		Assert.assertEquals(1, MainMemory.read(word).getSigned());
		word.set(2);
		Assert.assertEquals(2, MainMemory.read(word).getSigned());
		word.set(3);
		Assert.assertEquals(3, MainMemory.read(word).getSigned());
		word.set(4);
		Assert.assertEquals(5, MainMemory.read(word).getSigned());
		word.set(5);
		Assert.assertEquals(8, MainMemory.read(word).getSigned());// fibonacci series to 5
		
		
		
	}
	
	@Test
	void AssemblerMathTest() throws FileNotFoundException
	{
		String test = "HALT \n COPY R1 1 \n MATH ADD R1 R2 \n MATH SUBTRACT R1 R2 R3 \n";
		
		Lexer lexer2 = new Lexer(test, true);
		Parser parser = new Parser(lexer2);
		String[] strings = parser.parse();
		
		Assert.assertEquals("00000000000000000000000000000000", strings[0]);//HALT 
		Assert.assertEquals("00000000000000000100000000100001",strings[1]);//COPY R1 1 
		Assert.assertEquals("00000000000000000111100001000011",strings[2]);//MATH ADD R1 R2 
		Assert.assertEquals("00000000000010001011110001100010",strings[3]);//MATH SUBTRACT R1 R2 R3
	}
	
	@Test
	void AssemblerBranchTest() throws FileNotFoundException
	{
		String test = "JUMP \n JUMP R0 3 \n BRANCH LESS R0 R1 3 \n BRANCH GREATER R1 R2 R3 4 \n";
		
		Lexer lexer2 = new Lexer(test, true);
		Parser parser = new Parser(lexer2);
		String[] strings = parser.parse();
		
		Assert.assertEquals("00000000000000000000000000000100", strings[0]);//JUMP 
		Assert.assertEquals("00000000000000001100000000000101",strings[1]);//JUMP R0 3 
		Assert.assertEquals("00000000000110000000100000100111",strings[2]);//BRANCH LESS R0 R1 3
		Assert.assertEquals("00000100000010001001000001100110",strings[3]);//BRANCH GREATER R1 R2 R3 4
	}
	
	@Test
	void AssemblerCallTest() throws FileNotFoundException
	{
		String test = "CALL \n CALL R0 3 \n CALL GREATER R1 R2 0 \n CALL LESSOREQUAL R1 R2 R3 4 \n";
		
		Lexer lexer2 = new Lexer(test, true);
		Parser parser = new Parser(lexer2);
		String[] strings = parser.parse();
		
		Assert.assertEquals("00000000000000000000000000001000", strings[0]);//CALL 
		Assert.assertEquals("00000000000000001100000000001001",strings[1]);//CALL R0 3 
		Assert.assertEquals("00000000000000000101000001001011",strings[2]);//CALL GREATER R1 R2 0
		Assert.assertEquals("00000100000010001001010001101010",strings[3]);//CALL LESSOREQUAL R1 R2 R3 4
	}
	
	@Test
	void AssemblerPushTest() throws FileNotFoundException
	{
		String test = "PUSH \n PUSH ADD R0 3 \n PUSH MULTIPLY R1 R2 0 \n PUSH ADD R1 R2 R3 4 \n";
		
		Lexer lexer2 = new Lexer(test, true);
		Parser parser = new Parser(lexer2);
		String[] strings = parser.parse();
		
		Assert.assertEquals("00000000000000000000000000001100", strings[0]);//PUSH 
		Assert.assertEquals("00000000000000001111100000001101",strings[1]);//PUSH ADD R0 3 
		Assert.assertEquals("00000000000000000101110001001111",strings[2]);//PUSH MULTIPLY R1 R2 0
		Assert.assertEquals("00000100000010001011100001101110",strings[3]);//PUSH ADD R1 R2 R3 4
	}
	
	@Test
	void AssemblerLoadTest() throws FileNotFoundException
	{
		String test = "RETURN \n LOAD R0 3 \n LOAD R1 R2 0 \n LOAD R1 R2 R3 4 \n";
		
		Lexer lexer2 = new Lexer(test, true);
		Parser parser = new Parser(lexer2);
		String[] strings = parser.parse();
		
		Assert.assertEquals("00000000000000000000000000010000", strings[0]);//RETURN 
		Assert.assertEquals("00000000000000001100000000010001",strings[1]);//LOAD R0 3 
		Assert.assertEquals("00000000000000000100000001010011",strings[2]);//LOAD R1 R2 0
		Assert.assertEquals("00000100000010001000000001110010",strings[3]);//LOAD R1 R2 R3 4
	}
	
	@Test
	void AssemblerStoreTest() throws FileNotFoundException
	{
		String test = "STORE \n STORE R0 3 \n STORE R1 R2 0 \n STORE R1 R2 R3 4 \n";
		
		Lexer lexer2 = new Lexer(test, true);
		Parser parser = new Parser(lexer2);
		String[] strings = parser.parse();
		
		Assert.assertEquals("00000000000000000000000000010100", strings[0]);//STORE 
		Assert.assertEquals("00000000000000001100000000010101",strings[1]);//STORE R0 3 
		Assert.assertEquals("00000000000000000100000001010111",strings[2]);//STORE R1 R2 0
		Assert.assertEquals("00000100000010001000000001110110",strings[3]);//STORE R1 R2 R3 4
	}
	
	@Test
	void AssemblerPopTest() throws FileNotFoundException
	{
		String test = "POP \n POP R0 3 \n PEEK R1 R2 0 \n PEEK R1 R2 R3 4 \n";
		
		Lexer lexer2 = new Lexer(test, true);
		Parser parser = new Parser(lexer2);
		String[] strings = parser.parse();
		
		Assert.assertEquals("00000000000000000000000000011000", strings[0]);//POP 
		Assert.assertEquals("00000000000000001100000000011001",strings[1]);//POP R0 3 
		Assert.assertEquals("00000000000000000100000001011011",strings[2]);//PEEK R1 R2 0
		Assert.assertEquals("00000100000010001000000001111010",strings[3]);//PEEK R1 R2 R3 4
	}
	
	
	

}
;