import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Player extends BaseActor {
    private boolean facingRight = true;
    private float screenWidth, screenHeight;

    public Player(float x, float y, Stage s, float screenWidth, float screenHeight) {
        super(x, y, s);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        // Load the animation from the sprite sheet
        Animation<TextureRegion> anim = loadAnimationFromSheetExact("assets/player.PNG", 4, 7, 27, 0.05f, true);
        
        // Set the play mode to LOOP_PINGPONG
        anim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG); 
        
        // Set the animation for the player
        setAnimation(anim);
        
        // Set the boundary polygon for collision detection
        setBoundaryPolygon(8);
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        
        // Controls player movement, speed, and flipping the image
        float speed = 200;
        boolean moving = false;
        
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            moveBy(-speed * dt, 0);
            if (facingRight) {
                flipAnimation(true, false);
                facingRight = false;
            }
            moving = true;
        }
        
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            moveBy(speed * dt, 0);
            if (!facingRight) {
                flipAnimation(true, false);
                facingRight = true;
            }
            moving = true;
        }
        
        // Pause the animation if the player is not moving
        setAnimationPaused(!moving);
        
        // Clamp the player's position to keep them on screen
        clampPosition(0, 0, screenWidth - getWidth(), screenHeight - getHeight());
    }
}