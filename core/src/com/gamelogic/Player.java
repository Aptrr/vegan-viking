package com.gamelogic;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.gamelogic.powerup.PowerUpType;
import com.graphics.TextureManager;
import com.mygdx.prjo.PRJO;

public class Player extends Object
{
	// Private variables
	// ----------------------------------
	private int m_Score;
	private float m_ScoreMultiplier;
	private BitmapFont m_Font;
	private boolean m_MoveLeft;
	private boolean m_MoveRight;
	
	private boolean m_Alive;
	private boolean m_Invincible;
	// ----------------------------------
	
	// Constructors
	// ----------------------------------
	public Player()
	{
		m_Score = 0;
		m_ScoreMultiplier = 1.0f;
		//m_Sprite = TextureManager.getInstance().createSprite("idle", 0, TextureManager.PLAYER);
		AtlasRegion atlasRegion = TextureManager.getInstance().getAtlasRegion("idle", 0, TextureManager.PLAYER);
		m_Sprite = new Sprite(atlasRegion);
		
		float spriteX = PRJO.WORLD_WIDTH * 0.275f;
		float spriteY = PRJO.WORLD_HEIGHT * 0.19f;
		
		m_Sprite.setSize(spriteX, spriteY);
		
		// Set the sprite position
		m_Pos = new Vector2(PRJO.WORLD_WIDTH/2 - (spriteX/2), PRJO.WORLD_HEIGHT * 0.075f);
		
		// Set player to alive
		m_Alive = true;
		m_Invincible = false;
		
		// Init score font
		m_Font = new BitmapFont();
		m_Font.getData().setScale(0.09f, 0.2f);
	}
	// ----------------------------------

	// Methods
	// ----------------------------------
	public void update(float dt)
	{
		super.update(dt);
		if (m_MoveLeft)
		{
			this.m_Sprite.setRegion(TextureManager.getInstance().getAtlasRegion("attack", 1, TextureManager.PLAYER));
			this.m_Sprite.flip(true, false);
		}
		else if (m_MoveRight)
		{
			this.m_Sprite.setRegion(TextureManager.getInstance().getAtlasRegion("attack", 1, TextureManager.PLAYER));
		}
		else
		{
			this.m_Sprite.setRegion(TextureManager.getInstance().getAtlasRegion("idle", 0, TextureManager.PLAYER));
		}
	}
	
	public void render(Camera camera)
	{
		super.render(camera);
		m_SpriteBatch.begin();
		m_Font.draw(m_SpriteBatch, "Score: " + m_Score, PRJO.WORLD_WIDTH * 0.65f, PRJO.WORLD_HEIGHT * 0.95f);
		m_Sprite.draw(m_SpriteBatch);
		m_SpriteBatch.end();
	}
	
	public void setPos(int x, int y)
	{
		this.m_Pos.x = x;
		this.m_Pos.y = y;
	}
	
	public void setX(int x)
	{
		this.m_Pos.x = x;
	}
	
	public int getScore() 
	{
		return m_Score;
	}

	public void addScore() 
	{
		this.m_Score += 1 * m_ScoreMultiplier;
	}
	// ----------------------------------

	public boolean getMoveLeft()
	{
		return m_MoveLeft;
	}

	public void setMoveLeft(boolean moveLeft)
	{
		if (this.m_MoveRight && moveLeft)
		{
			this.m_MoveRight = false;
		}
		this.m_MoveLeft = moveLeft;
	}

	public boolean getMoveRight()
	{
		return m_MoveRight;
	}

	public void setMoveRight(boolean moveRight)
	{
		if (this.m_MoveLeft && moveRight)
		{
			this.m_MoveLeft = false;
		}
		this.m_MoveRight = moveRight;
	}
	
	
	/**
	 * Kill the player
	 */
	public void kill()
	{
		if (!m_Invincible)
		{
			m_Alive = false;
		}
	}
	
	/**
	 * Get whether the player is alive or dead
	 */
	public boolean isAlive()
	{
		return m_Alive;
	}
	
	/**
	 * Apply the specified power up type to the player
	 * @param The power up type
	 */
	public void applyPowerUp(PowerUpType powerUpType)
	{
		switch (powerUpType) {
			case INVINCIBLE:				
				m_Invincible = true;
				break;
			case SCORE_MULTIPLIER:
				m_ScoreMultiplier = 3.0f;
				break;
			default:
				// Do nothing
				break;
		}
	}
	
	/**
	 * Remove the specified power up type to the player
	 * @param The power up type
	 */
	public void removePowerUp(PowerUpType powerUpType)
	{
		switch (powerUpType) {
			case INVINCIBLE:
				m_Invincible = false;
				break;
			case SCORE_MULTIPLIER:
				m_ScoreMultiplier = 1.0f;
				break;
			default:
				break;
		}
	}
}
