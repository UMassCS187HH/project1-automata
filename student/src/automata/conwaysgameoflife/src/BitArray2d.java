package automata.conwaysgameoflife.src;

/**
 * 2 dimensional bit array with memory-efficient storage
 */
public class BitArray2d
{
	private final int width, height;
	public final long[] bits; // public for testing purposes only
	
	private final static int BITS_IN_LONG = 64;
	
	
	/**
	 * Creates a new 2d bit array with the specified size
	 * @param width width of the bit array
	 * @param height height of the bit array
	 */
	public BitArray2d(int width, int height)
	{
		
	}
	
	/**
	 * Evolves array1 according to the rules and puts the result into array2
	 * @param array1 the original array which we will evolve
	 * @param array2 the array to store the new, evolved array
	 * @throws IllegalArgumentException if the two BitArray2d's are not the same size
	 */
	public static void evolve(BitArray2d array1, BitArray2d array2)
	{
		
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
