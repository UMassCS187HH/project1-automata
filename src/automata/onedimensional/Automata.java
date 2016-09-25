package automata.onedimensional;

public class Automata
{	
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
	 * Evolve the pattern in originalPattern and store it in newPattern, using the specified rule
	 * @param originalPattern where the original pattern is stored
	 * @param newPattern where to store the new pattern
	 * @param rule the rule number, Wolfram style, from 0 to 255
	 */
	public static void evolve(BitArrayNoBP originalPattern, BitArrayNoBP newPattern, int rule)
	{
		for (int i = 1; i < originalPattern.getSize() - 1; i++) // keep the leftmost and rightmost cells as a wall of falses
		{
			// get 3 bit representation of cell and its neighbors
			byte representation = (byte)(
				  (originalPattern.get(i - 1) << 2)
				| (originalPattern.get(i    ) << 1)
				| (originalPattern.get(i + 1) << 0)
				);
			long newBit = (rule & (1 << representation)) >>> representation;
			newPattern.set(i, newBit);
		}
	}
}
