import java.util.Random;
public class Encrypt {
	public int powMod(int b, int e, int m){
		return (int) (Math.pow(b, e) % m);
	}
	public String shift(int seed, String message){
		char[] messageArray = message.toCharArray();
		int[] shifts;
		shifts = new int[messageArray.length];
		Random vals = new Random(seed);
		for(int i=0; i<shifts.length; i++){
			shifts[i] = vals.nextInt(96);
		}
		for(int i=0; i<shifts.length; i++){
			int charNum = (messageArray[i] + shifts[i]);
			if(charNum>126){
				charNum-=95;
			}
			messageArray[i] = (char)charNum;
		}
		return new String(messageArray);
	}
}