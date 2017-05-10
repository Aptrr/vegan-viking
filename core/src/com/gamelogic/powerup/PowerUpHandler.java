package com.gamelogic.powerup;
import java.util.ArrayList;

public class PowerUpHandler
{
	
	private ArrayList<PowerUp> m_PowerUps;
	
	public PowerUpHandler()
	{
		m_PowerUps = new ArrayList<PowerUp>();
	}
	
	/**
	 * Update the power up manager
	 * @param dt
	 * @return The power up type if a power up effect should be removed, otherwise the PowerUpType.NONE type
	 */
	public PowerUpType update(float dt)
	{
		PowerUpType powerUpType = null;
		
		for (int i = 0; i < m_PowerUps.size(); i++)
		{
			m_PowerUps.get(i).update(dt);
			
			if (m_PowerUps.get(i).isFinished())
			{
				powerUpType = m_PowerUps.get(i).getPowerUpType();
				m_PowerUps.remove(i);
			}
		}
		
		return powerUpType;
	}
	
	public void addPowerUp(PowerUpType powerUpType)
	{
		int powerUpActive = isPowerUpTypeActive(powerUpType);
		
		if (powerUpActive == -1)
		{
			switch (powerUpType) {
				case INVINCIBLE:
					m_PowerUps.add(new InvinciblePowerUp(powerUpType));
					break;
				case SCORE_MULTIPLIER:
					m_PowerUps.add(new ScorePowerUp(powerUpType));
				default:
					// Do nothing
					break;
			}
		}
		else
		{
			switch (powerUpType) {
				case INVINCIBLE:
					// Reset the time if the power up is already active
					m_PowerUps.get(powerUpActive).resetTimeActive();
					break;

				default:
					// Do nothing
					break;
			}
		}
	}
	
	/**
	 * Determine if the specified power up type is active
	 * @param powerUpType
	 * @return -1 if it isn't active, otherwise the arraylist position
	 */
	private int isPowerUpTypeActive(PowerUpType powerUpType)
	{
		int active = -1;
		
		for (int i = 0; i < m_PowerUps.size(); i++)
		{
			if (m_PowerUps.get(i).getPowerUpType() == powerUpType)
			{
				active = i;
				i = m_PowerUps.size();
			}
		}
		
		return active;
	}
}
