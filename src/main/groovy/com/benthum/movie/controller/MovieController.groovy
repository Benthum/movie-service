package com.benthum.movie.controller

import com.benthum.movie.StaticValues
import com.benthum.movie.model.MovieCount
import com.benthum.movie.service.MovieService
import groovy.json.JsonOutput
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
    ResponseEntity<Object> create(@RequestBody String createMovie) {
        try {
            movieService.create(createMovie)
            return ResponseEntity.ok(null)
        }
        catch (Exception ex) {
            def output = JsonOutput.toJson([
                    message: StaticValues.InternalServerError,
                    error: ex.message])
            log.error(output, ex)
            return new ResponseEntity<String>(output, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @RequestMapping(method=RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    ResponseEntity<Object> update(@RequestBody String updateMovie) {
        try {
            if(movieService.update(updateMovie)) {
                return ResponseEntity.ok(null)
            }
            def output = JsonOutput.toJson([
                    message: StaticValues.UnableToUpdateMovie])
            return new ResponseEntity<Object>(output, HttpStatus.BAD_REQUEST)
        }
        catch (Exception ex) {
            def output = JsonOutput.toJson([
                    message: StaticValues.InternalServerError,
                    error: ex.message])
            log.error(output, ex)
            return new ResponseEntity<String>(output, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @RequestMapping(method=RequestMethod.GET, value="/count", produces = "application/json")
    @ResponseBody
    ResponseEntity<Object> count() {
        try {
            return new ResponseEntity<MovieCount>(movieService.getCount(), HttpStatus.OK)
        }
        catch (Exception ex) {
            def output = JsonOutput.toJson([
                    message: StaticValues.InternalServerError,
                    error: ex.message])
            log.error(output, ex)
            return new ResponseEntity<String>(output, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}

