package com.graphics;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

// Singleton responsible for loading textures and providing textures

public class TextureManager
{
	// Singleton
	private static TextureManager instance = null;
	
	// Fruit variables
	private TextureAtlas m_FruitTextures;
	private Array<AtlasRegion> m_FruitAtlasRegions;
	
	// Meat variables
	private TextureAtlas m_MeatTextures;
	private Array<AtlasRegion> m_MeatAtlasRegions;
	
	
	// Player variables
	private TextureAtlas m_PlayerTextures;
	
	private Texture m_BackgroundTexture;
	
	private Hashtable<String, AtlasRegion> m_LoadedRegions;
	
	// Constant variables
	public static final int FRUIT = 0;
	public static final int PLAYER = 1;
	
	public TextureManager()
	{
		if (!initialize())
		{
			System.out.println("Could not initialize TextureManager");
		}
	}
	
	public static TextureManager getInstance()
	{
		if (instance == null)
		{
			instance = new TextureManager();
		}
		return instance;
	}
	
	// Load relevant textures
	public boolean initialize()
	{
		boolean initialized = true;
		
		// Load fruit textures
		m_FruitTextures = new TextureAtlas(Gdx.files.internal("vegies_output/vegies.atlas"));
		m_FruitAtlasRegions = m_FruitTextures.getRegions();
		
		// Load meat textures
		m_MeatTextures = new TextureAtlas(Gdx.files.internal("meat_output/meat.atlas"));
		m_MeatAtlasRegions = m_MeatTextures.getRegions();
		
		// Load player texture
		m_PlayerTextures = new TextureAtlas(Gdx.files.internal("character_output/viking.atlas"));
		
		
		m_LoadedRegions = new Hashtable<String, TextureAtlas.AtlasRegion>();
		
		return initialized;
	}
	
	/**
	 * Get texture for the specified atlas item
	 * @param Name of the image in the atlas
	 * @param Index. If index < 0 then only request the name
	 * @param Type to find.
	 * @return AtlasRegion. Can be null if no valid texture is found
	 */
	public AtlasRegion getAtlasRegion(String atlasName, int index, int type)
	{
		AtlasRegion atlasRegion = null;
		TextureAtlas textureAtlas = null;
		try
		{
			// Use the specified texture atlas
			if (type == PLAYER)
			{
				textureAtlas = m_PlayerTextures;
			}
			else if (type == FRUIT)
			{
				textureAtlas = m_FruitTextures;
			}
			else
			{
				System.out.println("Invalid type specified in TextureManager.getAtlasRegion");
			}
			
			
			atlasRegion = getAtlasRegion(textureAtlas, atlasName, index);
			
			if (atlasRegion == null)
			{
				throw new Exception(String.format("Unable to find an atlas region for values key: %s, index: %d", atlasName, index));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return atlasRegion;
	}
	
	/**
	 * Create sprite for the specified atlas item
	 * @param Name of the image in the atlas
	 * @param Index. If index < 0 then only request the name
	 * @param Type to find.
	 * @return Sprite. Texture can be null if no valid texture is found
	 */
	public Sprite createSprite(String atlasName, int index, int type)
	{
		Sprite sprite = null;
		switch (type) {
			case FRUIT:
				if (index < 0)
				{
					sprite = m_FruitTextures.createSprite(atlasName, index);
				}
				else
				{
					sprite = m_FruitTextures.createSprite(atlasName);
				}
				break;
			case PLAYER:
				if (index < 0)
				{
					sprite = m_PlayerTextures.createSprite(atlasName, index);
				}
				else
				{
					sprite = m_PlayerTextures.createSprite(atlasName);
				}
				break;
			default:
				System.out.println("Invalid type specified in TextureManager.createSprite");
				break;
		}
		
		return sprite;
	}
	
	
	/**
	 * Get random fruit from the loaded fruit textures
	 * @return AtlasRegion
	 */
	public AtlasRegion getRandomFruit()
	{		
		// Get a random number within the altas region
		Random ran = new Random();
		int nr = 0 + ran.nextInt((m_FruitAtlasRegions.size - 1) - 0 + 1);
		
		return m_FruitAtlasRegions.get(nr);
	}
	
	/**
	 * Get random meat from the loaded fruit textures
	 * @return AtlasRegion
	 */
	public AtlasRegion getRandomMeat()
	{		
		// Get a random number within the altas region
		Random ran = new Random();
		int nr = 0 + ran.nextInt((m_MeatAtlasRegions.size - 1) - 0 + 1);
		
		return m_MeatAtlasRegions.get(nr);
	}
	
	public void dispose()
	{
		this.m_BackgroundTexture.dispose();
		this.m_FruitTextures.dispose();
		this.m_PlayerTextures.dispose();
		
		// Remove atlas regions in the hashtable
		Enumeration<String> enumKey = m_LoadedRegions.keys();
		while(enumKey.hasMoreElements()) 
		{
		    String key = enumKey.nextElement();
		    m_LoadedRegions.remove((key));
		}
	}
	
	/**
	 * Find if the atlas region is loaded. If it isn't, load it and store it in the hashtable
	 * @param key to search for
	 * @return boolean
	 */
	private boolean isAtlasRegionLoaded(String key)
	{
		if (m_LoadedRegions.containsKey(key))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Find if the atlas region is loaded. 
	 * @param key to search for
	 * @param index
	 * @return boolean
	 */
	private boolean isAtlasRegionLoaded(String key, int index)
	{
		String keyWithIndex = key + index;
		if (m_LoadedRegions.containsKey(keyWithIndex))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Get AtlasRegion. Will get AtlasRegion from the cache if available
	 * @param textureAtlas
	 * @param key
	 * @param index
	 * @return
	 */
	private AtlasRegion getAtlasRegion(TextureAtlas textureAtlas, String key, int index)
	{
		AtlasRegion atlasRegion;
		if (index < 0)
		{
			if (isAtlasRegionLoaded(key))
			{
				atlasRegion = m_LoadedRegions.get(key);
			}
			else
			{
				atlasRegion = textureAtlas.findRegion(key);
				addToCache(key, atlasRegion);
			}
		}
		else
		{
			// key used to store key + index in hashtable
			String keyWithIndex = key + index;
			
			if (isAtlasRegionLoaded(keyWithIndex))
			{
				atlasRegion = m_LoadedRegions.get(keyWithIndex);
			}
			else
			{
				atlasRegion = textureAtlas.findRegion(key, index);
				addToCache(keyWithIndex, atlasRegion);
			}
		}
		
		return atlasRegion;
	}
	
	private boolean addToCache(String key, AtlasRegion atlasRegion)
	{
		boolean addedToCache = false;
		
		if (atlasRegion != null)
		{
			m_LoadedRegions.put(key, atlasRegion);
		}
		
		return addedToCache;
	}
}
