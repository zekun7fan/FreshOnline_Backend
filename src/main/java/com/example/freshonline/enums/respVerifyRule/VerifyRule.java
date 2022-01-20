package com.example.freshonline.enums.respVerifyRule;

import lombok.Data;

import java.util.Collection;

/**
 * rules that verify response data
 * @author zekun
 */
public enum VerifyRule implements VerifyRespData {

    NOT_NULL{
        @Override
        public boolean verify(Object data) {
            return data != null;
        }
    },
    COLLECTION_NOT_EMPTY{
        @Override
        public boolean verify(Object data) {
            Collection<?> collection = (Collection<?>) data;
            return collection != null && !collection.isEmpty();
        }
    },
    GREATER_THAN_ZERO{
        @Override
        public boolean verify(Object data) {
            return (Integer)data > 0;
        }
    },
    TRUE{
        @Override
        public boolean verify(Object data) {
            return (Boolean)data;
        }
    }
    ;
}
