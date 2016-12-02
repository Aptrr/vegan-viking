package com.mygdx.prjo;

import javax.swing.JOptionPane;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gamelogic.InputManager;
import com.googleservices.IGoogleServices;

public class PRJO extends ApplicationAdapter 
{
	// Private member variables
	// ------------------------------------
	private SpriteBatch m_SpriteBatch;
	private BitmapFont m_ScoreFont;
	private BitmapFont m_InstructionFont;
	private com.gamelogic.Game m_Game;
	private InputManager m_InputManager;
	private Sprite m_Background;
	private Camera m_Camera;
	private Viewport m_Viewport;
	// ------------------------------------
	
	
	// Public member variable
	// ------------------------------------
	public static final float WORLD_WIDTH = 50;
    public static final float WORLD_HEIGHT = 100;
    public static IGoogleServices m_GoogleServices;
	// ------------------------------------
	
    public PRJO(IGoogleServices googleServices)
	{
		super();
		m_GoogleServices = googleServices;
	}
    
	@Override
	public void create() 
	{
		try
		{
			PRJO.m_GoogleServices.signIn();
			m_SpriteBatch = new SpriteBatch();
			m_ScoreFont = new BitmapFont();
			m_InstructionFont = new BitmapFont();
			m_Game = new com.gamelogic.Game(m_Camera);
			
			// Set Gdx to use custom input manager
			m_InputManager = new InputManager();
			Gdx.input.setInputProcessor(m_InputManager);
			
			// Create camera and viewport
			float aspectRatio = (float)Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();
			m_Camera = new OrthographicCamera();
			
			// Init viewport
			m_Viewport = new StretchViewport(WORLD_WIDTH * aspectRatio, WORLD_HEIGHT, m_Camera);
			m_Viewport.apply();
			
			// Set camera position to the center of the world
			m_Camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2, 0);
			
			// Initialize background sprite
			m_Background = new Sprite(new Texture("background1.png"));
			m_Background.setSize(WORLD_WIDTH, WORLD_HEIGHT);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void render() 
	{
		try
		{
			m_Camera.update();
			
			Gdx.gl.glClearColor(1, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			m_SpriteBatch.setProjectionMatrix(m_Camera.combined);
			m_SpriteBatch.begin();
			m_Background.draw(m_SpriteBatch);
			m_SpriteBatch.end();
			
			if (m_Game.isRunning())
			{
				handleGameLogic();
			}
			else
			{
				handleMenu();
			}
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Fel", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public com.gamelogic.Game getGame()
	{
		return this.m_Game;
	}
	
	@Override
	public void resize(int width, int height)
	{
		m_Viewport.update(width, height);
		m_Camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2, 0);
	}
	
	// Start game
	public void startGame()
	{
		if (m_Game != null)
		{
			m_Game = null;
		}
		m_Game = new com.gamelogic.Game(m_Camera);
		m_Game.startGame();
	}
	
	// Update game state and render the game scene
	private void handleGameLogic()
	{
		m_Game.update(Gdx.graphics.getDeltaTime());
		m_Game.render();
	}
	
	// Handle menu and render the menu scene
	private void handleMenu()
	{
		m_SpriteBatch.begin();
		
		// Show score if a game has been played
		if (m_Game.getScore() > 0)
		{
			m_ScoreFont.draw(m_SpriteBatch, "Score: " + m_Game.getScore(), Gdx.graphics.getWidth()/2 - 100, (Gdx.graphics.getHeight()/2) + 50);
		}
		m_InstructionFont.draw(m_SpriteBatch, "Press enter to start the game", Gdx.graphics.getWidth()/2 - 100, Gdx.graphics.getHeight()/2);
		
		m_SpriteBatch.end();
	}
}
