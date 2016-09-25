package automata.onedimensional;
import java.awt.image.*;

public class AutomataImageGenerator
{
	private BitArrayLinkedListNode firstRow, lastRow;
	private final int width, height;
	private final int rule;
	private int trueColor = 0b00000000_00000000_00000000_00000000, falseColor = 0b00000000_11111111_11111111_11111111; // black and white, respectively
	
	/**
	 * Creates a new Image generator with the given dimensions and rule number
	 * @param width the width of the generated images
	 * @param height the height of the generated images
	 * @param rule the rule number, in Wolfram style, of the automata, from 0 to 255 inclusive
	 * @throws IllegalArgumentException if rule is not in the given range
	 */
	public AutomataImageGenerator(int width, int height, int rule)
	{
		this.width = width;
		this.height = height;
		if (rule < 256 && rule > 0)
		{
			this.rule = rule;
		} else
		{
			throw new IllegalArgumentException("Error: rule must be between 0 and 255 inclusive: " + rule);
		}
		// fill rows list with all 0 arrays
		this.firstRow = new BitArrayLinkedListNode();
		BitArrayLinkedListNode curNode = this.firstRow, nextNode;
		for (int row = 1; row < this.height; row++)
		{
			nextNode = new BitArrayLinkedListNode();
			nextNode.val = new BitArray(this.width);
			curNode.nextNode = nextNode;
			curNode = nextNode;
		}
		this.lastRow = curNode;
	}
	
	/**
	 * Initializes the Image generator with the given dimensions, and rule, but
	 * with the first (ie, bottom-most) row to be the given {@ BitArray}
	 * @param width width of the generated images
	 * @param height height of the generated images
	 * @param rule the rule number, in Wolfram style, of the automata, from 0 to 255 inclusive
	 * @param firstRow the bottom-most row to start with
	 */
	public AutomataImageGenerator(int width, int height, int rule, BitArray firstRow)
	{
		this(width, height, rule);
		this.firstRow.val = firstRow;
	}
	
	public BufferedImage evolveAndGetImage()
	{
		// take the last, ie top-most layer and move it to the bottom/beginning
		this.lastRow.nextNode = this.firstRow;
		this.firstRow = lastRow;
		Automata.evolve(firstRow.nextNode.val, firstRow.val, this.rule);
		BufferedImage result = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
		BitArrayLinkedListNode curRow = this.firstRow;
		for (int row = 0; row < this.height; row++)
		{
			for (int col = 0; col < this.width; col++)
			{
				if (curRow.val.get(col))
				{
					result.setRGB(col, this.height - 1 - row, this.trueColor);
					//System.out.print("#");
				} else
				{
					result.setRGB(col, this.height - 1 - row, this.falseColor);
					//System.out.print(" ");
				}
				//System.out.println("now painting row " + row);
			}
			this.lastRow = curRow; // guarantees that at the end of the loop, lastRow is correct
			curRow = curRow.nextNode;
		}
		this.lastRow.nextNode = null; // not necessary but I like the peace of mind of knowing that the linked list is not unnecessarily circular
		return result;
	}

	public int getWidth()
	{
		return this.width;
	}

	public int getHeight()
	{
		return this.height;
	}
	
	public int getRule()
	{
		return this.rule;
	}
	
	public BitArrayLinkedListNode getFirstRow()
	{
		return this.firstRow;
	}
}
