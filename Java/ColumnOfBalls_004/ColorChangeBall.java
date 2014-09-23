import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class ColorChangeBall extends Ball {
	public ColorChangeBall(int x, int y, int vx, int vy) {
		super(x, y, vx, vy);
	}

	@Override
	public void draw(Graphics g) {
		// 乱数の生成
		Random random = new Random();
		int red = random.nextInt(256);
		int green = random.nextInt(256);
		int blue = random.nextInt(256);
		// 色を変える
		g.setColor(new Color(red, blue, green));
		g.fillOval(x, y, SIZE, SIZE);
	}
}