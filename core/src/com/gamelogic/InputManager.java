package com.gamelogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.prjo.PRJO;

public class InputManager implements InputProcessor
{
	private PRJO m_App;
	
	public InputManager()
	{
		this.m_App = (PRJO) Gdx.app.getApplicationListener();
	}

	@Override
	public boolean keyDown(int keycode)
	{
		switch (keycode) {
			case Input.Keys.LEFT:
				m_App.getGame().getPlayer().setMoveLeft(true);
				break;
			case Input.Keys.RIGHT:
				m_App.getGame().getPlayer().setMoveRight(true);
			break;
			
			// Start new game if no game is running
			case Input.Keys.ENTER:
				if (!m_App.getGame().isRunning())
				{
					m_App.startGame();
				}
				break;
			default:
				break;
		}
		
		return true;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		switch (keycode) {
			case Input.Keys.LEFT:
				m_App.getGame().getPlayer().setMoveLeft(false);
				break;
			case Input.Keys.RIGHT:
				m_App.getGame().getPlayer().setMoveRight(false);
			break;
			default:
				break;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		System.out.println(screenX + " width: " + Gdx.graphics.getWidth()/2);
		if (screenX < Gdx.graphics.getWidth()/2)
		{
			m_App.getGame().getPlayer().setMoveLeft(true);
		}
		else
		{
			System.out.println("right");
			m_App.getGame().getPlayer().setMoveRight(true);
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		// Start game if the game is not running
		if (!m_App.getGame().isRunning())
		{
			m_App.startGame();
		}
		// The player is not moving if no touch is happening
		else
		{
			m_App.getGame().getPlayer().setMoveLeft(false);
			m_App.getGame().getPlayer().setMoveRight(false);
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount)
	{
		// TODO Auto-generated method stub
		return false;
	}
}
