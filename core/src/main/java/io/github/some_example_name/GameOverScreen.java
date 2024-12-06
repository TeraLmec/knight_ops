package io.github.some_example_name;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.some_example_name.FirstScreen;
import com.badlogic.gdx.utils.TimeUtils;
import io.github.some_example_name.AssetLoader;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameOverScreen {
    private final Stage stage;
    private final BitmapFont font;
    private final SpriteBatch firstScreen;
    private final BitmapFont score;
    private final FirstScreen round;
    private long deathTime = 0;
    private float fadeAlpha = 0f;
    private static final float FADE_DURATION = 3f;

    public GameOverScreen(SpriteBatch batch, BitmapFont font2, FirstScreen round) {
        this.firstScreen = batch;
        this.score = font2;
        this.round = round;
        this.stage = new Stage(new ScreenViewport());
        this.font = new BitmapFont();
        font.getData().setScale(4.0f);

        Skin skin = new Skin(Gdx.files.internal("assets/skin/tracer-ui.json"));

        TextButton restartButton = new TextButton("Recommencer la partie", skin);
        TextButton quitButton = new TextButton("Quitter le jeu", skin);

        Sound buttonHoverSound = AssetLoader.getSound("button_hover");
        float pitch = Settings.MIN_PITCH + new Random().nextFloat() * (Settings.MAX_PITCH - Settings.MIN_PITCH);

        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("restart button proc");
                round.restartGame();
                FirstScreen.setGameOver(false);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                buttonHoverSound.play(Settings.BUTTON_HOVER_VOLUME, pitch, 0);
            }
        });

        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Quit button proc");
                Gdx.app.exit();
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                buttonHoverSound.play(Settings.BUTTON_HOVER_VOLUME, pitch, 0);
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.add(restartButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(quitButton).fillX().uniformX();

        stage.addActor(table);
    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCursorCatched(false);
    }

    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 1f);
        stage.act(delta);
        stage.draw();

        if (fadeAlpha < 1f) {
            fadeAlpha += delta / FADE_DURATION;
            if (fadeAlpha > 1f) {
                fadeAlpha = 1f;
            }
        }

        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, fadeAlpha);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();
        shapeRenderer.dispose();

        SpriteBatch batch = firstScreen;
        batch.begin();
        font.draw(batch, "Game Over", Gdx.graphics.getWidth() / 2f - 50, Gdx.graphics.getHeight() / 2f + 60);
        font.draw(batch, "Score: " + score, Gdx.graphics.getWidth() / 2f - 50, Gdx.graphics.getHeight() / 2f + 30);
        font.draw(batch, "Round: " + round, Gdx.graphics.getWidth() / 2f - 50, Gdx.graphics.getHeight() / 2f);
        batch.end();
    }

    public void hide() {
        Gdx.input.setInputProcessor(round.getPlayer());
    }

    public void dispose() {
        stage.dispose();
        font.dispose();
    }
    
    public void updateViewport() {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    public void setDeathTime(long deathTime) {
        this.deathTime = deathTime;
    }

    public boolean canShowGameOverMenu() {
        return TimeUtils.timeSinceMillis(deathTime) > 500;
    }
}