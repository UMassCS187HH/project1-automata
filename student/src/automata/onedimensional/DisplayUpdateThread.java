package automata.onedimensional;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class DisplayUpdateThread extends Thread
{
	private ImageDisplayPane contentPane;
	private JFrame frame;
	private AutomataImageGenerator generator;
	
	private boolean isPaused = true;
	private boolean stop = false;
	
	public DisplayUpdateThread(ImageDisplayPane contentPane, JFrame frame, AutomataImageGenerator generator)
	{
		this.contentPane = contentPane;
		this.frame = frame;
		this.generator = generator;
	}
	
	/**
	 * Tells the Thread to temporarily stop executing. Can be restarted with
	 * the unPause() method.
	 */
	public void pause()
	{
		this.isPaused = true;
	}
	
	/**
	 * The method that the Thread runs.
	 */
	@Override
	public void run()
	{
		while (!stop)
		{
			if (!isPaused)
			{
				BufferedImage img = generator.evolveAndGetImage();
				contentPane.setImage(img);
				frame.repaint();
			}
			try
			{
				Thread.sleep(50);
			} catch (InterruptedException e)
			{
				e.printStackTrace(); // shouldn't happen
			}
		}
	}
	
	/**
	 * Tells the thread to stop in a non-deprecated fashion. Also closes the display window.
	 */
	public void stopThread()
	{
		this.stop = true;
	}
	
	/**
	 * Tells the thread to start. Must be run after running start() to actually
	 * start the Thread.
	 */
	public void unPause()
	{
		this.isPaused = false;
	}
}
