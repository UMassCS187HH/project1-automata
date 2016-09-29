package automata.conwaysgameoflife;

import javax.swing.JFrame;

public class DisplayUpdateThread extends Thread
{
	private JFrame frame;
	private AutomataDisplayComponent display;
	
	private boolean isPaused = true;
	private boolean stop = false;
	
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
		while (!stop)
		{
			while (true)
			{
				try
				{
					Thread.sleep(200);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				if (!isPaused)
				{
					display.evolve();
					frame.repaint();
				}
			}
		}
	}
	
	public DisplayUpdateThread(JFrame frame, AutomataDisplayComponent display)
	{
		this.frame = frame;
		this.display = display;
	}
	
	/**
	 * Tells the thread to stop in a non-deprecated fashion. Also closes the display window.
	 */
	public void stopThread()
	{
		this.stop = true;
	}
	
	/**
	 * Starts the thread. No need to call unPause() after as with the 1D thread
	 */
	@Override
	public void start()
	{
		super.start();
		this.unPause();
	}
	
	/**
	 * Tells the thread to start after being paused.
	 */
	public void unPause()
	{
		this.isPaused = false;
	}
}
