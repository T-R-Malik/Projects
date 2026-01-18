package com.practice_7;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class GameRoom extends GameObjects{
    private SpriteBatch batch;

    // Walls and Floor
    private Texture roomSheet;
    private TextureRegion[] roomFrames;
    private static final int ROOM_FRAME_COLS = 3;
    private static final int ROOM_FRAME_ROWS = 2;

    // Door
    private Sprite doorSprite;
    private Texture doorSheet;
    private TextureRegion[] doorFrames;
    private static final int DOOR_FRAME_COLS = 4;
    private static final int DOOR_FRAME_ROWS = 1;
    private Animation<TextureRegion> doorAnim;
    float stateTime;
    float speed = 100f;
    private Rectangle doorBounds;
    private boolean doorOpening = false;
    private boolean doorOpened = false;
    private boolean doorClosing;
    private GameCharacter charSprite;
    private Sound doorOpenSound;
    private Sound doorCloseSound;

    // Furniture
    private Sprite cupboardSprite;
    private Texture cupboardSheet;
    private TextureRegion[] cupboardFrames;
    private static final int CUPBOARD_FRAME_COLS = 2;
    private static final int CUPBOARD_FRAME_ROWS = 1;
    private Animation<TextureRegion> cupboardAnim;
    private Rectangle cupboardBounds;
    private boolean cupboardOpening = false;
    private boolean cupboardOpened = false;
    private boolean cupboardClosing;
    float cupboardStateTime;
    private Sound cupboardOpenSound;
    private Sound cupboardCloseSound;

    // Misc.
    private Texture matTex;
    private Texture picTex;
    private Texture plantsTex;
    private Texture clockTex;
    private Texture sofaTex;
    private Texture lampTex;
    private Texture plantTex;
    private Texture pauseButton;
    private Sound pauseSound;
    private boolean paused = false;
    private Rectangle pauseBounds;
    private static final float VIRTUAL_WIDTH = 700f;
    private static final float VIRTUAL_HEIGHT = 500f;
    private boolean pauseMusicStarted = false;
    private boolean wasPaused = false;
    private long pauseMusicId = -1;

    // Paper
    private boolean showPaper = false;
    private Texture paperTex;
    private Sound paperSound;
    private BitmapFont paperWriting;
    private boolean paperSeen = false;
    private BitmapFont endingMessage;
    private Texture backgroundImage;
    private String[] riddles = {
        "What goes up when rain comes down?",
        "The more you take, the more you \nleave behind. What am I?",
        "I have an eye but can't see. What am I?"
    };
    private String[] answers = {"umbrella", "footsteps", "needle"};
    private int currentRiddle = 0;
    private boolean allRiddlesSolved = false;
    private StringBuilder userAnswer = new StringBuilder();
    private boolean riddleSolved = false;
    private BitmapFont riddleFont;
    private Sound correctSound;
    private Sound wrongSound;
    private Texture buttonTex;
    private boolean justOpenedPaper = false;
    private Sound writingSound;

    // Ending.
    private Sound endSong;
    private boolean endMusicStarted = false;
    private boolean gameOver = false;

    public GameRoom(SpriteBatch batch, GameCharacter charSprite){
        this.batch = batch;
        this.charSprite = charSprite;
    }

    @Override
    public void start() {
        roomSheet = new Texture(Gdx.files.internal("ROOM.png"));
        roomSheet.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        TextureRegion[][] roomTiles = TextureRegion.split(roomSheet, roomSheet.getWidth() / ROOM_FRAME_COLS, roomSheet.getHeight() / ROOM_FRAME_ROWS);
        roomFrames = new TextureRegion[ROOM_FRAME_COLS * ROOM_FRAME_ROWS];

        int roomIndex = 0;
        for (int i = 0; i < ROOM_FRAME_ROWS; i++) {
            for (int j = 0; j < ROOM_FRAME_COLS; j++) {
                roomFrames[roomIndex++] = roomTiles[i][j];
            }
        }

        // Door.
        doorSheet = new Texture(Gdx.files.internal("DOORS_WINDOWS.png"));
        TextureRegion[][] doorTiles = TextureRegion.split(doorSheet, doorSheet.getWidth() / DOOR_FRAME_COLS, doorSheet.getHeight() / DOOR_FRAME_ROWS);
        doorFrames = new TextureRegion[DOOR_FRAME_COLS * DOOR_FRAME_ROWS];

        int doorIndex = 0;
        for (int i = 0; i < DOOR_FRAME_ROWS; i++) {
            for (int j = 0; j < DOOR_FRAME_COLS; j++) {
                doorFrames[doorIndex++] = doorTiles[i][j];
            }
        }

        doorBounds = new Rectangle(520, 240, 130, 180);
        TextureRegion doorOpen[] = {doorFrames[2], doorFrames[3], doorFrames[1], doorFrames[0]};
        doorAnim = new Animation<TextureRegion>(0.050f, doorOpen);

        stateTime = 0f;
        doorSprite = new Sprite(doorFrames[2]);
        doorSprite.setRegion(doorAnim.getKeyFrame(0));

        doorOpenSound = Gdx.audio.newSound(Gdx.files.internal("DOOR_OPEN.mp3"));
        doorCloseSound = Gdx.audio.newSound(Gdx.files.internal("DOOR_CLOSE.mp3"));

        // Furniture.
        cupboardSheet = new Texture(Gdx.files.internal("CUPBOARD.PNG"));
        TextureRegion[][] cupboardTiles = TextureRegion.split(cupboardSheet,
            cupboardSheet.getWidth() / CUPBOARD_FRAME_COLS,
            cupboardSheet.getHeight() / CUPBOARD_FRAME_ROWS);
        cupboardFrames = new TextureRegion[CUPBOARD_FRAME_COLS * CUPBOARD_FRAME_ROWS];

        int cupBoardIndex = 0;
        for (int i = 0; i < CUPBOARD_FRAME_ROWS; i++) {
            for (int j = 0; j < CUPBOARD_FRAME_COLS; j++) {
                cupboardFrames[cupBoardIndex++] = cupboardTiles[i][j];
            }
        }

        cupboardBounds = new Rectangle(100, 200, 80, 100);
        TextureRegion cupboardOpen[] = {cupboardFrames[0], cupboardFrames[1]};
        cupboardAnim = new Animation<TextureRegion>(0.050f, cupboardOpen);

        stateTime = 0f;
        cupboardSprite = new Sprite(cupboardFrames[0]);
        cupboardSprite.setRegion(cupboardAnim.getKeyFrame(0));
        cupboardOpenSound = Gdx.audio.newSound(Gdx.files.internal("CUPBOARD_OPEN.mp3"));
        cupboardCloseSound = Gdx.audio.newSound(Gdx.files.internal("CUPBOARD_CLOSE.mp3"));


        //Misc
        matTex = new Texture(Gdx.files.internal("MAT.png"));
        matTex.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        picTex = new Texture(Gdx.files.internal("PICTURE.png"));
        picTex.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        plantsTex = new Texture(Gdx.files.internal("PLANTS.png"));
        clockTex = new Texture(Gdx.files.internal("CLOCK.png"));
        sofaTex = new Texture(Gdx.files.internal("SOFA.png"));
        lampTex = new Texture(Gdx.files.internal("LAMP.png"));
        plantTex = new Texture(Gdx.files.internal("PLANT2.png"));
        pauseButton = new Texture(Gdx.files.internal("PAUSE_BUTTON.png"));
        pauseSound = Gdx.audio.newSound(Gdx.files.internal("BUTTON_SOUND.mp3"));
        pauseBounds = new  Rectangle(630, 420, 70, 60);

        // Paper
        paperTex = new Texture(Gdx.files.internal("PAPERTEX.png"));
        paperSound = Gdx.audio.newSound(Gdx.files.internal("PAPER_RUSTLE.mp3"));
        paperWriting = new BitmapFont();
        riddleFont = new BitmapFont();
        buttonTex = new Texture(Gdx.files.internal("BUTTON_E.png"));
        correctSound = Gdx.audio.newSound(Gdx.files.internal("CORRECT_SOUND.mp3"));
        wrongSound = Gdx.audio.newSound(Gdx.files.internal("WRONG_SOUND.mp3"));
        writingSound = Gdx.audio.newSound(Gdx.files.internal("WRITING_SOUND.mp3"));

        // Ending game.
        endingMessage = new BitmapFont();
        backgroundImage = new Texture(Gdx.files.internal("Brown.png"));
        endSong = Gdx.audio.newSound(Gdx.files.internal("INTRO_SOUND.mp3"));
    }

    @Override
    public boolean update() {
        batch.begin();
        int tileWidth = roomSheet.getWidth() / ROOM_FRAME_COLS;
        int tileHeight = roomSheet.getHeight() / ROOM_FRAME_ROWS;

        for (int i = 0; i < 9; i++) {
            batch.draw(roomFrames[2], i * tileWidth, 0, tileWidth + 23, tileHeight * 5);
        }

        if (Gdx.input.justTouched()) {

            float scaleX = VIRTUAL_WIDTH / Gdx.graphics.getWidth();
            float scaleY = VIRTUAL_HEIGHT / Gdx.graphics.getHeight();

            float touchX = Gdx.input.getX() * scaleX;
            float touchY = (Gdx.graphics.getHeight() - Gdx.input.getY()) * scaleY;

            if (pauseBounds.contains(touchX, touchY)) {
                pauseSound.play();
                paused = !paused;
            }
        }

        // Detect pause state change
        if (paused && !wasPaused) {
            // Just paused
            pauseMusicId = endSong.loop(0.6f);
            charSprite.playerVisible = false;
        }
        else if (!paused && wasPaused) {
            // Just unpaused
            endSong.stop(pauseMusicId);
            charSprite.playerVisible = true;
        }

        wasPaused = paused;

        if(!paused) {
            // Door animation.
            Rectangle playerRect = charSprite.getBounds();
            boolean touchingDoor = Intersector.overlaps(playerRect, doorBounds);

            if (touchingDoor && allRiddlesSolved) {
                if (!doorOpened && !doorOpening) {
                    doorOpening = true;
                    doorClosing = false;
                    stateTime = 0f;

                    doorOpenSound.play();
                }
            } else {
                if (doorOpened && !doorClosing) {
                    doorClosing = true;
                    doorOpening = false;
                    stateTime = 0f;

                    doorCloseSound.play();
                }
            }

            TextureRegion currentFrame;
            if (doorOpening) {
                stateTime += Gdx.graphics.getDeltaTime();
                if (doorAnim.isAnimationFinished(stateTime)) {
                    doorOpening = false;
                    doorOpened = true;
                    stateTime = doorAnim.getAnimationDuration();
                }
                currentFrame = doorAnim.getKeyFrame(stateTime, false);
            } else if (doorClosing) {
                stateTime += Gdx.graphics.getDeltaTime();
                float reverseTime = doorAnim.getAnimationDuration() - stateTime;
                if (reverseTime <= 0) {
                    doorClosing = false;
                    doorOpened = false;
                    reverseTime = 0f;
                    stateTime = 0f;
                }
                currentFrame = doorAnim.getKeyFrame(reverseTime, false);
            } else {
                currentFrame = doorAnim.getKeyFrame(stateTime, false);
            }

            // Cupboard Animation.
            boolean touchingCupboard = Intersector.overlaps(playerRect, cupboardBounds);

            if (touchingCupboard) {
                if (!cupboardOpened && !cupboardOpening) {
                    cupboardOpening = true;
                    cupboardClosing = false;
                    cupboardStateTime = 0f;

                    cupboardOpenSound.play();
                }
            } else {
                if (cupboardOpened && !cupboardClosing) {
                    cupboardClosing = true;
                    cupboardOpening = false;
                    cupboardStateTime = 0f;
                    cupboardCloseSound.play();
                }
            }

            TextureRegion currentCupboardFrame;
            if (cupboardOpening) {
                cupboardStateTime += Gdx.graphics.getDeltaTime();
                if (cupboardAnim.isAnimationFinished(cupboardStateTime)) {
                    cupboardOpening = false;
                    cupboardOpened = true;
                    cupboardStateTime = cupboardAnim.getAnimationDuration();
                }
                currentCupboardFrame = cupboardAnim.getKeyFrame(cupboardStateTime, false);
            } else if (cupboardClosing) {
                cupboardStateTime += Gdx.graphics.getDeltaTime();
                float reverseCupboardTime = cupboardAnim.getAnimationDuration() - cupboardStateTime;
                if (reverseCupboardTime <= 0) {
                    cupboardClosing = false;
                    cupboardOpened = false;
                    reverseCupboardTime = 0f;
                    cupboardStateTime = 0f;
                }
                currentCupboardFrame = cupboardAnim.getKeyFrame(reverseCupboardTime, false);
            } else {
                currentCupboardFrame = cupboardAnim.getKeyFrame(cupboardStateTime, false);
            }

            // Paper
            if (charSprite.getBounds().overlaps(cupboardBounds)) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.E) && !showPaper) {
                    paperSeen = true;
                    showPaper = true;
                    userAnswer.setLength(0);
                    justOpenedPaper = true;
                    paperSound.play();
                }
            }
            if (showPaper && Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                showPaper = false;
                paperSound.play();
            }

            // Riddle
            if (showPaper && !riddleSolved && !justOpenedPaper) {
                // User Input.
                for (int key = Input.Keys.A; key <= Input.Keys.Z; key++) {
                    if (Gdx.input.isKeyJustPressed(key)) {
                        writingSound.play();
                        userAnswer.append(Input.Keys.toString(key));
                    }
                }

                // Erasing input.
                if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE) && userAnswer.length() > 0) {
                    userAnswer.deleteCharAt(userAnswer.length() - 1);
                }

                if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    checkRiddleAnswer();
                }
            }

            batch.setColor(Color.WHITE);
            batch.draw(matTex, 150, 50, 350, 130);
            batch.draw(currentFrame, 480, 205, 130, 180);
            batch.draw(currentCupboardFrame, 90, 180, 130, 160);
            batch.draw(picTex, 60, 380, 150, 100);
            batch.draw(plantsTex, 100, 330, 80, 50);
            batch.draw(clockTex, 400, 420, 50, 50);
            batch.draw(sofaTex, 230, 200, 210, 130);
            batch.draw(lampTex, 450, 200, 50, 170);
            batch.draw(plantTex, 30, 195, 40, 120);
            batch.draw(pauseButton, 580, 420, 40, 40);

            if (cupboardOpened) {
                batch.draw(buttonTex, 150, 270, 20, 20);
            }
            if (paperSeen && charSprite.getBounds().overlaps(doorBounds) && allRiddlesSolved) {
                gameOver = true;
                batch.draw(backgroundImage, 0, 0, 700, 500);
                endingMessage.getData().setScale(4f);
                endingMessage.draw(batch, "GAME OVER", 150, 300);
                endingMessage.getData().setScale(2f);
                endingMessage.draw(batch, "Press ENTER to exit.", 180, 200);
                charSprite.playerVisible = !paused && !gameOver;
                if (!endMusicStarted) {
                    endSong.play();
                    endMusicStarted = true;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.ENTER) && allRiddlesSolved) {
                    Gdx.app.exit();
                }
            }

            if (showPaper) {
                batch.draw(paperTex, 200, 150, 300, 300);
                riddleFont.setColor(Color.BLACK);
                riddleFont.getData().setScale(2f);
                riddleFont.draw(batch, "RIDDLE " + (currentRiddle + 1), 300, 400);

                riddleFont.getData().setScale(1f);
                riddleFont.draw(batch, riddles[currentRiddle], 220, 350);

                riddleFont.getData().setScale(1f);
                riddleFont.draw(batch, "Your answer: ", 220, 300);
                riddleFont.draw(batch, userAnswer.toString() + "_", 320, 300);

                if (allRiddlesSolved) {
                    riddleFont.draw(batch, "All riddles solved! You can leave!", 220, 250);
                } else {
                    riddleFont.draw(batch, "Press ENTER to submit.", 220, 250);
                }

                riddleFont.draw(batch, "Press ESC to close.", 220, 230);

                if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                    showPaper = false;
                    userAnswer.setLength(0);
                    paperSound.play();
                }
            }
        } else{
            batch.setColor(1,  1, 1, 0.6f);
            batch.draw(backgroundImage, 0, 0, 700, 500);
            batch.draw(pauseButton, 580, 420, 40, 40);
            riddleFont.getData().setScale(5f);
            riddleFont.draw(batch, "PAUSED", 180, 270);
            riddleFont.setColor(Color.WHITE);
        }
        batch.end();
        justOpenedPaper = false;
        return false;
    }

    private void checkRiddleAnswer() {
        String answer = userAnswer.toString().toLowerCase().trim();

        if(answer.equals(answers[currentRiddle])){
            correctSound.play();
            userAnswer.setLength(0);

            currentRiddle++;

            if(currentRiddle >= riddles.length){
                allRiddlesSolved = true;
                riddleSolved = true;
                showPaper = false;
            }
        } else{
            wrongSound.play();
            userAnswer.setLength(0);
        }
    }

    @Override
    public void destroy() {}
}
