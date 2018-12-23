package com.benthum.movie.service

import com.benthum.movie.StaticValues
import com.benthum.movie.model.Movie
import com.benthum.movie.model.MovieRequest
import com.benthum.movie.model.ValidationEnum
import com.benthum.movie.model.ValidationResult
import com.benthum.movie.repository.MovieRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant

@Service
@Slf4j
class MovieService {
    @Autowired
    MovieRepository movieRepository
    @Autowired
    ValidationService validationService

    ValidationResult create(MovieRequest request) {
        def result = validationService.validateMovieRequest(request)
        if(result.type != ValidationEnum.Valid) {
            return result
        }
        try {
            def foundMovie = movieRepository.findOneByName(request.name)
            if(foundMovie.present) {
                result.type = ValidationEnum.DuplicateMovieName
                result.message = StaticValues.DuplicateMovieName(request.name)
                return result
            }
            def movie = new Movie(
                    name: request.name,
                    description: request.description,
                    updatedOn: Instant.now()
            )
            movie = movieRepository.save(movie)
            result.object = [id: movie.id]
        } catch(Exception e) {
            result.type = ValidationEnum.UnableToCreateMovie
            result.message = StaticValues.UnableToCreateMovie(request)
            log.error(result.message, e)
            return result
        }
        return result
    }

    ValidationResult get(Long id) {
        def result = new ValidationResult(type: ValidationEnum.Valid)
        try {
            def foundMovie = movieRepository.findById(id)
            if(!foundMovie.present) {
                result.type = ValidationEnum.MovieNotFound
                result.message = StaticValues.MovieNotFound(id)
                return result
            }
            def movie = foundMovie.get()
            def response = new MovieRequest(
                    name: movie.name,
                    description: movie.description
            )
            result.object = response
        } catch(Exception e) {
            result.type = ValidationEnum.UnableToGetMovie
            result.message = StaticValues.UnableToGetMovie(id)
            log.error(result.message, e)
            return result
        }
        return result
    }

    ValidationResult update(Long id, MovieRequest request) {
        def result = validationService.validateMovieRequest(request)
        if(result.type != ValidationEnum.Valid) {
            return result
        }
        try {
            def foundMovie = movieRepository.findById(id)
            if(!foundMovie.present) {
                result.type = ValidationEnum.MovieNotFound
                result.message = StaticValues.MovieNotFound(id)
                return result
            }
            def movie = foundMovie.get()
            movie.name = request.name
            movie.description = request.description
            movie.updatedOn = Instant.now()
            movieRepository.save(movie)
        } catch(Exception e) {
            result.type = ValidationEnum.UnableToUpdateMovie
            result.message = StaticValues.UnableToUpdateMovie(id, request)
            log.error(result.message, e)
            return result
        }
        return result
    }
}