package com.gucardev.springbootstartertemplate.infrastructure.util;


import java.util.Optional;
import java.util.function.Supplier;

public class NullSafeHelper {

    public static <T> T safeGet(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (NullPointerException e) {
            throw new NullPointerException("Null value encountered in object chain");
        }
    }

    public static <T> Optional<T> safeOptional(Supplier<T> supplier) {
        try {
            return Optional.ofNullable(supplier.get());
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }
}
