package com.gucardev.springbootstartertemplate.infrastructure.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

class NullSafeHelperTest {

    record TestObject(NestedObject nested) {}
    record NestedObject(String value) {}

    @Test
    void safeGet_shouldReturnValue_whenNoNulls() {
        TestObject obj = new TestObject(new NestedObject("test"));
        String result = NullSafeHelper.safeGet(() -> obj.nested().value());
        assertEquals("test", result);
    }

    @Test
    void safeGet_shouldThrowException_whenNullInChain() {
        TestObject obj = new TestObject(null);
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> NullSafeHelper.safeGet(() -> obj.nested().value()));
        assertEquals("Null value encountered in object chain", ex.getMessage());
    }

    @Test
    void safeGet_shouldThrowException_whenObjectIsNull() {
        TestObject obj = null;
        assertThrows(NullPointerException.class,
                () -> NullSafeHelper.safeGet(() -> obj.nested().value()));
    }

    @Test
    void safeOptional_shouldReturnValue_whenNoNulls() {
        TestObject obj = new TestObject(new NestedObject("test"));
        Optional<String> result = NullSafeHelper.safeOptional(() -> obj.nested().value());
        assertTrue(result.isPresent());
        assertEquals("test", result.get());
    }

    @Test
    void safeOptional_shouldReturnEmpty_whenNullInChain() {
        TestObject obj = new TestObject(null);
        Optional<String> result = NullSafeHelper.safeOptional(() -> obj.nested().value());
        assertTrue(result.isEmpty());
    }

    @Test
    void safeOptional_shouldReturnEmpty_whenObjectIsNull() {
        TestObject obj = null;
        Optional<String> result = NullSafeHelper.safeOptional(() -> obj.nested().value());
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldHandlePrimitiveTypes() {
        class PrimitiveHolder {
            int getValue() { return 42; }
        }

        PrimitiveHolder holder = new PrimitiveHolder();
        assertEquals(42, NullSafeHelper.safeGet(holder::getValue));
        assertEquals(42, NullSafeHelper.safeOptional(holder::getValue).orElse(0));
    }
}