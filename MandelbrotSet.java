import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MandelbrotSet {

	public static void main(String[] args) {
		new MBFrame();
	}
}

@SuppressWarnings("serial")
class MBFrame extends JFrame{
	
	MBFrame(){
		initialize();
	}
	
	public void initialize() {
		MBPanel panel = new MBPanel();
		this.add(panel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setTitle("Mandelbrot Set");
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
}


@SuppressWarnings("serial")
class MBPanel extends JPanel implements ActionListener{
	public static final int SCREEN_WIDTH = 600;
	public static final int SCREEN_HEIGHT = 600;
	public static final int UNIT_SIZE = 2;
	public static final int UNITS_WIDTH = (int)(SCREEN_WIDTH/UNIT_SIZE);
	public static final int UNITS_HEIGHT = (int)(SCREEN_HEIGHT/UNIT_SIZE);
	public static final int TOT_CELLS = UNITS_WIDTH*UNITS_HEIGHT;
	public static final int DELAY = 100;
	boolean running = false;
	Timer timer;
	public final int MAX_ITERATIONS = 50;
	
	MBPanel(){
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		for(int i=0; i<SCREEN_WIDTH; i++) {
			for(int j=0; j<SCREEN_HEIGHT; j++) {
				double a0 = mapPixelToValue(i, SCREEN_WIDTH, -2.25, +0.75);
				double b0 = mapPixelToValue(j, SCREEN_WIDTH, -1.5, +1.5);
				double a = 0;
				double b = 0;
				int iteration = 0;
				while(iteration < MAX_ITERATIONS) {
					double new_a = a*a - b*b + a0;
					double new_b = 2*a*b + b0;
					a = new_a;
					b = new_b;
					iteration++;
					if(Math.abs(a*a + b*b) > 16) {
						break;
					}
				}
				
				int value = (int)mapPixelToValue(iteration, MAX_ITERATIONS, 0, 255);
				Color myColor = new Color(0, 0, value);
				g.setColor(myColor);
				//g.drawLine(i, j, i, j);
				drawCircle(g, i, j, 1);
				//g.drawRect(i, j, 1, 1);
				
			}
		}
	}
	
	public double mapPixelToValue(int a, int width, double lower_bound, double upper_bound) {
		if(lower_bound>upper_bound) {
			throw new IllegalArgumentException("Invalid bounds");
		}
		double proportion = a/(double)width;
		double range = Math.abs(upper_bound-lower_bound);
		double res = lower_bound + proportion*range;
		return res;
	}
	
//	public double map_b(int b, int height, int up, int right) {
//		if(left>right) {
//			throw new IllegalArgumentException("Invalid bounds");
//		}
//		double proportion = a/(double)width;
//		double range = Math.abs(right-left);
//		double res = left + proportion*range;
//		return res;
//	}
	
	public static void drawCircle(Graphics g, int x, int y, int radius) {
		int diameter = radius * 2;
		//shift x and y by radius to center circle
		g.fillOval(x - radius, y - radius, diameter, diameter); 
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();	
	}

}
