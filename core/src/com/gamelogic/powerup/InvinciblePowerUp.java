package com.gamelogic.powerup;

public class InvinciblePowerUp extends PowerUp
{
	public InvinciblePowerUp(PowerUpType powerUpType)
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
