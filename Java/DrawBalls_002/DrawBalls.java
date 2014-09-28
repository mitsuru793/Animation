import java.awt.*;
import javax.swing.*;

public class DrawBalls extends JFrame {
	public DrawBalls() {
		// タイトルを設定
		setTitle("空からたくさんのランダムボールが降ってくる");
		// サイズ変更禁止
		setResizable(false);

		// パネルを作成してフレームに追加
		MainPanel panel = new MainPanel();
		Container contentPane = getContentPane();
		contentPane.add(panel);

		// パネルサイズに合わせてフレームサイズを自動設定
		pack();
	}

	public static void main(String[] args) {
		DrawBalls frame = new DrawBalls();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}