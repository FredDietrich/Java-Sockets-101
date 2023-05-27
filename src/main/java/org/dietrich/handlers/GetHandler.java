package org.dietrich.handlers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.dietrich.classLoading.MyClassLoader;
import org.dietrich.annotations.GET;

public class GetHandler implements Parser {

    public static final String COMMAND = "GET";

    @Override
    public String parseMessage(String message) {
        String resource = message.replace("GET ", "");
        System.out.println("Received GET for resource: " + resource);
        Set<Class<?>> classes = new MyClassLoader().getResourceClasses();
        Set<Method> methods = new HashSet<>();
        Map<Method, String> getMethods = new HashMap<>();
        String returnStr = null;
        classes.forEach((clazz) -> {
            methods.addAll(
                Arrays.asList(clazz.getMethods())
            );
        });
        methods.forEach((method) -> {
            Optional<GET> optAnnotation = Optional.ofNullable(method.getAnnotation(GET.class));
            if (optAnnotation.isPresent()) {
                getMethods.put(method, optAnnotation.get().value());
            }
        });
        for (Method method : getMethods.keySet()) {
            if (!getMethods.get(method).equalsIgnoreCase(resource)) {
                continue;
            }
            try {
                returnStr = (String) method.invoke(null);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return returnStr;
    }
}
