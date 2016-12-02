package com.gamelogic;

import com.mygdx.prjo.PRJO;


// This class handles the very simple collision detected required for this game
public class CollisionHandler
{
	
	public CollisionHandler()
	{
	}
	
	public CollisionResult colliding(Player player, FallingObjectsHandler foh)
	{
		CollisionResult collision = null;
		
		for (int i = 0; i < foh.getNrOfFallingObjects(); i++)
		{
			// Make sure the object is at or below the player height
			if (foh.getFallingObject(i).getPos().y <= player.getSprite().getY() + (player.getSprite().getHeight() * 0.75)
					&& foh.getFallingObject(i).getPos().y > PRJO.WORLD_HEIGHT * 0.1f)
			{
				// Decide whether it was a meat or a fruit that collided with the player
				FallingObjectType type = foh.getType(foh.getFallingObject(i));
				
				switch (foh.getFallingObject(i).getPosition()) {
					case 0:
						if (player.getMoveLeft())
						{
							collision = new CollisionResult(type, foh.getFallingObject(i).isPowerUp());
							foh.removeFallingObject(i);
						}
						break;
					case 1:
						if (!player.getMoveLeft() && !player.getMoveRight())
						{
							collision = new CollisionResult(type, foh.getFallingObject(i).isPowerUp());
							foh.removeFallingObject(i);
						}
						break;
					case 2:
						if (player.getMoveRight())
						{
							collision = new CollisionResult(type, foh.getFallingObject(i).isPowerUp());
							foh.removeFallingObject(i);
						}
						break;
					default:
						foh.removeFallingObject(i);
						break;
				}
			}
		}
		
		return collision;
	}
}
