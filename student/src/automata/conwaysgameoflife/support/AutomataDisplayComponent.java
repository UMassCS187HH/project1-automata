package automata.conwaysgameoflife.support;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import automata.conwaysgameoflife.src.BitArray2d;

@SuppressWarnings("serial")
public class AutomataDisplayComponent extends JComponent
{
	private BitArray2d board, copy;
	private int cellSize;
	private Color deadColor = Color.WHITE, liveColor = Color.BLACK;
	private Image imgToDraw;
	
	/**
	 * Creates a new AutomataDisplayPane with the given board and pixel size
	 * @param board the game board to start out with
	 * @param cellSize how wide and tall the cells should be
	 */
	public AutomataDisplayComponent(BitArray2d board_, int cellSize)
	{
		super();
		this.board = board_;
		this.copy = new BitArray2d(board.getWidth(), board.getHeight());
		this.cellSize = cellSize;
		this.setPreferredSize(new Dimension(board.getWidth() * cellSize, board.getHeight() * cellSize));
		this.addMouseListener
		(
			new MouseListener()
			{	@Override public void mouseClicked(MouseEvent arg0) { }
				@Override public void mouseEntered(MouseEvent arg0) { }
				@Override public void mouseExited(MouseEvent arg0) { }
				@Override public void mousePressed(MouseEvent arg0)
				{
					int cellX = arg0.getX() / AutomataDisplayComponent.this.cellSize;
					int cellY = arg0.getY() / AutomataDisplayComponent.this.cellSize;
					if (cellX > 0 && cellY > 0 && cellX < board.getWidth() - 1 && cellY < board.getHeight() - 1)
					{
						board.set(cellX, cellY, !board.get(cellX, cellY));
						setImageToDraw(AutomataDisplayComponent.this.getBufferedImage());
						AutomataDisplayComponent.this.repaint();
					}
				}
				@Override public void mouseReleased(MouseEvent arg0) { }
			}
		);
	}
	
	/**
	 * Called in order to evolve the current game board in its next state. A
	 * good idea to call it each time you call repaint().
	 */
	public void evolve()
	{
		BitArray2d.evolve(board, copy);
		BitArray2d temp = board;
		this.board = copy;
		this.copy = temp;
	}
	
	/**
	 * Draws an image of the board and returns it
	 * @return a BufferedImage that is the board
	 */
	public BufferedImage getBufferedImage()
	{
		BufferedImage img = new BufferedImage(board.getWidth() * cellSize, board.getHeight() * cellSize, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		if (this.getWidth() != board.getWidth() * cellSize || this.getHeight() != board.getHeight() * cellSize)
		{
			System.err.println("AutoamtaDisplayPane is not the right size to draw itself. Current size is: " + this.getWidth() + "x" + this.getHeight() + ", but I need to be size " + (board.getWidth() * cellSize) + "x" + (board.getHeight() * cellSize));
		}
		for (int x = 0; x < board.getWidth(); x++)
		{
			for (int y = 0; y < board.getHeight(); y++)
			{
				if (board.get(x, y))
				{
					g.setColor(liveColor);
				} else
				{
					g.setColor(deadColor);
				}
				g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
			}
		}
		return img;
	}
	
	@Override public void paint(Graphics g)
	{
		super.paint(g);
		g.drawImage(this.imgToDraw, 0, 0, null);
		
	}
	
	/***** GETTERS AND SETTERS *****/
	public BitArray2d getBoard()
	{
		return this.board;
	}
	
	public void setBoard(BitArray2d board)
	{
		this.board = board;
	}
	
	public int getCellSize()
	{
		return this.cellSize;
	}
	
	public void setCellSize(int cellSize)
	{
		this.cellSize = cellSize;
		this.setPreferredSize(new Dimension(cellSize * this.board.getWidth(), cellSize * this.board.getHeight()));
	}
	
	public Color getDeadColor()
	{
		return this.deadColor;
	}
	
	public void setDeadColor(Color color)
	{
		this.deadColor = color;
	}
	
	/**
	 * Called in order to set the image that this component should draw
	 * @param img the Image to draw
	 */
	public void setImageToDraw(Image img)
	{
		this.imgToDraw = img;
	}
	
	public Color getLiveColor()
	{
		return this.liveColor;
	}
	public void setLiveColor(Color color)
	{
		this.deadColor = color;
	}
}
