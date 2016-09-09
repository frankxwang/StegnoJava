import java.util.Scanner;
public class EncryptMain {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		Encrypt encrypt = new Encrypt();
		System.out.println("What is the agreed exponent and modulus?(exponent,modulus)");
		String[] vals = in.nextLine().split(",");
		int base = new Integer(vals[0]);
		int mod = new Integer(vals[1]);
		System.out.println("What is your private number?");
		int num = in.nextInt();
		int powmod = encrypt.powMod(base, num, mod);
		System.out.println("Now send the number " + powmod + " to the other person");
		System.out.println("What number did you get for the other person?");
		int getNum = in.nextInt();
		int key = encrypt.powMod(getNum, num, mod);
		System.out.println("Your shared secret key is " + key);
		
		System.out.println("What is your message?");
		in.nextLine();//need this for some reason
		String message1 = in.nextLine();
		String encrypted = encrypt.shift(key, message1);
		System.out.println("Your encrypted message is: " + encrypted);
	}
}