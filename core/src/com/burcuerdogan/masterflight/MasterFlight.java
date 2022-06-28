package com.burcuerdogan.masterflight;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class MasterFlight extends ApplicationAdapter {

	SpriteBatch batch;
	Texture background;
	Texture airship;
	Texture enemy1;
	Texture enemy2;
	Texture enemy3;
	float airshipX = 0;
	float airshipY = 0;
	int gameState = 0;
	float velocity = 0;
	float gravity = 0.1f;

	//float enemyX = 0;
	int numberOfEnemySet = 4;
	float[] enemyX = new float[numberOfEnemySet];
	float distance = 0;
	float enemyVelocity = 2;
	float[] enemyOffSet = new float[numberOfEnemySet];
	float[] enemyOffSet2 = new float[numberOfEnemySet];
	float[] enemyOffSet3 = new float[numberOfEnemySet];
	Random random;

	Circle airshipCircle;
	Circle[] enemyCircles;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;

	ShapeRenderer shapeRenderer;

	int score = 0;
	int scoredEnemy = 0;
	BitmapFont font;
	BitmapFont font2;


	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.png");
		airship = new Texture("airship.png");
		airshipX = Gdx.graphics.getWidth() / 3;
		airshipY = Gdx.graphics.getHeight() / 3;

		airshipCircle = new Circle();
		enemyCircles = new Circle[numberOfEnemySet];
		enemyCircles2 = new Circle[numberOfEnemySet];
		enemyCircles3 = new Circle[numberOfEnemySet];

		shapeRenderer = new ShapeRenderer();

		enemy1 = new Texture("enemy1.png");
		enemy2 = new Texture("enemy2.png");
		enemy3 = new Texture("enemy3.png");

		distance = Gdx.graphics.getWidth() / 2;
		random = new Random();

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);

		font2 = new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(6);

		for (int i=0; i<numberOfEnemySet; i++) {

			enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()-200);
			enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()-200);
			enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()-200);

			enemyX[i] = Gdx.graphics.getWidth() - enemy1.getWidth() / 2 + i * distance;

			enemyCircles[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();
		}
	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if (gameState == 1) {

			if (enemyX[scoredEnemy] < Gdx.graphics.getWidth()/2 - airship.getHeight()/2) {
				score++;

				if (scoredEnemy < numberOfEnemySet -1) {
					scoredEnemy++;
				} else {
					scoredEnemy = 0;
				}
			}

			if (Gdx.input.justTouched()) {
				velocity = -5;
			}

			for (int i= 0; i<numberOfEnemySet; i++) {

				if (enemyX[i] < 0) {
					enemyX[i] = enemyX[i] + numberOfEnemySet * distance;

					enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()-200);
					enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()-200);
					enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()-200);

				} else {
					enemyX[i] = enemyX[i] - enemyVelocity;
				}

				batch.draw(enemy1,enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffSet[i],Gdx.graphics.getWidth() / 15,Gdx.graphics.getHeight() / 10);
				batch.draw(enemy2,enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffSet2[i],Gdx.graphics.getWidth() / 15,Gdx.graphics.getHeight() / 10);
				batch.draw(enemy3,enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffSet3[i],Gdx.graphics.getWidth() / 15,Gdx.graphics.getHeight() / 10);

				enemyCircles[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight()/2 + enemyOffSet[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);
				enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight()/2 + enemyOffSet2[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);
				enemyCircles3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight()/2 + enemyOffSet3[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);

			}

			if (airshipY > 0) {
				velocity = velocity + gravity;
				airshipY = airshipY - velocity;
			} else {
				gameState = 2;
			}


		} else if (gameState == 0){
			if (Gdx.input.justTouched()) {
				gameState = 1;
			}
		} else if (gameState == 2) {

			font2.draw(batch,"Game Over! Tap To Play Again!",370,Gdx.graphics.getHeight()/2 +100);

			if (Gdx.input.justTouched()) {
				gameState = 1;

				airshipY = Gdx.graphics.getHeight() / 3;

				for (int i=0; i<numberOfEnemySet; i++) {

					enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()-200);
					enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()-200);
					enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()-200);

					enemyX[i] = Gdx.graphics.getWidth() - enemy1.getWidth() / 2 + i * distance;

					enemyCircles[i] = new Circle();
					enemyCircles2[i] = new Circle();
					enemyCircles3[i] = new Circle();
				}

				velocity = 0;
				scoredEnemy = 0;
				score = 0;

			}
		}

		batch.draw(airship,airshipX,airshipY,Gdx.graphics.getWidth()/15, Gdx.graphics.getHeight()/10);

		font.draw(batch,String.valueOf(score),100,200);

		batch.end();

		airshipCircle.set(airshipX+Gdx.graphics.getWidth()/30,airshipY+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);

		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(airshipCircle.x,airshipCircle.y,airshipCircle.radius);

		for (int i = 0; i<numberOfEnemySet; i++) {
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight()/2 + enemyOffSet[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight()/2 + enemyOffSet2[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight()/2 + enemyOffSet3[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);

			if (Intersector.overlaps(airshipCircle,enemyCircles[i]) || Intersector.overlaps(airshipCircle,enemyCircles2[i]) || Intersector.overlaps(airshipCircle,enemyCircles3[i])) {
				gameState = 2;
			}
		}

		//shapeRenderer.end();

	}
	
	@Override
	public void dispose () {
	}
}
