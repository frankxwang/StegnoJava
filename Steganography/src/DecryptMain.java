import java.util.Scanner;
public class DecryptMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		Decrypt decrypt = new Decrypt();
		System.out.println("What message do you want to decrypt?");
		String encrypted = in.nextLine();
		System.out.println("What is the key?");
		int key = in.nextInt();
		String decrypted = decrypt.decrypt(key, encrypted);
		System.out.println("Your decrypted message is: " + decrypted);
	}
}
