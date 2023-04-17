import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UlamSpiral {

	public static void main(String[] args) {
		new USFrame();
	}
	
}

@SuppressWarnings("serial")
class USFrame extends JFrame{
	
	USFrame(){
		initialize();
	}
	
	public void initialize() {
		USPanel panel = new USPanel();
		this.add(panel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setTitle("Ulam Spiral");
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
}

@SuppressWarnings("serial")
class USPanel extends JPanel implements ActionListener{
	public static final int SCREEN_WIDTH = 600;
	public static final int SCREEN_HEIGHT = 600;
	public static final int UNIT_SIZE = 2;
	public static final int UNITS_WIDTH = (int)(SCREEN_WIDTH/UNIT_SIZE);
	public static final int UNITS_HEIGHT = (int)(SCREEN_HEIGHT/UNIT_SIZE);
	public static final int TOT_CELLS = UNITS_WIDTH*UNITS_HEIGHT;
	public static final int DELAY = 100;
	boolean running = false;
	Timer timer;
	public static int counter = 0;
	
	
	
	USPanel(){
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		
	}
	
//	public void startSpiral() {
//		running = true;
//		timer = new Timer(DELAY, this);
//		timer.start();
//	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		int seg_len = 1;
		char direction = 'R';
		int curr_len = 0;
		int X = SCREEN_WIDTH/2;
		int Y = SCREEN_HEIGHT/2;
		genPrimes(TOT_CELLS);
		for(int i = 1; i<=TOT_CELLS; i++) {
			System.out.println(direction);
			int newX = X;
			int newY = Y;
			
			switch (direction) {

			case 'L':
				newX = X - UNIT_SIZE;
				newY = Y;
				break;
				
			case 'R':
				newX = X + UNIT_SIZE;
				newY = Y;
				break;
				
			case 'U':
				newX = X;
				newY = Y - UNIT_SIZE;
				break;
				
			case 'D':
				newX = X;
				newY = Y + UNIT_SIZE;
				break;
			}
			g.setColor(Color.gray);
			System.out.println(X + " " + Y);
			System.out.println(newX + " " + newY);
			g.drawLine(X, Y, newX, newY);
			if(isPrime(i)) {
				g.setColor(Color.red);
				//g.fillOval(X, Y, UNIT_SIZE, UNIT_SIZE);
				drawCircle(g, X, Y, UNIT_SIZE/2);
			}
			
			curr_len++;
			
			if(curr_len >= seg_len) {
				curr_len = 0;
				direction = changeDir(direction);
				counter++;
			}
			
			if(counter == 2) {
				counter = 0;
				seg_len++;
			}
			
			X = newX;
			Y = newY;	
		}
		
	}
	
	public static char changeDir(char direction) {
		switch (direction) {

		case 'L':
			direction = 'D';
			break;
			
		case 'R':
			direction = 'U';
			break;
			
		case 'U':
			direction = 'L';
			break;
			
		case 'D':
			direction = 'R';
			break;
			
		default:
			throw new IllegalArgumentException("Invalid direction");
		}
		return direction;
	}
	
	public static void drawCircle(Graphics g, int x, int y, int radius) {
		int diameter = radius * 2;
		//shift x and y by radius to center circle
		g.fillOval(x - radius, y - radius, diameter, diameter); 
	}
	
	
	public static boolean isPrime(int n) {
//		if(n==0 || n==1) {
//			return false;
//		}
//		for(int i=2; i<= Math.sqrt(n); i++) {
//			if(n%i == 0) {
//				return false;
//			}
//		}
//		return true;
		return(!isComp[n]);
	}
	
	public static boolean[] isComp;
	public static void genPrimes(int max) {
		int len = max+2;
		isComp = new boolean[len];
		for(int i=4; i<len; i+=2) {
			isComp[i] = true;
		}
		for(int i=3; i<len; i+=2) {
			if(!isComp[i]) {
				long multiple = i*(long)i;
				int to_add=i*2;
				while(multiple<len) {
					isComp[(int)multiple] = true;
					multiple += to_add;
				}
			}
		}
		isComp[0] = true;
		isComp[1] = true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();	
	}
}
