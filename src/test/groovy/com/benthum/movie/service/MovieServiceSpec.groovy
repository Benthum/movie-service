package com.benthum.movie.service

import com.benthum.movie.StaticValues
import com.benthum.movie.model.Movie
import com.benthum.movie.model.Resolution
import com.benthum.movie.repository.MovieRepository
import groovy.json.JsonOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration
@SpringBootTest
class MovieServiceSpec extends Specification {

    @Autowired
    MovieService movieService

    @Autowired
    MovieRepository movieRepository

    Set<Integer> generatedIds
    String testName
    String testCreateName
    String testDescription
    String testUpdateDescription

    def setup() {
        testName = "Test Name"
        testCreateName = "Test Create Name"
        testDescription = "Test Description"
        testUpdateDescription = "Test Update Description"
        generatedIds = new ArrayList<Integer>()
    }

    def cleanup() {
        generatedIds.each { movieRepository.delete(it) }
    }

    def "Test Service Create Movie"() {
        when:
        def json = JsonOutput.toJson([
                name: testCreateName,
                resolution: StaticValues.P1080,
                description: testDescription,
                watched: false,
                owned: false])

        movieService.create(json)
        def result = movieRepository.findOneByName(testCreateName)
        generatedIds << result.id

        then:
        result.name == testCreateName
        result.resolution.id == StaticValues.P1080
        !result.owned
        !result.watched
        result.description == testDescription
        result.insertedOn != null
    }

    def "Test Service Update Movie"() {
        when:
        def movie = new Movie()
        movie.name = testName
        movie.resolution = new Resolution(StaticValues.P1080)
        movie.owned = true
        movie.watched = true
        movie = movieRepository.save(movie)
        generatedIds << movie.id
        def json = JsonOutput.toJson([
                id: movie.id,
                name: testName,
                resolution: StaticValues.P1080,
                description: testUpdateDescription,
                watched: false,
                owned: false])

        movieService.update(json)
        def result = movieRepository.findOne(movie.id)

        then:
        result.name == testName
        result.resolution.id == StaticValues.P1080
        !result.owned
        !result.watched
        result.description == testUpdateDescription
        result.insertedOn != null
    }
}