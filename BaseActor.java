import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group; // Changed from Actor to Group
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import java.util.ArrayList;

public class BaseActor extends Group { // Extends Group
    private Animation<TextureRegion> animation;
    private float elapsedTime;
    private boolean animationPaused;
    private Vector2 velocity;
    private Polygon boundary;

    public BaseActor(float x, float y, Stage s) {
        setPosition(x, y);
        s.addActor(this);
        animation = null;
        elapsedTime = 0;
        animationPaused = false;
        velocity = new Vector2(0, 0);
        setBoundaryRectangle();
    }

    public void loadTexture(String fileName) {
        Texture tex = new Texture(Gdx.files.internal(fileName));
        tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        setAnimation(new Animation<TextureRegion>(1.0f, new TextureRegion(tex)));
    }

    public void setAnimation(Animation<TextureRegion> anim) {
        animation = anim;
        TextureRegion tr = animation.getKeyFrame(0);
        float w = tr.getRegionWidth();
        float h = tr.getRegionHeight();
        setSize(w, h);
        setOrigin(w / 2, h / 2);
        setBoundaryRectangle();
    }

    public Animation<TextureRegion> loadAnimationFromSheetExact(String fileName, int rows, int cols, int totalFrames, float frameDuration, boolean loop) {
        Texture sheet = new Texture(Gdx.files.internal(fileName), true);
        sheet.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int frameWidth = sheet.getWidth() / cols;
        int frameHeight = sheet.getHeight() / rows;
        TextureRegion[][] temp = TextureRegion.split(sheet, frameWidth, frameHeight);
        Array<TextureRegion> frames = new Array<>();
        outer:
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (frames.size >= totalFrames)
                    break outer;
                
                TextureRegion region = new TextureRegion(temp[r][c]);
                // This trimming logic requires the Texture's data to be readable
                // which might cause performance issues. A simpler approach is to
                // adjust the frame's position and size.
                frames.add(region);
            }
        }
        Animation<TextureRegion> anim = new Animation<>(frameDuration, frames);
        anim.setPlayMode(loop ? Animation.PlayMode.LOOP : Animation.PlayMode.NORMAL);
        setAnimation(anim);
        return anim;
    }

    public void flipAnimation(boolean flipX, boolean flipY) {
        if (animation == null) return;
        
        Object[] keyFrames = animation.getKeyFrames();
        Array<TextureRegion> newFrames = new Array<>();
        
        for (Object obj : keyFrames) {
            if (obj instanceof TextureRegion) {
                TextureRegion originalRegion = (TextureRegion) obj;
                TextureRegion flippedRegion = new TextureRegion(originalRegion);
                flippedRegion.flip(flipX, flipY);
                newFrames.add(flippedRegion);
            }
        }
        animation = new Animation<>(animation.getFrameDuration(), newFrames);
        animation.setPlayMode(animation.getPlayMode());
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        if (!animationPaused)
            elapsedTime += dt;
        applyPhysics(dt);
        updateBoundary();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isVisible() && animation != null) {
            TextureRegion frame = animation.getKeyFrame(elapsedTime);
            batch.setColor(getColor());
            batch.draw(frame, getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
        super.draw(batch, parentAlpha);
    }

    public void applyPhysics(float dt) {
        moveBy(velocity.x * dt, velocity.y * dt);
    }

    public void setVelocity(float x, float y) {
        velocity.set(x, y);
    }

    public void setBoundaryRectangle() {
        float[] vertices = {0, 0, getWidth(), 0, getWidth(), getHeight(), 0, getHeight()};
        boundary = new Polygon(vertices);
        updateBoundary();
    }

    public void setBoundaryPolygon(int sides) {
        float w = getWidth();
        float h = getHeight();
        float[] vertices = new float[2 * sides];
        for (int i = 0; i < sides; i++) {
            float angle = i * 6.28f / sides;
            vertices[2 * i] = w / 2 * (float) Math.cos(angle) + w / 2;
            vertices[2 * i + 1] = h / 2 * (float) Math.sin(angle) + h / 2;
        }
        boundary = new Polygon(vertices);
        updateBoundary();
    }

    private void updateBoundary() {
        if (boundary != null) {
            boundary.setPosition(getX(), getY());
            boundary.setRotation(getRotation());
        }
    }

    public Polygon getBoundaryPolygon() {
        return boundary;
    }

    public boolean overlaps(BaseActor other) {
        if (this.boundary == null || other.boundary == null) {
            Gdx.app.log("Overlap Error", "Boundary not set for one or both actors.");
            return false;
        }
        return Intersector.overlapConvexPolygons(this.boundary, other.boundary);
    }

    public static ArrayList<BaseActor> getList(Stage stage, String className) {
        ArrayList<BaseActor> list = new ArrayList<>();
        for (Actor a : stage.getActors()) {
            if (BaseActor.class.isAssignableFrom(a.getClass())) {
                try {
                    if (a.getClass().getSimpleName().equals(className) ||
                        Class.forName(className).isInstance(a)) {
                        list.add((BaseActor) a);
                    }
                } catch (ClassNotFoundException e) {
                    Gdx.app.error("getList Error", "Class not found: " + className, e);
                }
            }
        }
        return list;
    }

    public void setClickListener(final Runnable action) {
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                action.run();
                return true;
            }
        });
    }

    public void clampPosition(float minX, float minY, float maxX, float maxY) {
        float x = Math.max(minX, Math.min(getX(), maxX));
        float y = Math.max(minY, Math.min(getY(), maxY));
        setPosition(x, y);
    }

    public void setAnimationPaused(boolean pause) {
        animationPaused = pause;
    }

    public boolean isAnimationPaused() {
        return animationPaused;
    }
}