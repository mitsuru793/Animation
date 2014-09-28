import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class MainPanel extends JPanel implements Runnable {
	// パネルサイズ
	public static final int WIDTH = 240;
	public static final int HEIGHT = 240;

	// テキストの数
	private static int NUM_TEXT;
	// テキストを格納する配列
	private Text[] text;

	// ダブルバッファリング（db）用
	private Graphics dbg;
	private Image dbImage = null;

	// アニメーション用スレッド
	private Thread thread;
	public static final int MAIN_LOOP_INTERVAL = 30;
	
	// 何ミリ秒後にアニメーションを開始するか
	public final int startTime = 1000;

	public MainPanel() {
		// パネルの推奨サイズを設定、pack()するときに必要
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setSize(WIDTH, HEIGHT);

		NUM_TEXT = 2;
		text = new Text[NUM_TEXT];

		text[0] = new Text("Hello", 0, 0, 0, 2);
		text[0].setColor(255, 153, 102, 255);
		text[1] = new Text("Hello", 0, HEIGHT, 0, -2);
		text[1].setColor(255, 153, 102, 255);
		FontMetrics fm;
		for (Text currentText : text) {
			currentText.setFontSize(30);
			fm = getFontMetrics(currentText.font);
			// テキストを水平中央に配置
			currentText.x = (WIDTH - fm.stringWidth(currentText.text)) / 2;
		}

		// スレッドを起動
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);

	}

	// メインループ
	public void run() {
		// アニメーションの開始時間を遅らせる
		try {
			Thread.sleep(startTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int heightCenterText;
		// プログラムが終了するまでフレーム処理を繰り返す
		while (true) {
			for (int i = 0; i < NUM_TEXT; i++) {
				heightCenterText = HEIGHT / 2;
				text[i].moveY();
				if (text[i].vy > 0 && text[i].y >= heightCenterText) {
					text[i].stop();
				} else if (text[i].vy < 0 && text[i].y <= heightCenterText) {
					text[i].stop();
				}
			}
			objectUpdate();
			objectRender();
			paintScreen();
			// 10ミリ秒だけ休止
			try {
				Thread.sleep(MAIN_LOOP_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * オブジェクトの状態を更新
	 */
	private void objectUpdate() {
		// テキストの状態更新
		for (Text currentText : text) {
			currentText.update();
		}
	}

	/**
	 * バッファにレンダリング（ダブルバッファリング）
	 */
	public void objectRender() {
		// バッファイメージ
		if (dbImage == null) {
			dbImage = createImage(WIDTH, HEIGHT);
			if (dbImage == null) {
				System.out.println("dbImage is null");
				return;
			} else {
				// バッファイメージの描画オブジェクト
				dbg = dbImage.getGraphics();
			}
		}

		// バッファをクリアする
		dbg.setColor(Color.WHITE);
		dbg.fillRect(0, 0, WIDTH, HEIGHT);

		dbg.clearRect(0, 0, WIDTH, HEIGHT);
		for (Text currentText : text) {
			currentText.draw(dbg);
		}
	}

	/**
	 * バッファを画面に描画
	 */
	private void paintScreen() {
		try {
			Graphics g = getGraphics(); // グラフィックオブジェクトを取得
			if ((g != null) && (dbImage != null)) {
				g.drawImage(dbImage, 0, 0, null); // バッファイメージを画面に描画
			}
			Toolkit.getDefaultToolkit().sync();
			if (g != null) {
				g.dispose(); // グラフィックオブジェクトを破棄
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
