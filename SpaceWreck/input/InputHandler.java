package com.SpaceWreck.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.SpaceWreck.screens.PlayScreen;
import com.SpaceWreck.sprites.Player;

/**
 * Created by harsh on 06/20/17.
 */

public class InputHandler implements InputProcessor{

    private PlayScreen playScreen;
    private Player player;

    public InputHandler(PlayScreen playScreen){
        this.playScreen= playScreen;
        this.player= playScreen.getPlayer();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode){
            case Input.Keys.UP:
                player.setUpMove(true);
                break;
            case Input.Keys.DOWN:
                player.setDownMove(true);
                break;
            case Input.Keys.LEFT:
                player.setLeftMove(true);
                break;
            case Input.Keys.RIGHT:
                player.setRightMove(true);
                break;
            case Input.Keys.SPACE:
                player.setStatic(true);
                break;
            case Input.Keys.A:
                player.setShooting(true);
        }
        return true;
    }

    @Override
    public boolean keyUp( int keycode) {
       switch(keycode) {
           case Input.Keys.A:
               player.setShooting(false);
//                break;
//            case Input.Keys.DOWN:
//                player.setDownMove(false);
//                break;
//            case Input.Keys.LEFT:
//                player.setLeftMove(false);
//                break;
//            case Input.Keys.RIGHT:
//                player.setRightMove(false);
//                break;
//            case Input.Keys.SPACE:
//                player.setStatic(false);
       }
        return true;
    }



    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
