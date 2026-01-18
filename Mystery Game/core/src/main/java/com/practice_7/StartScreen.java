package com.practice_7;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class StartScreen implements Screen {
    private MonoBehaviour mainChar;
    private MonoBehaviour bedroom;

    private SpriteBatch batch;
    private Texture backgroundImage;
    private Stage stage;
    private Skin skin;
    private Sound introSong;
    private Sound buttonSound;
    private boolean gameStarted = false;

    String chosenCharacter = "Virtual Guy - Idle.png";

    @Override
    public void show() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("MyFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 48;

        BitmapFont titleFont = generator.generateFont(params);
        generator.dispose();

        batch = new SpriteBatch();
        backgroundImage = new Texture(Gdx.files.internal("Brown.png"));

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        TextButton startButton = new TextButton("Start", skin);
        startButton.setSize(400, 100);

        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = titleFont;
        Label title = new Label("OOP - SEMESTER PROJECT", titleStyle);
        Label description1 = new Label("Instructions:", titleStyle);
        Label description2 = new Label("1. Use the arrow keys to move your player.", titleStyle);
        Label description3 = new Label("2. Press E to search through closets and find riddles.", titleStyle);
        Label description4 = new Label("3. Leave the room after solving the riddles.", titleStyle);
        Label description5 = new Label("4. Repeat!", titleStyle);

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        table.add(title).padBottom(20);
        table.row();

        table.add(startButton).width(400).height(100);
        table.row();
        Label description6 = new Label("Choose your character:", titleStyle);
        table.row();

        Texture virtualGuy = new Texture(Gdx.files.internal("Virtual Guy.png"));
        Texture pinkGuy = new Texture(Gdx.files.internal("Pink Guy.png"));
        Texture frogChar = new Texture(Gdx.files.internal("Frog.png"));
        Texture maskedGuy = new Texture(Gdx.files.internal("Masked Guy.png"));

        ImageButton virtualGuyButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(virtualGuy)));
        ImageButton pinkGuyButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(pinkGuy)));
        ImageButton frogButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(frogChar)));
        ImageButton maskedGuyButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(maskedGuy)));

        virtualGuyButton.getImageCell().size(100, 100);
        virtualGuyButton.getImage().setScaling(Scaling.fit);

        pinkGuyButton.getImageCell().size(100, 100);
        pinkGuyButton.getImage().setScaling(Scaling.fit);

        maskedGuyButton.getImageCell().size(100, 100);
        maskedGuyButton.getImage().setScaling(Scaling.fit);

        frogButton.getImageCell().size(100, 100);
        frogButton.getImage().setScaling(Scaling.fit);

        Table characterRow = new Table();
        characterRow.add(virtualGuyButton).size(100, 100).padRight(30);
        characterRow.add(pinkGuyButton).size(100, 100).padRight(30);
        characterRow.add(frogButton).size(100, 100).padRight(30);
        characterRow.add(maskedGuyButton).size(100, 100);

        table.add(description6).padBottom(5).row();
        table.add(characterRow).padBottom(20).row();
        table.add(description1).padBottom(5).row();
        table.add(description2).padBottom(5).row();
        table.add(description3).padBottom(5).row();
        table.add(description4).padBottom(5).row();
        table.add(description5).padBottom(5);

        buttonSound = Gdx.audio.newSound(Gdx.files.internal("BUTTON_SOUND.mp3"));

        virtualGuyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                chosenCharacter = "Virtual Guy - Idle.png";
                buttonSound.play();

                virtualGuyButton.getImage().setColor(1, 1, 0, 1);
                pinkGuyButton.getImage().setColor(1, 1, 1, 1);
                frogButton.getImage().setColor(1, 1, 1, 1);
                maskedGuyButton.getImage().setColor(1, 1, 1, 1);
            }
        });

        pinkGuyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                chosenCharacter = "Pink Guy - Idle.png";
                buttonSound.play();

                virtualGuyButton.getImage().setColor(1, 1, 1, 1);
                pinkGuyButton.getImage().setColor(1, 1, 0, 1);
                frogButton.getImage().setColor(1, 1, 1, 1);
                maskedGuyButton.getImage().setColor(1, 1, 1, 1);
            }
        });

        frogButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                chosenCharacter = "Frog - Idle.png";
                buttonSound.play();

                virtualGuyButton.getImage().setColor(1, 1, 1, 1);
                pinkGuyButton.getImage().setColor(1, 1, 1, 1);
                frogButton.getImage().setColor(1, 1, 0, 1);
                maskedGuyButton.getImage().setColor(1, 1, 1, 1);
            }
        });

        maskedGuyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                chosenCharacter = "Masked Guy - Idle.png";
                buttonSound.play();

                virtualGuyButton.getImage().setColor(1, 1, 1, 1);
                pinkGuyButton.getImage().setColor(1, 1, 1, 1);
                frogButton.getImage().setColor(1, 1, 1, 1);
                maskedGuyButton.getImage().setColor(1, 1, 0, 1);
            }
        });

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonSound.play();
                gameStarted = true;

                mainChar = new GameCharacter(chosenCharacter, 64, 64, batch);
                bedroom  = new GameRoom(batch, (GameCharacter) mainChar);

                bedroom.start();
                mainChar.start();

                stage.clear();
            }
        });
        introSong = Gdx.audio.newSound(Gdx.files.internal("INTRO_SOUND.mp3"));
        introSong.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        if (!gameStarted) {
            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
        } else {
            introSong.pause();
            bedroom.update();
            mainChar.update();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundImage.dispose();
        stage.dispose();
        skin.dispose();
        introSong.dispose();
        if (mainChar != null) mainChar.destroy();
        if (bedroom != null) bedroom.destroy();
    }
}
