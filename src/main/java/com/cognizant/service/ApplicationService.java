package com.cognizant.service;

import com.cognizant.exception.NotUniqueException;

public interface ApplicationService<T> {

    void save(T t) throws NotUniqueException;

}
