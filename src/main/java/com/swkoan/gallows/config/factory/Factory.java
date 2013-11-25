package com.swkoan.gallows.config.factory;

import com.swkoan.gallows.config.Descriptor;

/**
 *
 */
public interface Factory<Product extends Object, Desc extends Descriptor> {

    Class<Desc> getDescriptorClass();

    Product produce(Desc descriptor);
}
