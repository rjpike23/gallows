package com.swkoan.gallows.data;

import com.swkoan.gallows.config.StyleConfig;

/**
 *
 */
public interface StyleFactory<T, U extends StyleConfig> {

    T createStyle(U styleConfig);
}
