package automata.conwaysgameoflife;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class AutomataDisplayComponent extends JComponent
{
	private BitArray2d board, copy;
	private int cellSize;
	private Color deadColor = Color.WHITE, liveColor = Color.BLACK;
	
	/**
	 * Creates a new AutomataDisplayPane with the given board and pixel size
	 * @param board the game board to start out with
	 * @param cellSize how wide and tall the cells should be
	 */
	public AutomataDisplayComponent(BitArray2d board, int cellSize)
	{
		super();
		this.board = board;
		this.copy = new BitArray2d(board.getWidth(), board.getHeight());
		this.cellSize = cellSize;
		this.setPreferredSize(new Dimension(board.getWidth() * cellSize, board.getHeight() * cellSize));
		this.addMouseListener
		(
			new MouseListener()
			{	@Override public void mouseClicked(MouseEvent arg0)
				{
					// TODO do this
				}
				@Override public void mouseEntered(MouseEvent arg0) { }
				@Override public void mouseExited(MouseEvent arg0) { }
				@Override public void mousePressed(MouseEvent arg0) { }
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
	
	@Override public void paint(Graphics g)
	{
		super.paint(g);
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
	
	public Color getDeadColor()
	{
		return this.deadColor;
	}
	
	public void setDeadColor(Color color)
	{
		this.deadColor = color;
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
