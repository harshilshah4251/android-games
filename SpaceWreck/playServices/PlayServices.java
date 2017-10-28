package com.SpaceWreck.playServices;

/**
 * Created by harsh on 07/02/17.
 */

public interface PlayServices {
    public void signIn();
    public void signOut();
    public void rateGame();
    public void unlockAchievement(String str);
    public void submitScore(int highScore);
    public void showAchievement();
    public void showScore();
    public boolean isSignedIn();
    public String getAchievement1();
    public String getAchievement2();
    public String getAchievement3();
    public String getAchievement4();
    public String getAchievement5();
    public String getLeaderBoard();

}
