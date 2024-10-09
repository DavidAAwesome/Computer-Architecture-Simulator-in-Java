
public class Bit {

	private boolean value;
	
	public Bit(Boolean value)
	{
		set(value);
	}
	
	public void set(Boolean value)
	{
		this.value = value;
	}
	
	public void toggle()
	{
		if(value == false)
			value = true;
		else
			value = false;
	}
	
	public void set()
	{
		value = true;
	}
	
	public void clear()
	{
		value = false;
	}
	
	public Boolean getValue()
	{
		return value;
	}
	
	public Bit and(Bit other)
	{
		if(value == true)
			if(other.getValue() == true)
				return new Bit(true);
		return new Bit(false);
	}
	
	public Bit or(Bit other)
	{
		if(!value)
			if(!other.getValue())
				return new Bit(false);
		return new Bit(true);
	}
	
	public Bit xor(Bit other)
	{
		if(value != other.getValue())
			return new Bit(true);
		return new Bit(false);
	}
	
	public Bit not()
	{
		return new Bit(!value);
	}
	
	public String toString()
	{
		if(value)
			return "t";
		return "f";
	}
}
