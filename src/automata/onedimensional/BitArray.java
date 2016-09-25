package automata.onedimensional;
public class BitArray
{
	/**
	 * How many bits are used to store a long. Should be 64 on most machines
	 * for the foreseeable future.
	 */
	public static final int BITS_IN_LONG = Long.BYTES * 8;
	
	/**
	 * The array that stores the actual bit data
	 */
	long[] bits;
	
	/**
	 * How many bits are stored in the array
	 */
	private int size;
	
	/**
	 * Constructs a bit array with a given number of bits
	 * @param size how many bits to store in the array
	 */
	public BitArray(int size)
	{
		this.bits = new long[(size - 1) / BITS_IN_LONG + 1];
		this.size = size;
	}
	
	/**
	 * Returns the value in the array at the specified location
	 * @param loc the location from which to retrieve the bit (from 0 to this.getSize() - 1 inclusive)
	 * @return the value at the specified location
	 */
	public boolean get(int loc)
	{
		if (loc < this.size)
		{
			return (bits[loc / BITS_IN_LONG] & (1L << (loc % BITS_IN_LONG))) != 0L;
		} else
		{
			throw new ArrayIndexOutOfBoundsException("Exception: tried to set value of BitArray that was out of bounds: " + loc + ". Size was " + this.size);
		}
	}
	
	/**
	 * Returns the number of bits in the array
	 * @return the number of bits stored in the array
	 */
	public int getSize()
	{
		return this.size;
	}
	
	/**
	 * Sets the bit at the given location to the given value
	 * @param loc the index at which to set the bit
	 * @param val the value to set the given bit to
	 */
	public void set(int loc, boolean val)
	{
		if (loc < this.size)
		{
			if (val)
			{
				//System.out.println(Long.toBinaryString(bits[loc / BITS_IN_LONG]));
				bits[loc / BITS_IN_LONG] |= (1L << (loc % BITS_IN_LONG));
				//System.out.println(Long.toBinaryString((1L << (loc % BITS_IN_LONG))));
				//System.out.println(Long.toBinaryString(bits[loc / BITS_IN_LONG]));
				//System.out.println("New value at " + loc + " is " + this.get(loc));
				//System.out.println();
			} else
			{
				bits[loc / BITS_IN_LONG] &= (-1L ^ (1L << (loc % BITS_IN_LONG))); // DO NOT FORGET THAT SECOND L
																				  // TOOK ME FOREVER TO FIX WHEN I FORGOT
			}
		} else
		{
			throw new ArrayIndexOutOfBoundsException("Exception: tried to set value of BitArray that was out of bounds: " + loc + ". Size was " + this.size);
		}
	}
	
	public String toString()
	{
		char[] result = new char[this.size];
		for (int i = 0; i < this.size; i++)
		{
			result[i] = this.get(i) ? '█' : ' ';
		}
		return new String(result);
	}
}
