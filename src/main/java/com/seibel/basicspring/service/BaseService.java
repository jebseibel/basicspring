package com.seibel.basicspring.service;

public abstract class BaseService {

    protected String thisName;

    protected void requireNonNull(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException(thisName + " cannot be null");
        }
    }

    protected void requireNonBlank(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(thisName + " (String) cannot be null or blank");
        }
    }

    protected void requireNonNull(Object obj, String fieldName) {
        if (obj == null) {
            throw new IllegalArgumentException(thisName + "." + fieldName + " cannot be null");
        }
    }

    protected void requireNonBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(thisName + "." + fieldName + " cannot be null or blank");
        }
    }

}
