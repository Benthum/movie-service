package com.benthum.movie.service

import com.benthum.movie.StaticValues
import com.benthum.movie.model.MovieRequest
import com.benthum.movie.model.ValidationEnum
import com.benthum.movie.model.ValidationResult
import com.benthum.movie.repository.MovieRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@Slf4j
class ValidationService {
    @Autowired
    MovieRepository movieRepository

    static ValidationResult validateMovieRequest(MovieRequest request) {
        def result = new ValidationResult(type: ValidationEnum.Valid)
        if(!request.name?.trim() || request.name.length() > StaticValues.MovieNameMaxLength) {
            result.type = ValidationEnum.InvalidMovieName
            result.message = StaticValues.InvalidMovieName(request.name)
            return result
        }
        return result
    }
}