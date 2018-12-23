package com.benthum.movie.service

import com.benthum.movie.StaticValues
import com.benthum.movie.model.MovieRequest
import com.benthum.movie.model.ValidationEnum
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration
@SpringBootTest
class ValidationServiceSpec extends Specification {
    def 'Validate Movie Request - Success'() {
        given:
        def request = new MovieRequest(
                name: "Test Name",
                description: "Test Description"
        )

        when:
        def response = ValidationService.validateMovieRequest(request)

        then:
        response
        response.type == ValidationEnum.Valid
        response.message == null
        response.object == null
    }

    def 'Validate Movie Request - Invalid Movie Name - null'() {
        given:
        def request = new MovieRequest(
                name: null,
                description: "Test Description"
        )

        when:
        def response = ValidationService.validateMovieRequest(request)

        then:
        response
        response.type == ValidationEnum.InvalidMovieName
        response.message == StaticValues.InvalidMovieName(request.name)
        response.object == null
    }

    def 'Validate Movie Request - Invalid Movie Name - whitespace'() {
        given:
        def request = new MovieRequest(
                name: " ",
                description: "Test Description"
        )

        when:
        def response = ValidationService.validateMovieRequest(request)

        then:
        response
        response.type == ValidationEnum.InvalidMovieName
        response.message == StaticValues.InvalidMovieName(request.name)
        response.object == null
    }

    def 'Validate Movie Request - Invalid Movie Name - too long'() {
        given:
        def request = new MovieRequest(
                name: "TOOOOOOOOOOOOOOOOO LONGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG",
                description: "Test Description"
        )

        when:
        def response = ValidationService.validateMovieRequest(request)

        then:
        response
        response.type == ValidationEnum.InvalidMovieName
        response.message == StaticValues.InvalidMovieName(request.name)
        response.object == null
    }
}