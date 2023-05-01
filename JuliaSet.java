import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.Random;

public class JuliaSet {

	public static void main(String[] args) {
		new JuliaFrame();
	}
}

@SuppressWarnings("serial")
class JuliaFrame extends JFrame{
	
	JuliaFrame(){
		initialize();
	}
	
	public void initialize() {
		JuliaPanel panel = new JuliaPanel();
		this.add(panel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setTitle("Julia Set");
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
}

@SuppressWarnings("serial")
class JuliaPanel extends JPanel implements ActionListener{
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 800;
	public static final int UNIT_SIZE = 2;
	public static final int UNITS_WIDTH = (int)(SCREEN_WIDTH/UNIT_SIZE);
	public static final int UNITS_HEIGHT = (int)(SCREEN_HEIGHT/UNIT_SIZE);
	public static final int TOT_CELLS = UNITS_WIDTH*UNITS_HEIGHT;
	public static boolean running = false;
	public final int MAX_ITERATIONS = 50;
	
	private static final double MIN_CX = -1;
	private static final double MAX_CX = 1;
	private static final double MIN_CY = -1;
	private static final double MAX_CY = 1;
	public static Random rand = new Random();
	private double CX = rand.nextDouble() * (MAX_CX - MIN_CX) + MIN_CX;
	private double CY = rand.nextDouble() * (MAX_CY - MIN_CY) + MIN_CY;
	public double ZOOM = 0.75;
	
	JuliaPanel(){
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.white);
		this.setFocusable(true);
		System.out.println(CX + " " + CY);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
    public void draw(Graphics g) {
        //super.paint(g);
        for (int x = 0; x < SCREEN_WIDTH; x++) {
            for (int y = 0; y < SCREEN_HEIGHT; y++) {
                double zx = 1.5 * (x - SCREEN_WIDTH/2.0) / (0.5 * ZOOM * SCREEN_WIDTH);
                double zy = (y - SCREEN_HEIGHT/2.0) / (0.5 * ZOOM * SCREEN_HEIGHT);
                int iter = 0;
                while (zx*zx + zy*zy < 4.0 && iter < MAX_ITERATIONS) {
                    double tmp = zx*zx - zy*zy + CX;
                    zy = 2.0 * zx * zy + CY;
                    zx = tmp;
                    iter++;
                }
                if (iter >= MAX_ITERATIONS) {
                    g.setColor(Color.BLUE);
                } else {
                    g.setColor(new Color(0, 0, iter * 255 / MAX_ITERATIONS));
                }
                drawCircle(g, x, y, UNIT_SIZE);
            }
        }
    }
    
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