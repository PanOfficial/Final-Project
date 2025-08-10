import com.badlogic.gdx.scenes.scene2d.Stage;

public class FallingObject extends BaseActor {
    private boolean isCoin;

    public FallingObject(float x, float y, Stage s, boolean coin) {
        super(x, y, s);
        isCoin = coin;
        loadTexture(coin ? "assets/coin.png" : "assets/bomb.png");
        setSize(32, 32); // TEST; find rite size for coin to appear
        setVelocity(0, -150);
        setBoundaryPolygon(6);
    }

    public boolean isCoin() {
        return isCoin;
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        if (getY() + getHeight() < 0) {
            remove();
        }
    }
}
