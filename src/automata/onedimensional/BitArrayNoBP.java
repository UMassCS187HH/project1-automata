package automata.onedimensional;

/**
 * identical to the BitArray class except there are no if statements (only
 * bitwise operations) so as to (hopefully) increase performance via reduced
 * branch prediction.
 * 
 * THIS CLASS IS NOT ACTUALLY FASTER!! DON"T USE IT
 */
public class BitArrayNoBP
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
	public BitArrayNoBP(int size)
	{
		this.bits = new long[(size - 1) / BITS_IN_LONG + 1];
		this.size = size;
	}

	/**
	 * Returns the value in the array at the specified location
	 * @param loc the location from which to retrieve the bit (from 0 to this.getSize() - 1 inclusive)
	 * @return 1 if the value at the location is true, 0 otherwise
	 */
	public long get(int loc)
	{
		return (bits[loc / BITS_IN_LONG] & (1L << (loc % BITS_IN_LONG))) >>> (loc % BITS_IN_LONG);
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
	 * @param loc the index at which to set the bit (1 if true, 0 otherwise)
	 * @param val the value to set the given bit to
	 */
	public void set(int loc, long val)
	{
		int n = loc % BITS_IN_LONG;
		long newVal = val << n;
		long oldVal = this.bits[loc / BITS_IN_LONG];
		long filter = ~(1L << n);
		newVal |= (oldVal & filter);
		this.bits[loc / BITS_IN_LONG] = newVal;
	}
	
	public String toString()
	{
		char[] result = new char[this.size];
		for (int i = 0; i < this.size; i++)
		{
			result[i] = (this.get(i) == 1L) ? '█' : ' ';
		}
		return new String(result);
	}
	
	public String toStringNoBP()
	{
		char[] result = new char[this.size];
		for (int i = 0; i < this.size; i++)
		{
			result[i] = (char)((int)' ' + ((int)'█' - (int)' ') * (int)this.get(i));
		}
		return new String(result);
	}
}
