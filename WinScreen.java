import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class WinScreen extends BaseScreen {
    @Override
    public void initialize() {
        BaseActor background = new BaseActor(0, 0, mainStage);
        background.loadTexture("assets/you_win.png");
        background.setSize((float)WIDTH, (float)HEIGHT);// ??? no idea why but adding float inherent killed error code
        background.setPosition(0, 0);
    }

    @Override
    public void update(float dt) {
        // No continuous updates needed
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Keys.R) {
            BaseGame.setActiveScreen(new LevelScreen());
            return true;
        }
        return false;
    }

    // Required InputProcessor methods
    @Override public boolean keyUp(int keycode) { return false; }
    @Override public boolean keyTyped(char character) { return false; }
    @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
    //@Override public boolean touchCancelled(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
    @Override public boolean mouseMoved(int screenX, int screenY) { return false; }

    //This @Override should work, I still can't find the reason it doesn't 
    //@Override public boolean scrolled(float amountX, float amountY) { return false; }
    public boolean scrolled(int amount) { return false; }
}
