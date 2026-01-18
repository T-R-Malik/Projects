package com.practice_7;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameCharacter extends GameObjects{
    private SpriteBatch batch;
    private Sprite charSprite;
    private Rectangle playerBounds;

    // Idle Character
    private Texture idleSheet;
    private static final int IDLE_FRAME_COLS = 11;
    private static final int IDLE_FRAME_ROWS = 1;
    private Animation<TextureRegion> idleAnim;
    private float stateTime;
    private TextureRegion[] idleFrames;
    float speed = 100f;

    // Moving Character
    private Texture runSheet;
    private static final int RUN_FRAME_COLS = 12;
    private static final int RUN_FRAME_ROWS = 1;
    private Animation<TextureRegion> runAnim;
    private TextureRegion[] runFrames;
    boolean facingRight = true;
    private Sound runningSound;
    private long runningSoundId = -1;
    private boolean isRunningSoundPlaying = false;

    // Constructor Variables
    private String charTexSheet;
    String charRunSheet;

    // For ending.
    boolean playerVisible = true;

    public GameCharacter(String character, int width, int height, SpriteBatch batch){
        this.batch = batch;
        playerBounds = new Rectangle(0, 0, width, height);
        charTexSheet = character;
        if (character.equals("Virtual Guy - Idle.png")){
            charRunSheet = "Virtual Guy - Run.png";
        }
        else if(character.equals("Frog - Idle.png")){
            charRunSheet = "Frog - Run.png";
        }
        else if(character.equals("Pink Guy - Idle.png")){
            charRunSheet = "Pink Guy - Run.png";
        }
        else if(character.equals("Masked Guy - Idle.png")){
            charRunSheet = "Masked Guy - Run.png";
        }
    }

    @Override
    public void start() {
        // Idle Character
        idleSheet = new Texture(Gdx.files.internal(charTexSheet));
        TextureRegion[][] idleTiles = TextureRegion.split(idleSheet, idleSheet.getWidth() / IDLE_FRAME_COLS, idleSheet.getHeight() / IDLE_FRAME_ROWS);
        idleFrames = new TextureRegion[IDLE_FRAME_COLS * IDLE_FRAME_ROWS];
        int idleIndex = 0;
        for (int i = 0; i < IDLE_FRAME_ROWS; i++) {
            for (int j = 0; j < IDLE_FRAME_COLS; j++) {
                idleFrames[idleIndex++] = idleTiles[i][j];
            }
        }

        idleAnim = new Animation<TextureRegion>(0.050f, idleFrames);

        // Moving Character
        runSheet = new Texture(Gdx.files.internal(charRunSheet));
        TextureRegion[][] runTiles = TextureRegion.split(runSheet, runSheet.getWidth() / RUN_FRAME_COLS, runSheet.getHeight() / RUN_FRAME_ROWS);
        TextureRegion[] runFrames = new TextureRegion[RUN_FRAME_COLS * RUN_FRAME_ROWS];
        int runIndex = 0;
        for (int i = 0; i < RUN_FRAME_ROWS; i++) {
            for (int j = 0; j < RUN_FRAME_COLS; j++) {
                runFrames[runIndex++] = runTiles[i][j];
            }
        }
        runAnim = new Animation<TextureRegion>(0.050f, runFrames);

        stateTime = 0f;
        charSprite = new Sprite(idleFrames[0]);
        charSprite.setSize(playerBounds.width, playerBounds.height);
        charSprite.setRegion(idleAnim.getKeyFrame(0));

        runningSound = Gdx.audio.newSound(Gdx.files.internal("RUNNING.mp3"));
    }

    public Rectangle getBounds() {return new Rectangle(charSprite.getX(), charSprite.getY(), charSprite.getWidth(), charSprite.getHeight());}

    @Override
    public boolean update() {
        float delta = Gdx.graphics.getDeltaTime();
        stateTime += delta;

        if(isMoving()) {
            Vector2 inputVec = new Vector2();
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) inputVec.y = 1;
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) inputVec.y = -1;
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) inputVec.x = -1;
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) inputVec.x = 1;

            if (inputVec.len2() > 0) inputVec.nor();

            charSprite.translate(inputVec.x * speed * delta, inputVec.y * speed * delta);

            charSprite.setX(MathUtils.clamp(charSprite.getX(), 20, 560));
            charSprite.setY(MathUtils.clamp(charSprite.getY(), 30, 200));

            if (inputVec.x != 0) {
                if (inputVec.x > 0) facingRight = true;
                else if (inputVec.x < 0) facingRight = false;
            }
        }

        TextureRegion currentFrame;
        if (isMoving()) {
            currentFrame = runAnim.getKeyFrame(stateTime, true);
            if (!isRunningSoundPlaying) {
                runningSoundId = runningSound.loop(0.5f);
                isRunningSoundPlaying = true;
            }
        } else {
            currentFrame = idleAnim.getKeyFrame(stateTime, true);
            if (isRunningSoundPlaying) {
                runningSound.stop(runningSoundId);
                isRunningSoundPlaying = false;
            }
        }

        // Flipping when character turns.
        if ((facingRight && currentFrame.isFlipX()) || (!facingRight && !currentFrame.isFlipX())) {
            currentFrame.flip(true, false);
        }

        charSprite.setRegion(currentFrame);

        batch.begin();
        if(playerVisible) {charSprite.draw(batch);}
        batch.end();
        return true;
    }

    public boolean isMoving() {
        return (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.DOWN));
    }

    @Override
    public void destroy() {
        runningSound.dispose();
    }
}
