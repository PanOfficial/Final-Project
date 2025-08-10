import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class GameOverScreen extends BaseScreen {
    @Override
    public void initialize() {
        BaseActor background = new BaseActor(0f, 0f, mainStage);
        background.loadTexture("assets/you_lose.png");
        background.setSize((float) WIDTH, (float) HEIGHT);
        background.setPosition(0f, 0f);
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
        //CHECK THESE //ONE ATA TIME, NEVER NEEDED THEM IN OTHER GMs
    // I believe these are required InputProcessor methods (I made this change with a few others and errors went away so possibly these didnt need to be done
    @Override public boolean keyUp(int keycode) { return false; }
    @Override public boolean keyTyped(char character) { return false; }
    @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
    //@Override public boolean touchCancelled(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
    @Override public boolean mouseMoved(int screenX, int screenY) { return false; }
    //@Override public boolean scrolled(float amountX, float amountY) { return false; }
    public boolean scrolled(int amount) { return false; } // Optional for compatibility
}
