package org.dietrich.resources;

import org.dietrich.annotations.GET;
import org.dietrich.annotations.Resource;

@Resource
public class TestEndpoint extends Endpoint {
    @GET("test")
    public static String get() {
        return "oi";
    }

    @GET("anotherOne")
    public static String anotherOne() {
        return "Hello World";
    }
}
