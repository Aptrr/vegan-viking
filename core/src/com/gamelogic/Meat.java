package com.gamelogic;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.graphics.TextureManager;
import com.mygdx.prjo.PRJO;

public class Meat extends FallingObject
{
	private static final float FALLSPEED = 85.0f;
	
	public Meat(float speedModifier)
	{
		super(speedModifier);
		m_Velocity = FALLSPEED;
		m_Pos = new Vector2(50.0f, 250.0f);
		m_Dir = new Vector2(0.0f, -1.0f);
	}
	
	public Meat(float x, float y, float speedModifier)
	{
		super(speedModifier);
		
		m_Velocity = FALLSPEED;
		m_Dir = new Vector2(0.0f, -1.0f);
		AtlasRegion atlasRegion = TextureManager.getInstance().getRandomMeat();
		m_Sprite = new Sprite(atlasRegion);
		m_Sprite.setSize(PRJO.WORLD_WIDTH * 0.1f, PRJO.WORLD_HEIGHT * 0.1f);
		m_Pos = new Vector2(x - (m_Sprite.getWidth()/2), y - (m_Sprite.getHeight()/2));
	}
	
	public void update(float dt)
	{
		super.update(dt);
		
		m_Pos.y -= m_Velocity * m_SpeedModifier * dt; 
	}
	
	public void render(Camera camera)
	{
		super.render(camera);
		m_SpriteBatch.begin();
		m_Sprite.draw(m_SpriteBatch);
		m_SpriteBatch.end();
	}
	
	@Override
	public void Dispose()
	{
		super.Dispose();
	}
}
