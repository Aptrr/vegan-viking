How to add new PowerUp:

Create class inheriting from power up.

Add it to PowerUpType

Add it to addPowerUp in PowerUpHandler

Add it to handleCollision in Game

Add it to removePowerUps in Game

Add it to either FallingObjectsHandler or Player (or other), applyPowerUp and removePowerUp
