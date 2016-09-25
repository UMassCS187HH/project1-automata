import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImageDisplayPane extends JPanel
{
	private BufferedImage img;
	
	public ImageDisplayPane(BufferedImage img)
	{
		this.img = img;
	}
	
	public BufferedImage getImage()
	{
		return this.img;
	}
	
	public void setImage(BufferedImage img)
	{
		this.img = img;
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		if (this.img != null)
		{
			g.drawImage(this.img, 0, 0, null);
		}
	}
}
