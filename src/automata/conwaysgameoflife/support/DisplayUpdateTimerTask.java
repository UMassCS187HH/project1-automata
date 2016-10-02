package automata.conwaysgameoflife.support;

import java.util.TimerTask;

public class DisplayUpdateTimerTask extends TimerTask
{
	private AutomataDisplayComponent display;
	
	private boolean isPaused = false;
	
	public DisplayUpdateTimerTask(AutomataDisplayComponent display)
	{
		this.display = display;
	}

	/**
	 * Tells the Thread to temporarily stop executing. Can be restarted with
	 * the unPause() method.
	 */
	public void pause()
	{
		this.isPaused = true;
	}
	
	@Override
	public void run()
	{
		display.repaint();
		if (!isPaused)
		{
			display.evolve();
			display.setImageToDraw(display.getBufferedImage());
		}
	}
	
	/**
	 * Tells the thread to start after being paused.
	 */
	public void unPause()
	{
		this.isPaused = false;
	}
}
