package com.benthum.movie.service

import com.benthum.movie.StaticValues
import com.benthum.movie.model.Movie
import com.benthum.movie.model.MovieRequest
import com.benthum.movie.model.ValidationEnum
import com.benthum.movie.repository.MovieRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration
@SpringBootTest
class MovieServiceSpec extends Specification {
    @Autowired
    MovieRepository movieRepository
    @Autowired
    MovieService movieService

    def setup() {
        movieService.movieRepository = movieRepository
    }

    def cleanup() {
        def all = movieRepository.findAll()
        all.each {movieRepository.deleteById(it.id)}
    }

    def 'Create Movie - Success'() {
        given:
        def request = new MovieRequest(
                name: "Test Name",
                description: "Test Description"
        )

        when:
        def response = movieService.create(request)

        then:
        response
        response.type == ValidationEnum.Valid
        response.message == null
        response.object != null
        response.object?.id != null

        Long id = response.object?.id
        def foundMovie = movieRepository.findById(id)
        foundMovie.present
        def movie = foundMovie.get()
        movie.id != null
        movie.name == request.name
        movie.description == request.description
        movie.updatedOn != null
        movie.enteredOn != null
    }

    def 'Create Movie - Duplicate Movie Name'() {
        given:
        def request = new MovieRequest(
                name: "Test Name",
                description: "Test Description"
        )
        def movie = new Movie(
                name: request.name,
                description: request.description
        )

        when:
        movieRepository.save(movie)
        def response = movieService.create(request)

        then:
        response
        response.type == ValidationEnum.DuplicateMovieName
        response.message == StaticValues.DuplicateMovieName(request.name)
        response.object == null
    }

    def 'Create Movie - Unable to Create Movie'() {
        given:
        movieService.movieRepository = Stub(MovieRepository)
        movieService.movieRepository.save(_ as Movie) >> {
            Movie movie ->
                throw new Exception()
        }
        def request = new MovieRequest(
                name: "Test Name",
                description: "Test Description"
        )

        when:
        def response = movieService.create(request)

        then:
        response
        response.type == ValidationEnum.UnableToCreateMovie
        response.message == StaticValues.UnableToCreateMovie(request)
        response.object == null
    }

    def 'Get Movie - Success'() {
        given:
        def movie = new Movie(
                name: "Test Name",
                description: "Test Description"
        )

        when:
        movie = movieRepository.save(movie)
        def response = movieService.get(movie.id)

        then:
        response
        response.type == ValidationEnum.Valid
        response.message == null
        response.object != null
        def result = response.object as MovieRequest

        movie.name == result.name
        movie.description == result.description
    }

    def 'Get Movie - Movie Not Found'() {
        given:
        def id = -1

        when:
        def response = movieService.get(id)

        then:
        response
        response.type == ValidationEnum.MovieNotFound
        response.message == StaticValues.MovieNotFound(id)
        response.object == null
    }

    def 'Get Movie - Unable to Get Movie'() {
        given:
        movieService.movieRepository = Stub(MovieRepository)
        movieService.movieRepository.findById(_ as Long) >> {
            Long id ->
                throw new Exception()
        }
        def movie = new Movie(
                name: "Test Name",
                description: "Test Description"
        )

        when:
        movie = movieRepository.save(movie)
        def response = movieService.get(movie.id)

        then:
        response
        response.type == ValidationEnum.UnableToGetMovie
        response.message == StaticValues.UnableToGetMovie(movie.id)
        response.object == null
    }

    def 'Update Movie - Success'() {
        given:
        def request = new MovieRequest(
                name: "Test Name2",
                description: "Test Description2"
        )
        def movie = new Movie(
                name: "Test Name",
                description: "Test Description"
        )

        when:
        movie = movieRepository.save(movie)
        def response = movieService.update(movie.id, request)
        def foundMovie = movieRepository.findById(movie.id)
        def newMovie = foundMovie.get()

        then:
        response
        response.type == ValidationEnum.Valid
        response.message == null
        response.object == null

        movie.id == newMovie.id
        movie.name != newMovie.name
        movie.description != newMovie.description
        request.name == newMovie.name
        request.description == newMovie.description
    }

    def 'Update Movie - Movie Not Found'() {
        given:
        def request = new MovieRequest(
                name: "Test Name",
                description: "Test Description"
        )
        def id = -1

        when:
        def response = movieService.update(id, request)

        then:
        response
        response.type == ValidationEnum.MovieNotFound
        response.message == StaticValues.MovieNotFound(id)
        response.object == null
    }

    def 'Update Movie - Unable to Update Movie'() {
        given:
        movieService.movieRepository = Stub(MovieRepository)
        movieService.movieRepository.findById(_ as Long) >> {
            Long id ->
                throw new Exception()
        }
        def movie = new Movie(
                name: "Test Name",
                description: "Test Description"
        )
        def request = new MovieRequest(
                name: "Test Name2",
                description: "Test Description2"
        )

        when:
        movie = movieRepository.save(movie)
        def response = movieService.update(movie.id, request)

        then:
        response
        response.type == ValidationEnum.UnableToUpdateMovie
        response.message == StaticValues.UnableToUpdateMovie(movie.id, request)
        response.object == null
    }
}