package com.test.service;

import org.apache.catalina.User;

import java.io.IOException;
import java.text.ParseException;

public interface UserService {
    void save() throws IOException, ParseException;
}
