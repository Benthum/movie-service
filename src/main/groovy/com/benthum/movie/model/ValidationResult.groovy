package com.benthum.movie.model

import com.fasterxml.jackson.annotation.JsonInclude

class ValidationResult {
    ValidationEnum type

    String message

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Object object
}