
import java.util.Scanner;
import java.math.BigInteger;


public class SimpleComputing {
	
	
	public static void main (String args[])
	{
		int max=0;
		System.out.println("Podaj a");
		Scanner sc = new Scanner(System.in);
		max = sc.nextInt(); 
		try {
			
			for (int i=0; i<=max; i++)
			{
				System.out.println("Ciag Fibonacciego dla "+i+" = "+fibonacciIter(i));
			}
			
		} catch (NumberFormatException e) {
			System.out.println("Blad! To nie jest liczba ca³kowita"); 
		}
		
		
	}

	private static int silnia(int x) {
		int y = 1;
		for(int i=2;i <= x;i++) {
			y *= i;
		}
		return y;
	}
	
	private static long silnia2(int x) {
		long y = 1;
		for(long i=2;i <= x;i++) {
			y *= i;
		}
		return y;
	}
	
	private static BigInteger silnia3(int x) {
		BigInteger y = new BigInteger("1");
		for (long i = 2;i <= x;i++) {
			y = y.multiply(new BigInteger(i+""));
		}
		return y;
		
	}
	
	private static long silnia4(int x) {
		if (x <= 1) return 1;
		else return silnia4(x - 1) * x;
	}

	private static long fibonacci(int x) {
		if (x <= 2) return 1;
		else return fibonacci(x-2) + fibonacci(x - 1);
	}

	private static long fibonacciIter(int z) {
		int x = 1;
		int y = 1;
		for (int i = 3; i <= z; i++) {
			int a = y;
			y += x;
			x = a;
		}
		return y;
	}
}