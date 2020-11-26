package com.demo.station.utils;

import java.io.Serializable;


/**
 * @author : lipb
 * @date : 2020-11-23 14:15
 */
public interface IResultCode extends Serializable {
    String getMessage();

    int getCode();
}
