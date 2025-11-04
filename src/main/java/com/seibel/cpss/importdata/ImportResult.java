package com.seibel.cpss.importdata;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ImportResult {
    private String tableName;
    private int recordsImported;
    private boolean success;
    private List<String> errors;

    private ImportResult() {
        this.errors = new ArrayList<>();
    }

    public static ImportResult success(String tableName, int recordsImported) {
        ImportResult result = new ImportResult();
        result.tableName = tableName;
        result.recordsImported = recordsImported;
        result.success = true;
        return result;
    }

    public static ImportResult withErrors(String tableName, int recordsImported, List<String> errors) {
        ImportResult result = new ImportResult();
        result.tableName = tableName;
        result.recordsImported = recordsImported;
        result.success = false;
        result.errors = errors;
        return result;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
