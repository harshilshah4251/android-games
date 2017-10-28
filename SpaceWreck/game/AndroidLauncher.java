package com.SpaceWreck.game;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.SpaceWreck.ads.AdHandler;
import com.SpaceWreck.playServices.PlayServices;

public class AndroidLauncher extends AndroidApplication implements PlayServices, AdHandler {

	private GameHelper gameHelper;
	private final static int requestCode = 1;
	private final static String BANNER_AD_UNIT_ID = "ca-app-pub-5722215799140796/9092493061";
	private final static String TESTBANNER_AD_UNIT_ID = "ca-app-pub-3940256099942544/6300978111";
	private final static String INTERSTITIAL_AD_UNIT_ID= "ca-app-pub-5722215799140796/8423011862";
	private final static String TESTINTERSTITIAL_AD_UNIT_ID= "ca-app-pub-3940256099942544/1033173712";

	private static final String TAG = "AndroidLauncher";
	protected AdView bannerAdView;
	protected InterstitialAd interstitialAd;



	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer= true;
		config.useWakelock=true;



		View gameView= initializeForView(new MainGame(this, this), config);
		setUpAds();


		RelativeLayout layout = new RelativeLayout(this);
		layout.addView(gameView, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		RelativeLayout.LayoutParams adParams= new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
		);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		adParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		layout.addView(bannerAdView, adParams);
		setContentView(layout);


		gameHelper= new GameHelper(this, GameHelper.CLIENT_GAMES);
		gameHelper.enableDebugLog(false);

		GameHelper.GameHelperListener gameHelperListener= new GameHelper.GameHelperListener() {
			@Override
			public void onSignInFailed() {

			}

			@Override
			public void onSignInSucceeded() {

			}
		};
		gameHelper.setup(gameHelperListener);


		//initialize(new MainGame(this), config);
	}

	public void setUpAds(){
		bannerAdView= new AdView(this);
		bannerAdView.setVisibility(View.INVISIBLE);
		bannerAdView.setBackgroundColor(0xff000000);
		bannerAdView.setAdUnitId(BANNER_AD_UNIT_ID);
		bannerAdView.setAdSize(AdSize.SMART_BANNER);

		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(INTERSTITIAL_AD_UNIT_ID);
		interstitialAd.loadAd(new AdRequest.Builder().build());
		interstitialAd.setAdListener(new AdListener(){
			@Override
			public void onAdClosed() {
				interstitialAd.loadAd(new AdRequest.Builder().build());
			}
		});
	}



	@Override
	protected void onStart() {
		super.onStart();
		gameHelper.onStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		gameHelper.onStop();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		gameHelper.onActivityResult(requestCode, resultCode, data);
	}


	@Override
	public void signIn() {
		try
		{
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					gameHelper.beginUserInitiatedSignIn();
				}
			});
			//Gdx.app.log("MainActivity","Log in successful" );
			//Log.d("Sign in success", "SpaceWreck");
		}
		catch(Exception e)
		{
			//Gdx.app.log("MainActivity","Log in failed:" + e.getMessage()+".");
		}
	}

	@Override
	public void signOut() {
		try
		{
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					gameHelper.signOut();
					//Log.d("Sign out success", "SpaceWreck");
				}
			});
		}
		catch(Exception e)
		{
			//Gdx.app.log("MainActivity","Log out failed:" + e.getMessage()+".");
		}
	}

	@Override
	public void rateGame() {  //need to add this
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
		//Log.i(TAG, this.getPackageName() +"yoyo");
	}

	@Override
	public void unlockAchievement(String str) {
		Games.Achievements.unlock(gameHelper.getApiClient(), str);
	}

	@Override
	public void submitScore(int highScore) {
		if (isSignedIn() == true)
		{
			Games.Leaderboards.submitScore(gameHelper.getApiClient(),
					getString(R.string.leaderboard_top_scores), highScore);
		}
	}

	@Override
	public void showAchievement() {
		startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()),
				requestCode);
	}

	@Override
	public void showScore() {
		if (isSignedIn() == true)
		{
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
					getString(R.string.leaderboard_top_scores)), requestCode);
		}
		else
		{
			signIn();
		}
	}

	@Override
	public boolean isSignedIn() {
		return gameHelper.isSignedIn();
	}

	@Override
	public String getAchievement1() {
		return getString(R.string.achievement_true_warrior);
	}

	@Override
	public String getAchievement2() {
		return getString(R.string.achievement_born_to_kill);
	}

	@Override
	public String getAchievement3() {
		return getString(R.string.achievement_jumbo_victory);
	}

	@Override
	public String getAchievement4() {
		return getString(R.string.achievement_youre_my_ultraman);
	}

	@Override
	public String getAchievement5() {
		return getString(R.string.achievement_you_wrecked_it_all);
	}

	@Override
	public String getLeaderBoard() {
		return getString(R.string.leaderboard_top_scores);
	}

	@Override
	public void showBannerAd() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				bannerAdView.setVisibility(View.VISIBLE);
				AdRequest.Builder builder = new AdRequest.Builder();
				AdRequest ad = builder.build();
				bannerAdView.loadAd(ad);
			}
		});
	}
	@Override
	public void hideBannerAd() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				bannerAdView.setVisibility(View.INVISIBLE);
			}
		});
	}

	@Override
	public void showInterstitialAd(final Runnable then) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				interstitialAd.show();
			}
		});

	}
	//	@Override
//	public void onPause() {
//		if (adView != null) {
//			adView.pause();
//		}
//		super.onPause();
//	}

//	/** Called when returning to the activity */
//	@Override
//	public void onResume() {
//		super.onResume();
//		if (adView != null) {
//			adView.resume();
//		}
//	}
//
//	/** Called before the activity is destroyed */
//	@Override
//	public void onDestroy() {
//		if (adView != null) {
//			adView.destroy();
//		}
//		super.onDestroy();
//	}
}
