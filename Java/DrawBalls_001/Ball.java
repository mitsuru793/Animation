import java.awt.*;
import java.util.Random;

public class Ball {
	public static void randomDraw(Graphics g) {
		// 乱数の生成
		Random random = new Random();
		int x = random.nextInt(MainPanel.WIDTH);
		int y = random.nextInt(MainPanel.HEIGHT);
		int size = random.nextInt(300);
		int red = random.nextInt(256);
		int green = random.nextInt(256);
		int blue = random.nextInt(256);
		// 0だと見えないため1〜255を指定
		int alpha = random.nextInt(255) + 1;

		// 色をセット
		g.setColor(new Color(red, green, blue, alpha));
		g.fillOval(x, y, size, size);
	}
}