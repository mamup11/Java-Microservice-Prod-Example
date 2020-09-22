package com.companion.api.commons.interceptors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
@SuppressWarnings("unchecked")
public class LoggingMasker {
    private static final String ERROR_MESSAGE = "Error parsing masker.attributesToMask property. " +
            "This property should be coma separated strings or empty to skip masking";
    private final ObjectMapper mapper;
    private final Set<String> maskedAttributes;

    public LoggingMasker(@Value(value = "${masker.attributesToMask:}") String comaSeparatedAttributes,
                         ObjectMapper objectMapper) {
        this.mapper = objectMapper;

        try {
            if (StringUtils.isNotBlank(comaSeparatedAttributes)) {
                maskedAttributes = Arrays.stream(comaSeparatedAttributes.split(","))
                        .map(String::toUpperCase)
                        .collect(Collectors.toSet());
            } else {
                maskedAttributes = null;
            }
        } catch (Exception e) {
            log.error(ERROR_MESSAGE, e);
            throw new IllegalStateException(ERROR_MESSAGE, e);
        }
    }

    public String maskJsonMessage(String message) throws JsonProcessingException {
        Map<String, Object> map = mapper.readValue(message, new TypeReference<Map<String, Object>>() {
        });

        if (!CollectionUtils.isEmpty(maskedAttributes)) {
            findMarkedAttributes(map);
        }

        return mapper.writeValueAsString(map);
    }

    private void findMarkedAttributes(Map<String, Object> map) {
        map.forEach((key, value) -> {
            if (maskedAttributes.contains(key.toUpperCase())) {
                map.put(key, "****");
            } else if (value instanceof LinkedHashMap) {
                findMarkedAttributes((Map<String, Object>) value);
            }
        });
    }
}
