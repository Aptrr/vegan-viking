package com.mygdx.prjo.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.googleservices.IGoogleServices;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;
import com.mygdx.prjo.PRJO;

public class AndroidLauncher extends AndroidApplication implements IGoogleServices 
{
	private static final int REQUEST_CODE_UNUSED = 5555;
	private GameHelper m_GameHelper;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Create the GameHelper.
		m_GameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		m_GameHelper.enableDebugLog(false);
		
		GameHelperListener gameHelperListener = new GameHelper.GameHelperListener()
		{
			@Override
			public void onSignInSucceeded()
			{
			}
			
			@Override
			public void onSignInFailed()
			{
			}
		};
		
		m_GameHelper.setup(gameHelperListener);
		
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new PRJO(this), config);
	}

	public void signIn()
	{
		try
		{
			runOnUiThread(new Runnable()
			{
				//@Override
				public void run()
				{
					m_GameHelper.beginUserInitiatedSignIn();
				}
			});
		}
		catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void signOut()
	{
		try
		{
			runOnUiThread(new Runnable()
			{
			//@Override
				public void run()
				{
					m_GameHelper.signOut();
				}
			});
		}
		catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
		}
	}
		
	@Override
	public void rateGame()
	{
		// Replace the end of the URL with the package of your game
		String str ="https://play.google.com/store/apps/details?id=org.fortheloss.plunderperil";
		//startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
	}
	
	@Override
	public void submitScore(long score)
	{
		if (isSignedIn() == true)
		{
			Games.Leaderboards.submitScore(m_GameHelper.getApiClient(), getString(R.string.leaderboard_id), score);
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(m_GameHelper.getApiClient(), getString(R.string.leaderboard_id)), REQUEST_CODE_UNUSED);
		}
		else
		{
			// Maybe sign in here then redirect to submitting score?
		}
	}
	
	@Override
	public void showScores()
	{
		if (isSignedIn() == true)
		{
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(m_GameHelper.getApiClient(), getString(R.string.leaderboard_id)), REQUEST_CODE_UNUSED);
		}
		else
		{
			// Maybe sign in here then redirect to showing scores?
		}
	}
	
	@Override
	public boolean isSignedIn()
	{
		return m_GameHelper.isSignedIn();
	}
	
	//
	//  Methods to inform the gamehelper of the android state
	//
	@Override
	protected void onStart()
	{
		super.onStart();
		m_GameHelper.onStart(this);
	}
	
	@Override
	protected void onStop()
	{
		super.onStop();
		m_GameHelper.onStop();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		m_GameHelper.onActivityResult(requestCode, resultCode, data);
	}
	// ----------------------------------
}
