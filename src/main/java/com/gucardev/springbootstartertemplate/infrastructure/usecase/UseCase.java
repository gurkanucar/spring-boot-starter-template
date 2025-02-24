package com.gucardev.springbootstartertemplate.infrastructure.usecase;

// 1. Base UseCase interface without params and return (void)
public interface UseCase<T> {
    void execute();
}

