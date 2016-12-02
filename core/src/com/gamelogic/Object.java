package com.gamelogic;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Object
{
	// Private variables
	// ----------------------------------
	protected Vector2 m_Pos;
	protected Vector2 m_Dir;
	protected Sprite m_Sprite;
	protected SpriteBatch m_SpriteBatch;
	// ----------------------------------
	
	// Constructors
	// ----------------------------------
	public Object()
	{
		m_SpriteBatch = new SpriteBatch();
	}
	// ----------------------------------
	
	
	
	// Methods
	// ----------------------------------
	public void update(float dt)
	{
		
	}
	
	public void render(Camera camera)
	{
		m_SpriteBatch.setProjectionMatrix(camera.combined);
		m_Sprite.setPosition(m_Pos.x, m_Pos.y);
	}

	public Vector2 getPos()
	{
		return m_Pos;
	}

	public void setPos(Vector2 m_Pos)
	{
		this.m_Pos = m_Pos;
	}

	public Vector2 getDir()
	{
		return m_Dir;
	}

	public void setDir(Vector2 m_Dir)
	{
		this.m_Dir = m_Dir;
	}

	public Sprite getSprite()
	{
		return m_Sprite;
	}
	
	public void Dispose()
	{
		m_SpriteBatch.dispose();
	}
	// ----------------------------------
}
