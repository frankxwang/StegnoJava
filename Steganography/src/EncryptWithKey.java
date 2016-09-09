import java.util.Scanner;
public class EncryptWithKey {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Encrypt encrypt = new Encrypt();
		Scanner in = new Scanner(System.in);
		System.out.println("What is your message?");
		String message = in.nextLine();
		System.out.println("What is your key?");
		int key = in.nextInt();
		String encrypted = encrypt.shift(key, message);
		System.out.println("Your encrypted message is: " + encrypted);
	}
}