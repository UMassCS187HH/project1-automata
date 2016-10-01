package automata.conwaysgameoflife;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JPanel;
import javax.swing.Scrollable;

@SuppressWarnings("serial")
public class ScrollContentPane extends JPanel implements Scrollable
{
	private int scrollIncrement = 1;
	
	public int getScrollIncrement()
	{
		return this.scrollIncrement;
	}
	
	public void setScrollIncrement(int scrollIncrement)
	{
		this.scrollIncrement = scrollIncrement;
	}
	
	public ScrollContentPane(BorderLayout borderLayout)
	{
		super(borderLayout);
	}

	@Override
	public Dimension getPreferredScrollableViewportSize()
	{
		return this.getPreferredSize();
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle arg0, int arg1, int arg2)
	{
		return this.scrollIncrement;
	}

	@Override
	public boolean getScrollableTracksViewportHeight()
	{
		return false;
	}

	@Override
	public boolean getScrollableTracksViewportWidth()
	{
		return false;
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle arg0, int arg1, int arg2)
	{
		return this.scrollIncrement;
	}
}
