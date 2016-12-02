package com.gamelogic;

public enum FallingObjectType
{
	NO_COLLISION(-1),
	FRUIT(0), 
	MEAT(1);
	
	private final int type;
	private FallingObjectType(int value)
	{
		type = value;
	}
	
	public int getValue() { return type; }
}
