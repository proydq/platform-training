package com.proshine.training.common.crypto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Component
@Converter
public class AESConverter implements AttributeConverter<String, String> {

    @Value("${app.encryption.key}")
    private String key;

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) {
            return null;
        }
        return AESUtil.encrypt(attribute, key);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return AESUtil.decrypt(dbData, key);
    }
}

