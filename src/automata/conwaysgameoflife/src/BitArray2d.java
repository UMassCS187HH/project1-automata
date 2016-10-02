package automata.conwaysgameoflife.src;

/**
 * 2 dimensional bit array with memory-efficient storage
 */
public class BitArray2d
{
	private int width, height;
	public long[] bits; // public for testing purposes only
	
	private static int BITS_IN_LONG = 64;
	
	
	/**
	 * Creates a new 2d bit array with the specified size
	 * @param width width of the bit array
	 * @param height height of the bit array
	 */
	public BitArray2d(int width, int height)
	{
		this.width = width;
		this.height = height;
		this.bits = new long[(width * height - 1) / BITS_IN_LONG + 1];
	}
	
	/**
	 * Evolves array1 according to the rules and puts the result into array2
	 * @param array1 the original array which we will evolve
	 * @param array2 the array to store the new, evolved array
	 * @throws IllegalArgumentException if the two BitArray2d's are not the same size
	 */
	public static void evolve(BitArray2d array1, BitArray2d array2)
	{
		// check to make sure both arrays are of the same size
		if (array1.width != array2.width || array1.height != array2.height)
		{
			throw new IllegalArgumentException("Both arrays in evolve must be of same size. First array is " + array1.width + "x" + array2.height + " while second array is " + array2.width + "x" + array2.height);
		}
		
		// do the evolution; start from row 1 not zero and don't go to last row because
		// we assume that the boundaries are dead cells
		for (int x = 1; x < array1.width - 1; x++)
		{
			for (int y = 1; y < array1.height - 1; y++)
			{
				// count number of live neighbors of this cell
				int neighbors = 
					(array1.get(x - 1, y - 1) ? 1 : 0) +
					(array1.get(x - 1, y    ) ? 1 : 0) +
					(array1.get(x - 1, y + 1) ? 1 : 0) +
					(array1.get(x    , y + 1) ? 1 : 0) +
					(array1.get(x + 1, y + 1) ? 1 : 0) +
					(array1.get(x + 1, y    ) ? 1 : 0) +
					(array1.get(x + 1, y - 1) ? 1 : 0) +
					(array1.get(x    , y - 1) ? 1 : 0)
					;
				if (array1.get(x, y)) // if we start with a live cell
				{
					if (neighbors < 2) // underpopulation
					{
						array2.set(x, y, false);
					} else if (neighbors > 3) // overpopulation
					{
						array2.set(x, y, false);
					} else // nothing happens
					{
						array2.set(x, y, true);
					}
				} else // if we start with a dead cell
				{
					if (neighbors == 3) // reproduction
					{
						array2.set(x, y, true);
					} else // nothing happens
					{
						array2.set(x, y, false);
					}
				}
			}
		}
	}
	
	/**
	 * Returns the value of the bit array at the specified (x,y) pair
	 * @param x x-coord of point
	 * @param y y-y-coord of point
	 * @return the value at the specified point
	 * @throws ArrayIndexOutOfBoundsException if x or y are not in bounds
	 */
	public boolean get(int x, int y)
	{
		if (x < 0 || y < 0 || x > this.width || y > this.height)
		{
			throw new ArrayIndexOutOfBoundsException("Error: tried to access point (" + x + ", " + y + ") on a " + this.width + "x" + this.height + " array");
		}
		long bits = this.bits[(x + y * this.width) / BITS_IN_LONG];
		return (bits & (1L << ((x + y * this.width) % BITS_IN_LONG))) != 0L;
	}
	
	/**
	 * Returns the height of the array
	 * @return the height
	 */
	public int getHeight()
	{
		return this.height;
	}
	
	/**
	 * Returns the width of the array
	 * @return the width
	 */
	public int getWidth()
	{
		return this.width;
	}
	
	/**
	 * Sets the value of the array at the given point to the given value
	 * @param x x-coord of point to set at
	 * @param y y-coord of point to set at
	 * @param val value to set
	 * @throws ArrayIndexOutOfBoundsException if x or y are not in bounds
	 */
	public void set(int x, int y, boolean val)
	{
		if (x < 0 || y < 0 || x > this.width || y > this.height)
		{
			throw new ArrayIndexOutOfBoundsException("Error: tried to access point (" + x + ", " + y + ") on a " + this.width + "x" + this.height + " array");
		}
		long bits = this.bits[(x + y * this.width) / BITS_IN_LONG];
		if (val)
		{
			bits |= (1L << ((x + y * this.width) % BITS_IN_LONG));
		} else
		{
			bits &= ~(1L << ((x + y * this.width) % BITS_IN_LONG));
		}
		this.bits[(x + y * this.width) / BITS_IN_LONG] = bits;
	}
	
	public String toString()
	{
		StringBuffer bf = new StringBuffer();
		char[][] result = new char[this.height][this.width];
		for (int row = 0; row < this.height; row++)
		{
			for (int col = 0; col < this.width; col++)
			{
				result[row][col] = this.get(col, row) ? '█' : ' ';
			}
			bf.append(result[row]);
			bf.append('\n');
		}
		return bf.toString();
	}
}
