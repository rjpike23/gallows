package com.swkoan.gallows.config;

/**
 *
 */
public class ConfigStatus {
    public enum States {INIT, LOADED, INVALID, UNSAVED;}
    
    private States currentState = States.INIT;
    
    public States getCurrentState() {
        return currentState;
    }
    
    public void loadSuccess() {
        currentState = States.LOADED;
    }
    
    public void loadFail() {
        currentState = States.INVALID;
    }
    
    public void modify() {
        currentState = States.UNSAVED;
    }
    
    public void saveSuccess() {
        currentState = States.LOADED;
    }
    
    public void saveFail() {
        currentState = States.INVALID;
    }
}
