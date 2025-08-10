import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


public class CoinCatcherGame extends BaseGame {
    @Override
    public void create() {
        //super.create();
        BitmapFont font = new BitmapFont();
        labelStyle = new Label.LabelStyle(font, Color.WHITE);
        setActiveScreen(new MenuScreen());
    }
}
