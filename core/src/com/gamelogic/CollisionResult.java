package com.gamelogic;

public class CollisionResult
{
	public FallingObjectType fallingObjectType;
	public boolean powerUp;
	
	public CollisionResult(FallingObjectType fallingObjectType, boolean powerUp)
	{
		this.fallingObjectType = fallingObjectType;
		this.powerUp = powerUp;
	}
}
