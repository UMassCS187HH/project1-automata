import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.LayoutManager;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class SetupFrame extends JFrame implements WindowListener
{
	// Component variables
	private JLabel headerLabel = new JLabel("Set Up Automata"),
				   widthLabel = new JLabel("Width: "),
				   heightLabel = new JLabel("Height: "),
				   ruleLabel = new JLabel("Rule: ");
	private JCheckBox fullScreenCheckBox = new JCheckBox("full screen mode (press esc to close)");
	private JSpinner widthSpinner = new JSpinner(new SpinnerNumberModel(1201, 1, 1000000, 1)),
					 heightSpinner = new JSpinner(new SpinnerNumberModel(600, 1, 1000000, 1)),
					 ruleSpinner = new JSpinner(new SpinnerNumberModel(90, 0, 255, 1));
	private JButton startButton = new JButton("Start"),
					pauseButton = new JButton("Pause"),
					stopButton = new JButton("Stop");
	private JPanel contentPane = new JPanel(),
				   widthPane = new JPanel((LayoutManager) new FlowLayout(FlowLayout.LEFT)),
				   heightPane = new JPanel((LayoutManager) new FlowLayout(FlowLayout.LEFT)),
				   rulePane = new JPanel((LayoutManager) new FlowLayout(FlowLayout.LEFT));
	
	// variables for rendering etc
	private ImageDisplayPane imageDisplayPane;
	private JFrame displayFrame;
	private AutomataImageGenerator generator;
	private DisplayUpdateThread displayUpdateThread;
	
	public SetupFrame()
	{
		// Add the gazillions of panes and components
		this.setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		contentPane.add(headerLabel);
		
		widthPane.add(widthLabel);
		widthPane.add(widthSpinner);
		widthPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		contentPane.add(widthPane);
		
		heightPane.add(heightLabel);
		heightPane.add(heightSpinner);
		heightPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		contentPane.add(heightPane);
		
		contentPane.add(fullScreenCheckBox);
		
		rulePane.add(ruleLabel);
		rulePane.add(ruleSpinner);
		rulePane.setAlignmentX(Component.LEFT_ALIGNMENT);
		contentPane.add(rulePane);
		
		startButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		pauseButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		stopButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		startButton.addActionListener(new StartButtonActionListener());
		pauseButton.addActionListener(new PauseButtonActionListener());
		stopButton.addActionListener(new StopButtonActionListener());
		pauseButton.setEnabled(false);
		stopButton.setEnabled(false);
		contentPane.add(startButton);
		contentPane.add(pauseButton);
		contentPane.add(stopButton);
		
		this.addWindowListener(this);
		this.setTitle("Automata");
		this.setSize(300, 240);
		this.setVisible(true);
	}
	
	/**
	 * Returns whether or not the display window is in full screen mode or
	 * should be upon launching. Ie, whether or not the full screen checkbox is
	 * checked.
	 * @return whether or not the full screen checkbox is checked.
	 */
	private boolean isFullScreen()
	{
		return this.fullScreenCheckBox.isSelected();
	}
	
	/**
	 * To be called when one wants to permanently stop displaying that specific
	 * instance of a cellular automaton and close permanently the display
	 * window. Resets several variables and enables/disables buttons and stuff
	 * @throws NullPointerException if there is currently no display Frame
	 * showing a cellular automaton
	 */
	private void killDisplayFrame()
	{
		this.displayUpdateThread.stopThread();
		this.displayFrame.setVisible(false);
		this.displayFrame.dispose();
		this.displayUpdateThread = null;
		
		this.fullScreenCheckBox.setEnabled(true);
		this.stopButton.setEnabled(false);
		this.pauseButton.setEnabled(false);
		this.startButton.setEnabled(true);
	}
	
	// methods for WindowListener for this frame
	@Override public void windowActivated(WindowEvent e) { }
	@Override public void windowClosed(WindowEvent e) { }
	@Override public void windowClosing(WindowEvent e) { System.exit(0); }
	@Override public void windowDeactivated(WindowEvent e) { }
	@Override public void windowDeiconified(WindowEvent e) { }
	@Override public void windowIconified(WindowEvent e) { }
	@Override public void windowOpened(WindowEvent e) { }
	
	private class StartButtonActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			if (displayUpdateThread == null) // if we're starting the display from scratch, not just unpausing it
			{
				// set up the window/imageDisplayPane/automataGenerator
				imageDisplayPane = new ImageDisplayPane(null);
				displayFrame = new JFrame();
				displayFrame.setContentPane(imageDisplayPane);
				displayFrame.addWindowListener
				(
					new WindowListener()
					{   @Override public void windowActivated(WindowEvent e) { }
						@Override public void windowClosed(WindowEvent e) { }
						@Override public void windowClosing(WindowEvent e) { killDisplayFrame(); }
						@Override public void windowDeactivated(WindowEvent e) { }
						@Override public void windowDeiconified(WindowEvent e) { }
						@Override public void windowIconified(WindowEvent e) { }
						@Override public void windowOpened(WindowEvent e) { }
					}
				);
				if (isFullScreen())
				{
					// makes the window full screen. Will probably do very strange things on multimonitor setups
					// no idea how those next few lines work, got them off SO
					// http://stackoverflow.com/questions/7456227/how-to-handle-events-from-keyboard-and-mouse-in-full-screen-exclusive-mode-in-ja/7457102#7457102
					GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
				    GraphicsDevice dev = env.getDefaultScreenDevice();
				    dev.setFullScreenWindow(displayFrame);
				    
				    // set up rest of generator stuff
					int imageWidth = dev.getDisplayMode().getWidth();
					int imageHeight = dev.getDisplayMode().getHeight();
					BitArray firstRowArray = new BitArray(imageWidth);
					firstRowArray.set(firstRowArray.getSize() / 2, true);
					generator = new AutomataImageGenerator
					(
								imageWidth,
								imageHeight,
								((SpinnerNumberModel)ruleSpinner.getModel()).getNumber().intValue(),
								firstRowArray
					);
					
					// add key listener that closes it on escape so they can quit in full screen mode
					displayFrame.addKeyListener
					(
						new KeyListener()
						{	@Override public void keyPressed(KeyEvent arg0) { }
							@Override public void keyReleased(KeyEvent keyEvent)
							{
								System.out.println("Key pressed, code " + keyEvent.getKeyCode());
								if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE)
								{
									killDisplayFrame();
								}
							}
							@Override public void keyTyped(KeyEvent arg0) { }
						}
					);
				} else
				{
					int imageWidth = ((SpinnerNumberModel)widthSpinner.getModel()).getNumber().intValue();
					int imageHeight = ((SpinnerNumberModel)heightSpinner.getModel()).getNumber().intValue();
					BitArray firstRowArray = new BitArray(((SpinnerNumberModel)widthSpinner.getModel()).getNumber().intValue());
					firstRowArray.set(firstRowArray.getSize() / 2, true);
					imageDisplayPane.setPreferredSize
					(new Dimension(
							((SpinnerNumberModel)widthSpinner.getModel()).getNumber().intValue(),
							((SpinnerNumberModel)heightSpinner.getModel()).getNumber().intValue()
					));
					displayFrame.pack();
					generator = new AutomataImageGenerator
					(
						imageWidth,
						imageHeight,
						((SpinnerNumberModel)ruleSpinner.getModel()).getNumber().intValue(), // rule number
						firstRowArray
					);
				}
				displayFrame.setVisible(true);
				displayUpdateThread = new DisplayUpdateThread(imageDisplayPane, displayFrame, generator);
				displayUpdateThread.start();
			}
			displayUpdateThread.unPause();
			
			// enable/disable stuff
			fullScreenCheckBox.setEnabled(false);
			startButton.setEnabled(false);
			stopButton.setEnabled(true);
			pauseButton.setEnabled(true);
		}
	}
	private class PauseButtonActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (displayUpdateThread != null)
			{
				displayUpdateThread.pause();
				startButton.setEnabled(true);
				pauseButton.setEnabled(false);
			}
		}
	}
	private class StopButtonActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (SetupFrame.this.displayUpdateThread != null)
			{
				SetupFrame.this.killDisplayFrame();
			}
		}
	}
}
