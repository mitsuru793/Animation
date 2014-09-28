import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Text implements Runnable {
	// 出現位置
	protected int startX;
	protected int startY;
	// 位置
	protected int x;
	protected int y;
	// 過去の座標
	protected ArrayList<Point> pointHistory;
	// 最大の履歴数
	protected int maxCountPast;
	protected int offsetResidual;

	// テキスト
	protected String text;
	// フォント
	protected Font font;
	protected String fontName;
	protected int fontStyle;
	protected int fontSize;

	// 色
	protected int red;
	protected int green;
	protected int blue;
	protected int alpha;

	// 速度
	protected double vx;
	protected double vy;

	// 残像を消していく時のスレッド
	private Thread threadDleteHistory;

	public Text(String text, int startX, int startY, int vx, int vy) {
		maxCountPast = 10;
		pointHistory = new ArrayList<Point>();
		this.startX = x = startX;
		this.startY = y = startY;

		this.vx = vx;
		this.vy = vy;
		this.text = text;
		setColor(0, 0, 0, 255);
		fontName = "Arial";
		fontStyle = Font.BOLD;
		fontSize = 12;
		font = new Font("Arial", Font.BOLD, fontSize);

		offsetResidual = 7;
	}

	/**
	 * テキストの色を設定
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 * @param alpha
	 */
	public void setColor(int red, int green, int blue, int alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
		if (red > 255) {
			this.red = 255;
		} else if (red < 0) {
			this.red = 0;
		}
		if (green > 255) {
			this.green = 255;
		} else if (green < 0) {
			this.green = 0;
		}
		if (blue > 255) {
			this.blue = 255;
		} else if (blue < 0) {
			this.blue = 0;
		}
		if (alpha > 255) {
			this.alpha = 255;
		} else if (alpha < 0) {
			this.alpha = 0;
		}
	}

	/**
	 * テキストのフォントを設定
	 * 
	 * @param name
	 * @param style
	 * @param size
	 */
	public void setFont(String name, int style, int size) {
		fontName = name;
		fontStyle = style;
		fontSize = size;
		font = new Font(name, style, fontSize);
	}

	public void setFontSize(int size) {
		fontSize = size;
		font = new Font(fontName, fontStyle, fontSize);
	}

	public void move() {
		// テキストを速度分だけ移動させる
		x += vx;
		y += vy;
	}

	public void moveX() {
		// テキストを速度分だけ移動させる
		x += vx;
	}

	public void moveY() {
		// テキストを速度分だけ移動させる
		y += vy;
	}

	public void stop() {
		vx = 0;
		vy = 0;
	}

	public void draw(Graphics g) {
		// 色をセット
		g.setColor(new Color(red, green, blue, alpha));
		g.setFont(font);
		g.drawString(text, x, y);

		if (maxCountPast > 0) {
			drawResidualY(pointHistory, g);
		}
	}

	public void update() {
		updatePointHistory(pointHistory, x, y);
	}

	/**
	 * テキストの座標位置を履歴として記録していく
	 * 
	 * @param pointHistory
	 * @param x
	 * @param y
	 */
	public void updatePointHistory(ArrayList<Point> ppointHistoryastPonts,
			int x, int y) {
		if (vx == 0 && vy == 0) {
			// スレッドを起動
			if (threadDleteHistory == null) {
				threadDleteHistory = new Thread(this);
			} else if (threadDleteHistory.getState() == Thread.State
					.valueOf("NEW")) {
				threadDleteHistory.start();
			}

			return;
		}
		// 履歴数が最大の場合は、古いものを追い出す（キューの仕組み）
		if (pointHistory.size() < maxCountPast) {
			pointHistory.add(new Point(x, y));
		} else {
			pointHistory.remove(pointHistory.size() - 1);
			pointHistory.add(0, new Point(x, y));
		}
	}

	/**
	 * 座標の履歴から縦の動きに対して残像を描く
	 * 
	 * @param pointHistory
	 * @param g
	 */
	public void drawResidualY(ArrayList<Point> pointHistory, Graphics g) {
		if (pointHistory.size() <= 0) {
			return;
		}
		// 薄くする量
		int unitPale = 255 / pointHistory.size();
		int pale;
		// 上から下への残像
		if (vy >= 0 && vx == 0) {
			for (int i = 1; i <= pointHistory.size(); i++) {
				// 出現位置より残像の始まりが高い場合
				if (y - i * offsetResidual < startY) {
					break;
				}
				pale = alpha - (unitPale * i);
				pale = pale < 0 ? 0 : pale;
				g.setColor(new Color(red, green, blue, pale));
				g.drawString(text, x, y - i * offsetResidual);
			}
		}
		// 下から上への残像
		if (vy <= 0 && vx == 0) {
			for (int i = 1; i <= pointHistory.size(); i++) {
				// 出現位置より残像の始まりが低い場合
				if (y - i * offsetResidual > startY) {
					break;
				}
				pale = alpha - (unitPale * i);
				pale = pale < 0 ? 0 : pale;
				g.setColor(new Color(red, green, blue, pale));
				g.drawString(text, x, y + i * offsetResidual);
			}
		}
	}

	// 　一番古い履歴を消去していく
	public void run() {
		while (true) {
			if (pointHistory.size() > 0) {
				pointHistory.remove(pointHistory.size() - 1);
			} else {
				break;
			}
			try {
				// テキスト停止時に残像が減る間隔
				Thread.sleep(MainPanel.MAIN_LOOP_INTERVAL * 2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}