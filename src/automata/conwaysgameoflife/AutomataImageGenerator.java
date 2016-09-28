package automata.conwaysgameoflife;

import java.awt.image.BufferedImage;

/**
 * Generates images of 2d automata.
 */
public class AutomataImageGenerator
{
	private BitArray2d world, temp;
	
	private int pixelsPerCell = 1;
	
	private int aliveColor = -1 /* white */, deadColor = 0 /* black */; 
	
	public AutomataImageGenerator(BitArray2d startingWorld)
	{
		this.world = startingWorld;
		this.temp = new BitArray2d(world.getWidth(), world.getHeight());
	}
	
	public BufferedImage evolveAndGetImage()
	{
		BitArray2d.evolve(world, temp);
		this.world = temp;
		BufferedImage img = new BufferedImage(world.getWidth() * pixelsPerCell, world.getHeight() * pixelsPerCell, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < world.getWidth(); x++)
		{
			for (int y = 0; y < world.getHeight(); y++)
			{
				if (world.get(x, y))
				{
					paintRect(img, this.aliveColor, x*pixelsPerCell, y*pixelsPerCell, (x+1)*pixelsPerCell-1, (y+1)*pixelsPerCell-1);
				} else
				{
					paintRect(img, this.deadColor, x*pixelsPerCell, y*pixelsPerCell, (x+1)*pixelsPerCell-1, (y+1)*pixelsPerCell-1);
				}
			}
		}
		return img;
	}
	
	private static void paintRect(BufferedImage img, int color, int x1, int y1, int x2, int y2)
	{
		for (int x = x1; x <= x2; x++)
		{
			for (int y = y1; y <= y2; y++)
			{
				img.setRGB(x, y, color);
			}
		}
	}
	
	/****Getters and Setters*****/
	
	/**
	 * Gets the color that the live cells will be drawn with
	 * @return the color of the live cells
	 */
	public int getAliveColor()
	{
		return aliveColor;
	}
	
	/**
	 * Sets the color that the dead live will be drawn with
	 * @param aliveColor the color to draw the live cells with
	 */
	public void setAliveColor(int aliveColor)
	{
		this.aliveColor = aliveColor;
	}
	
	/**
	 * Gets the color that the dead cells will be drawn with
	 * @return the color of the dead cells
	 */
	public int getDeadColor()
	{
		return deadColor;
	}
	
	/**
	 * Sets the color that the dead live will be drawn with
	 * @param aliveColor the color to draw the dead cells with
	 */
	public void setDeadColor(int deadColor)
	{
		this.deadColor = deadColor;
	}

	/**
	 * Gets the dimension of the cells, how many pixels wide/tall they will be in the final image
	 * @return the pixel width/height of each cell in the image
	 */
	public int getPixelsPerCell()
	{
		return pixelsPerCell;
	}
	
	/**
	 * Sets the size of the cells in the image
	 * @param pixelsPerCell pixel dimension of the cells in the image
	 */
	public void setPixelsPerCell(int pixelsPerCell)
	{
		this.pixelsPerCell = pixelsPerCell;
	}
}
