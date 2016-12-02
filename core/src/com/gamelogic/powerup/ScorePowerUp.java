package com.gamelogic.powerup;

public class ScorePowerUp extends PowerUp
{
	public ScorePowerUp(PowerUpType powerUpType)
	{
		super(powerUpType);
		
		m_Duration = 10.0f;
		m_TimeActive = 0.0f;
	}
	
	public void update(float dt)
	{
		super.update(dt);
	}
}
