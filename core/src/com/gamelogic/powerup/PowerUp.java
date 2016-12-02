package com.gamelogic.powerup;

public class PowerUp
{
	protected float m_Duration;
	protected float m_TimeActive;
	protected PowerUpType m_PowerUpType;
	
	public PowerUp(PowerUpType powerUpType)
	{
		m_Duration 	  = 0.0f;
		m_TimeActive  = 0.0f;
		m_PowerUpType = powerUpType;
	}
	
	public void update(float dt)
	{
		m_TimeActive += dt;
	}
	
	public boolean isFinished()
	{
		if (m_Duration <= m_TimeActive)
		{
			return true;
		}
		
		return false;
	}
	
	public PowerUpType getPowerUpType()
	{
		return m_PowerUpType;
	}
	
	/**
	 * The time active will be reset if a player pick up the same power up type
	 */
	public void resetTimeActive()
	{
		m_TimeActive = 0.0f;
	}
}
