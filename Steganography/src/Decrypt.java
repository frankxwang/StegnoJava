import java.util.Random;
public class Decrypt {
	public String decrypt(int seed, String e){
		char[] messageArray = e.toCharArray();
		int[] shifts;
		shifts = new int[messageArray.length];
		Random vals = new Random(seed);
		for(int i=0; i<shifts.length; i++){
			shifts[i] = vals.nextInt(96);
		}
		for(int i=0; i<shifts.length; i++){
			int charNum = (messageArray[i] - shifts[i]) % 126;
			if(charNum<32){
				charNum+=95;
			}
			messageArray[i] = (char)charNum;
		}
		return new String(messageArray);
	}
}
