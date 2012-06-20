package com.swkoan.gallows.service;

/**
 *
 */
public interface Operation {

    boolean handles(Request request);
    
    void execute(Request request, ResponseHandler handler);
}
