package com.practice_7;

import com.badlogic.gdx.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class Main implements ApplicationListener {
    private StartScreen game;
    @Override
    public void create() {
        game = new StartScreen();
        game.show();
    }

    @Override
    public void resize(int width, int height) {
        game.resize(width, height);
    }

    @Override
    public void render() {
        game.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    public void hide() {}

    @Override
    public void dispose() {
        game.dispose();
    }
}

