package com.gamelogic;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Camera;
import com.gamelogic.powerup.PowerUpType;
import com.mygdx.prjo.PRJO;
import com.utilities.Utils;

public class FallingObjectsHandler
{
	private static final float[] SPAWNPOSITIONS = {
		(PRJO.WORLD_WIDTH / 2) * 0.7f,
		PRJO.WORLD_WIDTH / 2,
		(PRJO.WORLD_WIDTH / 2) * 1.3f,
		};
	
	private static final float MINSPAWNINTERVAL = 0.3f;
	private ArrayList<FallingObject> m_FallingObjects;
	private float m_SpawnInterval = 1.0f;
	private float m_TimeSinceLastSpawn = 0.0f;
	private float m_SpeedModifier = 1.0f;
	private boolean m_Dead = false;
	private Player m_Player;
	
	public FallingObjectsHandler()
	{
		m_FallingObjects = new ArrayList<FallingObject>();
	}
	
	public FallingObjectsHandler(Player player)
	{
		m_FallingObjects = new ArrayList<FallingObject>();
		m_Player = player;
	}
	
	public void update(float dt)
	{
		for (int i = 0; i < m_FallingObjects.size(); i++)
		{
			m_FallingObjects.get(i).update(dt);
			if (m_FallingObjects.get(i).getPos().y < 0)
			{
				// Kill the player if a fruit hit the ground
				if (this.getType(m_FallingObjects.get(i)) != FallingObjectType.MEAT)
				{
					m_Player.kill();
					removeFallingObject(i);
				}
			}
		}
		
		spawnFallingObject(dt);
	}
	
	public void render(Camera camera)
	{
		for (int i = 0; i < m_FallingObjects.size(); i++)
		{
			m_FallingObjects.get(i).render(camera);
		}
	}
	
	public void removeFallingObject(int index)
	{
		if (index < m_FallingObjects.size())
		{
			m_FallingObjects.get(index).Dispose();
			m_FallingObjects.remove(index);
		}
	}
	
	public FallingObject getFallingObject(int index)
	{
		if (index < m_FallingObjects.size())
		{
			return m_FallingObjects.get(index);
		}
		else
		{
			return null;
		}
	}
	
	public int getNrOfFallingObjects()
	{
		return m_FallingObjects.size();
	}
	
	

	private void spawnFallingObject(float dt)
	{
		m_TimeSinceLastSpawn += dt;
		if (m_TimeSinceLastSpawn >= m_SpawnInterval)
		{
			int spawnPosition = Utils.getRandomNumber(0, SPAWNPOSITIONS.length);
			
			// Determine if it's going to be meat or fruit
			FallingObjectType type = getFallingObjectType();
			
			FallingObject fallingObject = null;
			
			switch (type) {
				case MEAT:
					// Create meat
					fallingObject = new Meat(SPAWNPOSITIONS[spawnPosition], PRJO.WORLD_HEIGHT, m_SpeedModifier);
					break;
				case FRUIT:
				default:
					// Create fruit
					boolean powerUp = false;
					
					// Determine if the fruit should contain power up
					if (Utils.getRandomNumber(0, 100) <= 10)
					{
						powerUp = true;
					}
					
					fallingObject = new Fruit(SPAWNPOSITIONS[spawnPosition], PRJO.WORLD_HEIGHT, m_SpeedModifier, powerUp);
					
					break;
			}			
			
			// Set position array for collision handling
			fallingObject.setPoisition(spawnPosition);
			// Add the fruit to the falling objects
			m_FallingObjects.add(fallingObject);	
			
			// Update spawn timer
			m_TimeSinceLastSpawn = 0.0f;
			if (m_SpawnInterval > MINSPAWNINTERVAL)
			{
				m_SpawnInterval -= 0.08f;
			}
		}
	}
	
	private FallingObjectType getFallingObjectType()
	{
		FallingObjectType type = FallingObjectType.FRUIT;
		
		int randomNr = Utils.getRandomNumber(0, 100);
		
		if (randomNr <= 25)
		{
			type = FallingObjectType.MEAT;
		}
		
		return type;
	}	
	
	public boolean isDead()
	{
		return m_Dead;
	}
	
	public void setDead(boolean dead)
	{
		m_Dead = dead;
	}
	
	/**
	 * Get FallingObject type
	 */
	public FallingObjectType getType(FallingObject fo)
	{
		FallingObjectType type = FallingObjectType.FRUIT;
		
		if (fo instanceof Meat)
		{
			type = FallingObjectType.MEAT;			
		}
		
		return type;
	}
	
	/**
	 * Apply the specified power up type to the player
	 * @param The power up type
	 */
	public void applyPowerUp(PowerUpType powerUpType)
	{
		switch (powerUpType) {
			default:
				break;
		}
	}
	
	public void removePowerUp(PowerUpType powerUpType)
	{
		switch (powerUpType) {
			default:
				break;
		}
	}
	
	public void setSpeedModifier(float speedModifier)
	{
		m_SpeedModifier = speedModifier;
		
		// Update existing falling objects
		for (int i = 0; i < m_FallingObjects.size(); i++)
		{
			m_FallingObjects.get(i).setSpeedModifer(m_SpeedModifier);
		}
	}
}
