public class SpeedChangeBall extends Ball {
	// ボールのスピードを変更する段階
	protected int maxSpeedStage;
	// 現在のボールのスピード段階
	protected int nowSpeedStage;

	public SpeedChangeBall(int x, int y, int vx, int vy, int maxSpeedStage) {
		super(x, y, vx, vy);
		this.maxSpeedStage = maxSpeedStage;
		nowSpeedStage = 0;
	}

	public void move() {
		// ボールを速度分だけ移動させる
		x += vx;
		y += vy;

		// 左または右に当たったらx方向速度の符号を反転させる
		if (x < 0 || x > MainPanel.WIDTH - SIZE) {
			vx = -vx;
			// 加速させる
			if (nowSpeedStage < maxSpeedStage) {
				addSpeedX(2);
				nowSpeedStage++;
			}
		}

		// 上または下に当たったらy方向速度の符号を反転させる
		if (y < 0 || y > MainPanel.HEIGHT - SIZE) {
			vy = -vy;
			// 加速させる
			if (nowSpeedStage < maxSpeedStage) {
				addSpeedY(2);
				nowSpeedStage++;
			}
		}
	}
}
