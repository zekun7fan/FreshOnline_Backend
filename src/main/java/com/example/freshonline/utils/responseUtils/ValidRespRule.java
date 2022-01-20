package com.example.freshonline.utils.responseUtils;

public enum ValidRespRule {

    COLLECTION_NOT_EMPTY(0),
    OBJECT_NOT_NULL(1),
    BOOLEAN(2),
    ;


    private int rule_id;

    ValidRespRule(int rule_id) {
        this.rule_id = rule_id;
    }
}
