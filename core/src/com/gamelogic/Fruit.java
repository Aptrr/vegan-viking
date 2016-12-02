package com.gamelogic;

import sun.print.PeekGraphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter.Particle;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.graphics.TextureManager;
import com.mygdx.prjo.PRJO;

public class Fruit extends FallingObject
{
	private static final float FALLSPEED = 85.0f;
	
	// Particle effect
	private ParticleEffect m_ParticleEffect;
	
	public Fruit(float x, float y, float speedModifier, boolean powerUp)
	{
		super(speedModifier);
		
		m_Velocity = FALLSPEED;
		m_Dir = new Vector2(0.0f, -1.0f);
		AtlasRegion atlasRegion = TextureManager.getInstance().getRandomFruit();
		m_Sprite = new Sprite(atlasRegion);
		m_Sprite.setSize(PRJO.WORLD_WIDTH * 0.1f, PRJO.WORLD_HEIGHT * 0.1f);
		m_Pos = new Vector2(x - (m_Sprite.getWidth()/2), y - (m_Sprite.getHeight()/2));
		
		if (powerUp)
		{
			m_PowerUp = true;
			m_ParticleEffect = new ParticleEffect();
			m_ParticleEffect.load(Gdx.files.internal("pfx\\powerup_blue.ole"), Gdx.files.internal("pfx\\"));
			m_ParticleEffect.scaleEffect(0.1f);
		}
	}
	
	public void update(float dt)
	{
		super.update(dt);
		
		m_Pos.y -= m_Velocity * m_SpeedModifier * dt;
		
		// Update particle effect
		if (m_ParticleEffect != null)
		{
			m_ParticleEffect.update(dt);
			m_ParticleEffect.getEmitters().first().setPosition(m_Pos.x + (m_Sprite.getWidth()/2), m_Pos.y + (m_Sprite.getWidth()/2));
		}
	}
	
	public void render(Camera camera)
	{
		super.render(camera);
		m_SpriteBatch.begin();
		m_Sprite.draw(m_SpriteBatch);
		
		// Render particle effect
		if (m_ParticleEffect != null)
		{
			m_ParticleEffect.draw(m_SpriteBatch);
			
			if (m_ParticleEffect.isComplete())
			{
				m_ParticleEffect.reset();
			}
		}
		
		m_SpriteBatch.end();		
	}
	
	@Override
	public void Dispose()
	{
		super.Dispose();
	}
}
