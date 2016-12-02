package com.gamelogic;

import com.badlogic.gdx.graphics.Camera;
import com.gamelogic.powerup.PowerUpHandler;
import com.gamelogic.powerup.PowerUpType;
import com.mygdx.prjo.PRJO;

public class Game
{
	// Private variables
	// ----------------------------------
	private Player m_Player;
	private long m_StartTime;
	private boolean m_Running;
	private Camera m_Camera;
	
	// Handlers
	private FallingObjectsHandler m_FallingObjectsHandler;
	private CollisionHandler m_CollisionHandler;
	private PowerUpHandler m_PowerUpHandler;	
	// ----------------------------------
	
	// Constructors
	// ----------------------------------
	public Game(Camera camera)
	{
		m_Player = new Player();
		m_FallingObjectsHandler = new FallingObjectsHandler(m_Player);
		m_CollisionHandler = new CollisionHandler();
		m_PowerUpHandler = new PowerUpHandler();
		
		m_StartTime = 0;
		m_Running = false;
		m_Camera = camera;
	}
	// ----------------------------------
	
	// Methods
	// ----------------------------------
	public void startGame()
	{
		m_StartTime = System.currentTimeMillis();
		m_Running = true;
	}
	
	public void update(float dt)
	{
		m_Player.update(dt);
		m_FallingObjectsHandler.update(dt);
		
		// Handle power ups
		PowerUpType powerUpType = m_PowerUpHandler.update(dt);
		if (powerUpType != null)
		{
			removePowerUps(powerUpType);
		}
		
		// Check for collisions
		CollisionResult collisionType = checkCollisions();
		if (collisionType != null)
		{
			handleCollision(collisionType);
		}
		
		
		if (!m_Player.isAlive())
		{
			PRJO.m_GoogleServices.submitScore(m_Player.getScore());
			m_Running = false;
		}
	}
	
	public void render()
	{
		m_Player.render(m_Camera);
		m_FallingObjectsHandler.render(m_Camera);
	}
	
	
	private CollisionResult checkCollisions()
	{
		return m_CollisionHandler.colliding(m_Player, m_FallingObjectsHandler);
	}
	
	private void handleCollision(CollisionResult collisionResult)
	{
		switch (collisionResult.fallingObjectType) {
			case FRUIT:
				// Update player score
				m_Player.addScore();
				
				// Add power up if the fruit has a power up
				if (collisionResult.powerUp)
				{
					PowerUpType powerUpType = PowerUpType.getRandomPowerUpType();
					// Add the power up to the power up handler
					System.out.println(powerUpType.toString());
					m_PowerUpHandler.addPowerUp(powerUpType);
					
					switch (powerUpType) {
						case SCORE_MULTIPLIER:
						case INVINCIBLE:
							// Apply the power up effect to the player
							m_Player.applyPowerUp(powerUpType);
							break;
						case SLOW_MOTION:
							// Apply power up to the falling objects handler
							m_FallingObjectsHandler.applyPowerUp(powerUpType);
							break;
						default:
							break;
					}
				}
				break;
				
			case MEAT:
				m_Player.kill();
				break;

			default:
				// Do nothing
				break;
		}
	}
	
	private void removePowerUps(PowerUpType powerUpType)
	{
		switch (powerUpType) {
			case SCORE_MULTIPLIER:
			case INVINCIBLE:
				m_Player.removePowerUp(powerUpType);
				break;
			case SLOW_MOTION:
				m_FallingObjectsHandler.removePowerUp(powerUpType);
				break;
			default:
				break;
		}
	}
	
	private long getElapsedTime()
	{
		return System.currentTimeMillis() - m_StartTime;
	}
	
	public boolean isRunning()
	{
		return m_Running;
	}
	
	public int getScore()
	{
		return m_Player.getScore();
	}
	
	public Player getPlayer()
	{
		return this.m_Player;
	}
	// ----------------------------------
}
