package automata.conwaysgameoflife;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public class SetupFrame extends JFrame implements WindowListener
{
	private JLabel headerLabel = new JLabel("Set Up Conway's Game of Life"),
				   widthLabel = new JLabel("Width: "),
				   heightLabel = new JLabel("Height: "),
				   cellSizeLabel = new JLabel("Cell size: ");
	private JSpinner widthSpinner = new JSpinner(new SpinnerNumberModel(100, 3, 1000000, 1)),
					 heightSpinner = new JSpinner(new SpinnerNumberModel(100, 3, 1000000, 1)),
					 cellSizeSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 1000000, 1));
	private JButton startButton = new JButton("Start"),
					pauseButton = new JButton("Pause"),
					stopButton = new JButton("Stop");
	private JPanel contentPane = new JPanel((LayoutManager) new FlowLayout(FlowLayout.LEFT)),
				   widthPanel = new JPanel((LayoutManager) new FlowLayout(FlowLayout.LEFT)),
				   heightPanel = new JPanel((LayoutManager) new FlowLayout(FlowLayout.LEFT)),
				   cellSizePanel = new JPanel((LayoutManager) new FlowLayout(FlowLayout.LEFT));
	
	private JFrame displayFrame = null;
	private AutomataDisplayComponent display = null;
	private DisplayUpdateThread displayUpdateThread = null;
	public SetupFrame()
	{
		super();
		this.setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		contentPane.add(headerLabel);
		
		widthPanel.add(widthLabel);
		widthPanel.add(widthSpinner);
		widthPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		contentPane.add(widthPanel);
		
		heightPanel.add(heightLabel);
		heightPanel.add(heightSpinner);
		heightPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		contentPane.add(heightPanel);
		
		cellSizePanel.add(cellSizeLabel);
		cellSizePanel.add(cellSizeSpinner);
		cellSizePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		contentPane.add(cellSizePanel);
		
		startButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		pauseButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		stopButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		pauseButton.setEnabled(false);
		stopButton.setEnabled(false);
		startButton.addActionListener(new StartButtonActionListener());
		pauseButton.addActionListener(new PauseButtonActionListener());
		stopButton.addActionListener(new StopButtonActionListener());
		contentPane.add(startButton);
		contentPane.add(pauseButton);
		contentPane.add(stopButton);
		
		this.addWindowListener(this);
		this.setSize(250, 250);
		this.setTitle("Conway's Game of Life");
		this.setVisible(true);
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
			if (displayUpdateThread == null)
			{
				// Set up a simple starting board
				int boardWidth = ((SpinnerNumberModel)widthSpinner.getModel()).getNumber().intValue();
				int boardHeight = ((SpinnerNumberModel)heightSpinner.getModel()).getNumber().intValue();
				int cellSize = ((SpinnerNumberModel)cellSizeSpinner.getModel()).getNumber().intValue();
				BitArray2d board = new BitArray2d(boardWidth, boardHeight);
				board.set(10, 10, true);
				board.set(10, 11, true);
				board.set(10, 12, true);
				board.set(9, 12, true);
				board.set(8, 11, true);
				
				// set up important variables
				displayFrame = new JFrame("Conway's Game of Life");
				display = new AutomataDisplayComponent(board, cellSize);
				displayUpdateThread = new DisplayUpdateThread(displayFrame, display);
				
				// set up components used to display the stuff
				JPanel contentPane = new JPanel();
				JScrollPane scrollPane = new JScrollPane(contentPane);
				scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				displayFrame.getContentPane().setLayout(new BorderLayout());
				displayFrame.getContentPane().add(scrollPane);
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
				contentPane.add(display);
				
				// let's go!
				displayFrame.setVisible(true);
				displayFrame.pack();
				displayUpdateThread.start();
			} else
			{
				displayUpdateThread.unPause();
			}
			
			// enable/disable various buttons
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
