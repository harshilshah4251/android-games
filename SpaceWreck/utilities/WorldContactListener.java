package com.SpaceWreck.utilities;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.SpaceWreck.sprites.Enemy;
import com.SpaceWreck.sprites.WhiteEnemyBullet;

/**
 * Created by harsh on 06/20/17.
 */

public class WorldContactListener implements ContactListener{
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA= contact.getFixtureA();
        Fixture fixB= contact.getFixtureB();

        int cDef= fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch(cDef){
            case com.SpaceWreck.game.GameConstants.ENEMY_BIT | com.SpaceWreck.game.GameConstants.PLAYER_BIT :
                if(fixA.getFilterData().categoryBits== com.SpaceWreck.game.GameConstants.PLAYER_BIT){
                    ((com.SpaceWreck.sprites.Player)fixA.getUserData()).updateHealth();
                    ((com.SpaceWreck.sprites.Enemy)fixB.getUserData()).updateHealth();

                }
                else if(fixA.getFilterData().categoryBits== com.SpaceWreck.game.GameConstants.ENEMY_BIT){
                    ((com.SpaceWreck.sprites.Enemy)fixA.getUserData()).updateHealth();
                    ((com.SpaceWreck.sprites.Player)fixB.getUserData()).updateHealth();

                }
                break;
            case com.SpaceWreck.game.GameConstants.ENEMYBULLET_BIT | com.SpaceWreck.game.GameConstants.PLAYER_BIT :
                if(fixA.getFilterData().categoryBits== com.SpaceWreck.game.GameConstants.PLAYER_BIT){
                    ((com.SpaceWreck.sprites.Player)fixA.getUserData()).updateHealth();
                    if(fixB.getUserData() instanceof com.SpaceWreck.sprites.RedEnemyBullet){
                        ((com.SpaceWreck.sprites.RedEnemyBullet)fixB.getUserData()).setDead(true);
                    }
                    if(fixB.getUserData() instanceof WhiteEnemyBullet){
                        ((WhiteEnemyBullet)fixB.getUserData()).setDead(true);
                    }
                    if(fixB.getUserData() instanceof com.SpaceWreck.sprites.WhiteEnemyBullet){
                        ((com.SpaceWreck.sprites.WhiteEnemyBullet)fixB.getUserData()).setDead(true);
                    }

                }
                else if(fixA.getFilterData().categoryBits== com.SpaceWreck.game.GameConstants.ENEMYBULLET_BIT){
                    ((com.SpaceWreck.sprites.Player)fixB.getUserData()).updateHealth();
                    if(fixB.getUserData() instanceof com.SpaceWreck.sprites.RedEnemyBullet){
                        ((com.SpaceWreck.sprites.RedEnemyBullet)fixB.getUserData()).setDead(true);
                    }
                    if(fixB.getUserData() instanceof com.SpaceWreck.sprites.WhiteEnemyBullet){
                        ((com.SpaceWreck.sprites.WhiteEnemyBullet)fixB.getUserData()).setDead(true);
                    }
                    if(fixB.getUserData() instanceof com.SpaceWreck.sprites.WhiteEnemyBullet){
                        ((com.SpaceWreck.sprites.WhiteEnemyBullet)fixB.getUserData()).setDead(true);
                    }
                }
                break;
            case com.SpaceWreck.game.GameConstants.PLAYERBULLET_BIT | com.SpaceWreck.game.GameConstants.ENEMY_BIT :
                if(fixA.getFilterData().categoryBits== com.SpaceWreck.game.GameConstants.PLAYERBULLET_BIT){
                    ((com.SpaceWreck.sprites.PlayerBullet)fixA.getUserData()).setDead(true);
                    ((com.SpaceWreck.sprites.Enemy)fixB.getUserData()).updateHealth();
                }
                else if(fixA.getFilterData().categoryBits== com.SpaceWreck.game.GameConstants.ENEMY_BIT){
                    ((com.SpaceWreck.sprites.PlayerBullet)fixB.getUserData()).setDead(true);
                    ((Enemy)fixA.getUserData()).updateHealth();
                }
                break;
            case com.SpaceWreck.game.GameConstants.PLAYER_BIT | com.SpaceWreck.game.GameConstants.STAR_BIT :
                if(fixA.getFilterData().categoryBits== com.SpaceWreck.game.GameConstants.PLAYER_BIT){
                    ((com.SpaceWreck.sprites.Player)fixA.getUserData()).updateScore((com.SpaceWreck.sprites.Star)fixB.getUserData());
                    ((com.SpaceWreck.sprites.Star)fixB.getUserData()).setHit(true);
                }
                else if(fixA.getFilterData().categoryBits== com.SpaceWreck.game.GameConstants.STAR_BIT){
                    ((com.SpaceWreck.sprites.Player)fixB.getUserData()).updateScore((com.SpaceWreck.sprites.Star)fixA.getUserData());
                    ((com.SpaceWreck.sprites.Star)fixA.getUserData()).setHit(true);
                }
                break;
            case com.SpaceWreck.game.GameConstants.PLAYER_BIT | com.SpaceWreck.game.GameConstants.FINISHLINE_BIT :
                if(fixA.getFilterData().categoryBits== com.SpaceWreck.game.GameConstants.PLAYER_BIT){
                    ((com.SpaceWreck.sprites.Player)fixA.getUserData()).setHasFinishedLevel(true);
                    ((com.SpaceWreck.sprites.FinishLine)fixB.getUserData()).setHit(true);
                }
                else if(fixA.getFilterData().categoryBits== com.SpaceWreck.game.GameConstants.FINISHLINE_BIT){
                    ((com.SpaceWreck.sprites.Player)fixB.getUserData()).setHasFinishedLevel(true);
                    ((com.SpaceWreck.sprites.FinishLine)fixA.getUserData()).setHit(true);
                }
                //Gdx.app.log("Contact" , "true");
                break;
            case com.SpaceWreck.game.GameConstants.ENEMY_BIT | com.SpaceWreck.game.GameConstants.WALL_BIT :
                if(fixA.getFilterData().categoryBits== com.SpaceWreck.game.GameConstants.ENEMY_BIT && fixA.getUserData() instanceof com.SpaceWreck.sprites.FollowEnemy2){
                    ((com.SpaceWreck.sprites.FollowEnemy2)fixA.getUserData()).reverseVelocity();
                }
                if(fixB.getFilterData().categoryBits== com.SpaceWreck.game.GameConstants.ENEMY_BIT && fixB.getUserData() instanceof com.SpaceWreck.sprites.FollowEnemy2){
                    ((com.SpaceWreck.sprites.FollowEnemy2)fixB.getUserData()).reverseVelocity();
                }
                //Gdx.app.log("Contact" , "true");
                break;

            case com.SpaceWreck.game.GameConstants.ENEMY_BIT | com.SpaceWreck.game.GameConstants.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits== com.SpaceWreck.game.GameConstants.ENEMY_BIT && fixA.getUserData() instanceof com.SpaceWreck.sprites.FollowEnemy2){
                    ((com.SpaceWreck.sprites.FollowEnemy2)fixA.getUserData()).reverseVelocity();
                }
                if(fixB.getFilterData().categoryBits== com.SpaceWreck.game.GameConstants.ENEMY_BIT && fixB.getUserData() instanceof com.SpaceWreck.sprites.FollowEnemy2){
                    ((com.SpaceWreck.sprites.FollowEnemy2)fixB.getUserData()).reverseVelocity();
                }
                //Gdx.app.log("Contact" , "true");
                break;
            case com.SpaceWreck.game.GameConstants.PLAYER_BIT | com.SpaceWreck.game.GameConstants.POWERUP_BIT :
                if(fixA.getFilterData().categoryBits== com.SpaceWreck.game.GameConstants.POWERUP_BIT){
                    ((com.SpaceWreck.sprites.PowerUps)fixA.getUserData()).setHit(true);
                }
                else if(fixB.getFilterData().categoryBits== com.SpaceWreck.game.GameConstants.POWERUP_BIT){
                    ((com.SpaceWreck.sprites.PowerUps)fixB.getUserData()).setHit(true);
                }
                break;
            case com.SpaceWreck.game.GameConstants.ENEMY_BIT | com.SpaceWreck.game.GameConstants.BOX_BIT:
                if(fixA.getFilterData().categoryBits== com.SpaceWreck.game.GameConstants.ENEMY_BIT && fixA.getUserData() instanceof com.SpaceWreck.sprites.FollowEnemy2){
                    ((com.SpaceWreck.sprites.FollowEnemy2)fixA.getUserData()).reverseVelocity();
                }
                if(fixB.getFilterData().categoryBits== com.SpaceWreck.game.GameConstants.ENEMY_BIT && fixB.getUserData() instanceof com.SpaceWreck.sprites.FollowEnemy2){
                    ((com.SpaceWreck.sprites.FollowEnemy2)fixB.getUserData()).reverseVelocity();
                }
                //Gdx.app.log("Contact" , "true");
                break;

        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
