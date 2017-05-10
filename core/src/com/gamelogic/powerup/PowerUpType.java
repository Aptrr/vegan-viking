package com.gamelogic.powerup;

import com.utilities.Utils;

public enum PowerUpType
{
	/**
	 * Make the player invincible
	 */
	INVINCIBLE,
	/**
	 * Multiply the score
	 */
	SCORE_MULTIPLIER;
	
	public static PowerUpType getRandomPowerUpType()
	{
		PowerUpType[] powerUpTypes = PowerUpType.values();
		
		return powerUpTypes[Utils.getRandomNumber(0, powerUpTypes.length)];
	}
}
