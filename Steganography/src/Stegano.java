import java.awt.Color;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
public class Stegano {
	public static String path = "/Users/frank/git/StegnoGit/Steganography/";
	public byte[] toBytes(String fileName){
		byte[] b = null;
		try {
	        BufferedImage originalImage = ImageIO.read(new File(
	                path + fileName));

	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        ImageIO.write(originalImage, "png", baos);
	        baos.flush();
	        b = baos.toByteArray();
	        baos.close();

	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	public void toImage(byte[] b, String fileName){
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(b);
			BufferedImage newImage = ImageIO.read(in);

			ImageIO.write(newImage, "png", new File(
			        path + fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private void encode(int key, String fileName, String fileName2, String s){
		try {
			Encrypt e = new Encrypt();
			s = e.shift(key, s);
			BufferedImage image = ImageIO.read(new File(
			        path + fileName));
			int row = 0;
			char[] chars = s.toCharArray();
			byte[] add = new byte[chars.length];
			for(int i=0; i<add.length; i++){
				add[i] = (byte)chars[i];
			}
			for(int i=1; i<chars.length*8; i+=8){
				int b2Int = Integer.valueOf((Integer.toBinaryString(add[i/8])));
				String b2 = String.format("%08d", b2Int);
				for(int j=0; j<8; j++){
					int bit = Integer.valueOf(String.valueOf(b2.charAt(j)));
					int x = (i+j) % image.getWidth();
					if(x == 0 && row < image.getHeight()-1){
						x = 0;
						row++;
//						System.out.println(x + " " + row);
					}else if(row >= image.getHeight()-1){
						System.out.println("Too long");
						throw new Exception("Too long");
					}
//					System.out.println(x + " " + row);
					int rgb = image.getRGB(x, row);
//					Color c = new Color(rgb);
					
//					c = new Color(c.getAlpha(), c.getRed(), c.getGreen()-c.getGreen()%2-bit, c.getBlue());
//					System.out.println(c.getGreen());
					image.setRGB(x, row, ((rgb-(rgb%2)) - bit));
//					rgb = rgb - (Math.abs(rgb%2));
//					image.setRGB(x, row, addRGB(rgb, 0, -bit, 0, 0));
					System.out.print((((rgb-(rgb%2)) - bit)) + " ");
				}
			}
			System.out.println();
			ImageIO.write(image, "png", new File(
			        path + fileName2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private String decode(int seed, String fileName){
		String word = "";
		try {
			BufferedImage image = ImageIO.read(new File(
			        path + fileName));
			ArrayList bits = new ArrayList<Integer>();
			int row = 0;
			for(int i=1; i<image.getWidth()*image.getHeight()/8; i+=8){
				for(int j=0; j<8; j++){
					int x = (i+j) % image.getWidth();
					if(x == 0 && row < image.getHeight()-1){
						x = 0;
						row++;
//						System.out.println(x + " " + row);
					}else if(row >= image.getHeight()-1){
						System.out.println("Too long");
						throw new Exception("Too long");
					}
//					System.out.println(x + " " + row);
//					Color c = new Color(image.getRGB(x, row));
//					int b = c.getBlue();
					int rgb = image.getRGB(x, row);
					System.out.print(rgb + " ");
					bits.add(Math.abs(rgb%2));
				}
			}
			Iterator it = bits.iterator();
			while(it.hasNext()){
				String charNum = "";
				for(int i=0; i<8; i++){
					charNum += it.next();
				}
				int charInt = Integer.valueOf(charNum, 2);
				word += (char)charInt;
			}
			Decrypt d = new Decrypt();
			word = d.decrypt(seed, word);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return word;
	}
	private int colorToRGB(int alpha, int red, int green, int blue) {
        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red;
        newPixel = newPixel << 8;
        newPixel += green;
        newPixel = newPixel << 8;
        newPixel += blue;

        return newPixel;
    }

	private int addRGB(int rgb, int alpha, int red, int green, int blue) {
        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red;
        newPixel = newPixel << 8;
        newPixel += green;
        newPixel = newPixel << 8;
        newPixel += blue;

        return newPixel + rgb;
    }
	
	public static void main(String[] args) {
		Stegano s = new Stegano();
//		byte[] b = s.toBytes("file.png");
		
		s.encode(10, "file.png", "file2.png", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio. Praesent libero. Sed cursus ante dapibus diam. Sed nisi. Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. Praesent mauris. Fusce nec tellus sed augue semper porta. Mauris massa. Vestibulum lacinia arcu eget nulla. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Curabitur sodales ligula in libero. Sed dignissim lacinia nunc. Curabitur tortor. Pellentesque nibh. Aenean quam. In scelerisque sem at dolor. Maecenas mattis. Sed convallis tristique sem. Proin ut ligula vel nunc egestas porttitor. Morbi lectus risus, iaculis vel, suscipit quis, luctus non, massa. Fusce ac turpis quis ligula lacinia aliquet. Mauris ipsum. Nulla metus metus, ullamcorper vel, tincidunt sed, euismod in, nibh. Quisque volutpat condimentum velit. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nam nec ante. Sed lacinia, urna non tincidunt mattis, tortor neque adipiscing diam, a cursus ipsum ante quis turpis. Nulla facilisi. Ut fringilla. Suspendisse potenti. Nunc feugiat mi a tellus consequat imperdiet. Vestibulum sapien. Proin quam. Etiam ultrices. Suspendisse in justo eu magna luctus suscipit. Sed lectus. Integer euismod lacus luctus magna. Quisque cursus, metus vitae pharetra auctor, sem massa mattis sem, at interdum magna augue eget diam. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Morbi lacinia molestie dui. Praesent blandit dolor. Sed non quam. In vel mi sit amet augue congue elementum. Morbi in ipsum sit amet pede facilisis laoreet. Donec lacus nunc, viverra nec, blandit vel, egestas et, augue. Vestibulum tincidunt malesuada tellus. Ut ultrices ultrices enim. Curabitur sit amet mauris. Morbi in dui quis est pulvinar ullamcorper. Nulla facilisi. Integer lacinia sollicitudin massa. Cras metus. Sed aliquet risus a tortor. Integer id quam. Morbi mi. Quisque nisl felis, venenatis tristique, dignissim in, ultrices sit amet, augue. Proin sodales libero eget ante. Nulla quam. Aenean laoreet. Vestibulum nisi lectus, commodo ac, facilisis ac, ultricies eu, pede. Ut orci risus, accumsan porttitor, cursus quis, aliquet eget, justo. Sed pretium blandit orci. Ut eu diam at pede suscipit sodales. Aenean lectus elit, fermentum non, convallis id, sagittis at, neque. Nullam mauris orci, aliquet et, iaculis et, viverra vitae, ligula. Nulla ut felis in purus aliquam imperdiet. Maecenas aliquet mollis lectus. Vivamus consectetuer risus et tortor. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio. Praesent libero. Sed cursus ante dapibus diam. Sed nisi. Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. Praesent mauris. Fusce nec tellus sed augue semper porta. Mauris massa. Vestibulum lacinia arcu eget nulla. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Curabitur sodales ligula in libero. Sed dignissim lacinia nunc. Curabitur tortor. Pellentesque nibh. Aenean quam. In scelerisque sem at dolor. Maecenas mattis. Sed convallis tristique sem. Proin ut ligula vel nunc egestas porttitor. Morbi lectus risus, iaculis vel, suscipit quis, luctus non, massa. Fusce ac turpis quis ligula lacinia aliquet. Mauris ipsum. Nulla metus metus, ullamcorper vel, tincidunt sed, euismod in, nibh. Quisque volutpat condimentum velit. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nam nec ante. Sed lacinia, urna non tincidunt mattis, tortor neque adipiscing diam, a cursus ipsum ante quis turpis. Nulla facilisi. Ut fringilla. Suspendisse potenti. Nunc feugiat mi a tellus consequat imperdiet. Vestibulum sapien. Proin quam. Etiam ultrices. Suspendisse in justo eu magna luctus suscipit. Sed lectus. Integer euismod lacus luctus magna. Quisque cursus, metus vitae pharetra auctor, sem massa mattis sem, at interdum magna augue eget diam. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Morbi lacinia molestie dui. Praesent blandit dolor. Sed non quam. In vel mi sit amet augue congue elementum. Morbi in ipsum sit amet pede facilisis laoreet. Donec lacus nunc, viverra nec, blandit vel, egestas et, augue. Vestibulum tincidunt malesuada tellus. Ut ultrices ultrices enim. Curabitur sit amet mauris. Morbi in dui quis est pulvinar ullamcorper. Nulla facilisi. Integer lacinia sollicitudin massa. Cras metus. Sed aliquet risus a tortor. Integer id quam. Morbi mi. Quisque nisl felis, venenatis tristique, dignissim in, ultrices sit amet, augue. Proin sodales libero eget ante. Nulla quam. Aenean laoreet. Vestibulum nisi lectus, commodo ac, facilisis ac, ultricies eu, pede. Ut orci risus, accumsan porttitor, cursus quis, aliquet eget, justo. Sed pretium blandit orci. Ut eu diam at pede suscipit sodales. Aenean lectus elit, fermentum non, convallis id, sagittis at, neque. Nullam mauris orci, aliquet et, iaculis et, viverra vitae, ligula. Nulla ut felis in purus aliquam imperdiet. Maecenas aliquet mollis lectus. Vivamus consectetuer risus et tortor. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio. Praesent libero. Sed cursus ante dapibus diam. Sed nisi. Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. Praesent mauris. Fusce nec tellus sed augue semper porta. Mauris massa. Vestibulum lacinia arcu eget nulla. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Curabitur sodales ligula in libero. Sed dignissim lacinia nunc. Curabitur tortor. Pellentesque nibh. Aenean quam. In scelerisque sem at dolor. Maecenas mattis. Sed convallis tristique sem. Proin ut ligula vel nunc egestas porttitor. Morbi lectus risus, iaculis vel, suscipit quis, luctus non, massa. Fusce ac turpis quis ligula lacinia aliquet. Mauris ipsum. Nulla metus metus, ullamcorper vel, tincidunt sed, euismod in, nibh. Quisque volutpat condimentum velit. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nam nec ante. Sed lacinia, urna non tincidunt mattis, tortor neque adipiscing diam, a cursus ipsum ante quis turpis. Nulla facilisi. Ut fringilla. Suspendisse potenti. Nunc feugiat mi a tellus consequat imperdiet. Vestibulum sapien. Proin quam. Etiam ultrices. Suspendisse in justo eu magna luctus suscipit. Sed lectus. Integer euismod lacus luctus magna. Quisque cursus, metus vitae pharetra auctor, sem massa mattis sem, at interdum magna augue eget diam. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Morbi lacinia molestie dui. Praesent blandit dolor. Sed non quam. In vel mi sit amet augue congue elementum. Morbi in ipsum si.");
		System.out.println("\n"+s.decode(10, "file2.png"));
	}

}
