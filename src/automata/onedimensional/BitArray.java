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
	 * Evolve the pattern in originalPattern and store it in newPattern, using the specified rule
	 * @param originalPattern where the original pattern is stored
	 * @param newPattern where to store the new pattern
	 * @param rule the rule number, Wolfram style, from 0 to 255
	 */
	public static void evolve(BitArray originalPattern, BitArray newPattern, int rule)
	{
		for (int i = 1; i < originalPattern.getSize() - 1; i++) // keep the leftmost and rightmost cells as a wall of falses
		{
			// get 3 bit representation of cell and its neighbors
			byte representation = (byte)(
				  ((originalPattern.get(i - 1) ? 1 : 0) << 2)
				| ((originalPattern.get(i    ) ? 1 : 0) << 1)
				| ((originalPattern.get(i + 1) ? 1 : 0) << 0)
				);
			boolean newBit = (rule & (1 << representation)) != 0;
			newPattern.set(i, newBit);
		}
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
				bits[loc / BITS_IN_LONG] |= (1L << (loc % BITS_IN_LONG));
			} else
			{
				bits[loc / BITS_IN_LONG] &= ~(1L << (loc % BITS_IN_LONG)); // DO NOT FORGET THAT SECOND L
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