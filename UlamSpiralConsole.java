import java.util.Scanner;

public class UlamSpiralConsole {

	public static boolean[] isComp;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter a positive value: ");
		int max = sc.nextInt();
		sc.close();
		genPrimes(max);
		for(int i=1; i<isComp.length; i++) {
			if(isComp[i]) {
				System.out.print(" ");
			}
			else {
				System.out.print("*");
			}
		}
		System.out.println("|");

	}
	
	public static void genPrimes(int max) {
		int len = max+1;
		isComp = new boolean[len];
		for(int i=4; i<len; i+=2) {
			isComp[i] = true;
		}
		for(int i=3; i<len; i+=2) {
			if(!isComp[i]) {
				long multiple = i*(long)i;
				while(multiple<len) {
					isComp[(int)multiple] = true;
					multiple += i*2;
				}
			}
		}
		isComp[0] = true;
		isComp[1] = true;
	}
	
	public static void printPrimes() {
		for(int i=0; i<isComp.length; i++) {
			if(!isComp[i]) {
				System.out.println(i + " ");
			}
		}
	}

}
