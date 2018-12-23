package com.benthum.movie.controller

import com.benthum.movie.StaticValues
import com.benthum.movie.model.MovieRequest
import com.benthum.movie.model.ValidationEnum
import com.benthum.movie.model.ValidationResult
import com.benthum.movie.service.MovieService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/movie")
@Slf4j
class MovieController {
    @Autowired
    MovieService movieService

    @RequestMapping(method=RequestMethod.POST, produces = "application/json")
    @ResponseBody
    ResponseEntity create(@RequestBody MovieRequest request) {
        try {
            def result = movieService.create(request)
            if (result.type != ValidationEnum.Valid) {
                log.warn(result.message)
                return new ResponseEntity(result, HttpStatus.BAD_REQUEST)
            }
            return new ResponseEntity(result.object, HttpStatus.OK)
        }
        catch (Exception ex) {
            def result = new ValidationResult(type: ValidationEnum.UnknownInvalid, message: StaticValues.CreateMovieError)
            log.error(result.message, ex)
            return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @RequestMapping(method=RequestMethod.GET, value="/{id}", produces = "application/json")
    @ResponseBody
    ResponseEntity get(@PathVariable Long id) {
        try {
            def result = movieService.get(id)
            if (result.type != ValidationEnum.Valid) {
                log.warn(result.message)
                return new ResponseEntity(result, HttpStatus.BAD_REQUEST)
            }
            return new ResponseEntity(result.object, HttpStatus.OK)
        }
        catch (Exception ex) {
            def result = new ValidationResult(type: ValidationEnum.UnknownInvalid, message: StaticValues.GetMovieError)
            log.error(result.message, ex)
            return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @RequestMapping(method=RequestMethod.PUT, value="/{id}", produces = "application/json")
    @ResponseBody
    ResponseEntity update(@PathVariable Long id, @RequestBody MovieRequest request) {
        try {
            def result = movieService.update(id, request)
            if (result.type != ValidationEnum.Valid) {
                log.warn(result.message)
                return new ResponseEntity(result, HttpStatus.BAD_REQUEST)
            }
            return new ResponseEntity(HttpStatus.OK)
        }
        catch (Exception ex) {
            def result = new ValidationResult(type: ValidationEnum.UnknownInvalid, message: StaticValues.UpdateMovieError)
            log.error(result.message, ex)
            return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}

