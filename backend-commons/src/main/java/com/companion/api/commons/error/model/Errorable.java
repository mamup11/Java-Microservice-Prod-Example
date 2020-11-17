package com.companion.api.commons.error.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class Errorable {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected String error;

    public void setError(String error) {
        this.error = error;
    }
    public boolean hasError() {
        return StringUtils.isNotBlank(error);
    }
}
