import java.awt.*;
import java.util.Random;

public class Ball {
	// スピード
	private static final int SPEED = 6;
	// 重力
	private static final double GRAVITY = 1.0;

	// 位置
	protected int x;
	protected int y;
	// 大きさ
	protected int size;
	// 色
	protected int red;
	protected int green;
	protected int blue;
	protected int alpha;

	// 速度
	protected double vx;
	protected double vy;

	public Ball() {
		// 乱数の生成
		Random random = new Random();
		// ボールの出現場所
		x = random.nextInt(MainPanel.WIDTH);
		y = -random.nextInt(500);

		vx = 0;
		vy = 0;

		// 色
		size = random.nextInt(100);
		red = random.nextInt(256);
		green = random.nextInt(256);
		blue = random.nextInt(256);
		// 0だと見えないため1〜255を指定
		alpha = random.nextInt(255) + 1;
	}

	public void draw(Graphics g) {
		// 色をセット
		g.setColor(new Color(red, green, blue, alpha));
		g.fillOval((int) x, (int) y, size, size);
	}

	/**
	 * ボールの位置情報を更新する
	 */
	public void update() {
		// 重力で下向きに加速度がかかる
		vy += GRAVITY;

		// 速度を元に位置を更新
		y += vy;
		// 着地したか調べる
		if (y >= MainPanel.HEIGHT - size) {
			y = MainPanel.HEIGHT - size;
			double vyBefore = vy;
			//跳ね返り係数をかける
			vy = vy * -1 * 0.8;
			//　微妙に動き続けてしまうのを防止
			vy = Math.abs(vyBefore) - Math.abs(vy) < 1 ? 0 : vy;
		}
	}
}