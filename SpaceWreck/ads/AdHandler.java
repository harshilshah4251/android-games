package com.SpaceWreck.ads;

/**
 * Created by harsh on 07/03/17.
 */

public interface AdHandler {
    public void showBannerAd();
    public void hideBannerAd();

    public void showInterstitialAd(Runnable then);
}
