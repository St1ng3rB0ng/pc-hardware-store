package com.pc_hardware_shop.demo.exceprion;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
