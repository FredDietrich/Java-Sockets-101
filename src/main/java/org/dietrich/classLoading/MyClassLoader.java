package org.dietrich.classLoading;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.dietrich.annotations.Resource;

public class MyClassLoader {

    public Set<Class<?>> getResourceClasses() {
        Set<Class<?>> classes = new HashSet<>();
        getAllClassesFromPackage("org.dietrich.resources").stream()
            .forEach((clazz) -> {
                Optional<Resource> resourceAnnotationOpt = Optional.ofNullable(clazz.getAnnotation(Resource.class));
                if (resourceAnnotationOpt.isPresent()) {
                    classes.add(clazz);
                }
            });
        return classes;
    }

    private Set<Class<?>> getAllClassesFromPackage(String packageName) {
        InputStream inStream = ClassLoader.getSystemClassLoader()
            .getResourceAsStream(packageName.replaceAll("\\.", "/"));
        BufferedReader inReader = new BufferedReader(new InputStreamReader(inStream));
        return inReader.lines()
            .filter((line) -> line.endsWith(".class"))
            .map((line) -> getClassInstance(line, packageName))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toSet());
    }

    private Optional<Class<?>> getClassInstance(String className, String packageName) {
        try {
            String fullName = new StringBuilder()
                .append(packageName)
                .append(".")
                .append(className.substring(0, className.lastIndexOf(".")))
                .toString();
            return Optional.of(Class.forName(fullName));
        } catch (ClassNotFoundException e) {
            return Optional.empty();
        }
    }

}
