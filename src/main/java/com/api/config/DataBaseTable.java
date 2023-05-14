package com.api.config;

public enum DataBaseTable {
    PRODUCT_TABLE("products");

    private final String value;

    DataBaseTable(final String table) {
        this.value = table;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
