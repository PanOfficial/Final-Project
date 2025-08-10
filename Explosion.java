/*
 * import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Explosion extends BaseActor {
    public Explosion(float x, float y, Stage s) {
        super(x, y, s);

        // Load the explosion animation from your sprite sheet
        // Adjust these parameters (e.g., "explosion.png", 6, 6, 36) to match your sprite sheet
        // The last parameter 'false' ensures the animation plays only once
        Animation<TextureRegion> anim = loadAnimationFromSheetExact("assets/explosion.png", 6, 6, 36, 0.03f, false);
        setAnimation(anim);
        
        // This makes sure the animation is centered on the player
        centerAtPosition(x, y);

        // This removes the explosion from the stage after the animation is finished
        // Gdx.app.postRunnable is a safe way to do this after the current rendering cycle
        addAction(new com.badlogic.gdx.scenes.scene2d.actions.Actions() {
            @Override
            public com.badlogic.gdx.scenes.scene2d.Action run() {
                // Remove the actor from the stage when the animation completes
                if (isAnimationFinished()) {
                    remove();
                }
                return super.run();
            }
        });
    }
}


*/ // Change the update method in the LevelScreen
//@Override*
/*
public void update(float dt) {
    spawnTimer += dt;
    if (spawnTimer > 1.0f) {
        spawnTimer = 0;
        float x = MathUtils.random(50, WIDTH - 50);
        if (MathUtils.randomBoolean(0.8f)) {
            new FallingObject(x, HEIGHT, mainStage, true);
        } else {
            new FallingObject(x, HEIGHT, mainStage, false);
        }
    }

    
    for (BaseActor obj : BaseActor.getList(mainStage, "FallingObject")) {
        if (obj.overlaps(player)) {
            FallingObject fo = (FallingObject) obj;
            if (fo.isCoin()) {
                // Coin collision logic
            } else {
                // Bomb collision logic
                bombSound.play(audioVolume);
                
                // Create a new Explosion instance at the player's position
                new Explosion(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, mainStage);
                
                backgroundMusic.stop();
                BaseGame.setActiveScreen(new GameOverScreen());
            }
        }
    }
}*


/* Cahnge tghe constructor in BaseActor
 * public BaseActor(float x, float y, Stage s) {
    setPosition(x, y);
    s.addActor(this);
    animation = null;
    elapsedTime = 0;
    animationPaused = false;
    velocity = new Vector2(0, 0);
    // Ensure a default boundary is set for all actors
    setBoundaryRectangle(); 
 */


/* add this new method
 * // Center the actor's position at a given coordinate
public void centerAtPosition(float x, float y) {
    setPosition(x - getWidth() / 2, y - getHeight() / 2);
}
 */

//Unfortunaley I cant seem to get this to work, although I believe it is rite, it throws a ton opf errors
//I have no time to fix.
//Also doesnt allow time after the explosion for the images to play out before the game over screen appears
//maybe in the levelscreen, mainstage addaction new sequenceAction, delay (Actions.delay ?) 1.06f or higher float
//Also need to make sure the player is removed in time to the largest point in the explosion.
//I dont want the player to disappear without being hiddden from view when it happens.
//Need dispose method and add explosion sprite sheet to assets folder.