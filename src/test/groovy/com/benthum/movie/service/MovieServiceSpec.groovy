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
    String testName1
    String testName2
    String testCreateName
    String testDescription
    String testUpdateDescription

    def setup() {
        testName1 = "Test Name1"
        testName2 = "Test Name2"
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
                resolution: new Resolution(StaticValues.P1080),
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
        movie.name = testName1
        movie.resolution = new Resolution(StaticValues.P1080)
        movie.owned = true
        movie.watched = true
        movie = movieRepository.save(movie)
        generatedIds << movie.id
        def json = JsonOutput.toJson([
                id: movie.id,
                name: testName1,
                resolution: null,
                description: testUpdateDescription,
                watched: null,
                owned: false])

        movieService.update(json)
        def result = movieRepository.findOne(movie.id)

        then:
        result.name == testName1
        result.resolution.id == StaticValues.P1080
        result.watched
        !result.owned
        result.description == testUpdateDescription
        result.insertedOn != null
    }

    def "Test Service Counts"() {
        when:
        def movieItem1 = new Movie()
        movieItem1.name = testName1
        def movie1 = movieRepository.save(movieItem1)
        generatedIds << movie1.id
        def movieItem2 = new Movie()
        movieItem2.name = testName2
        movieItem2.owned = true
        movieItem2.watched = true
        def movie2 = movieRepository.save(movieItem2)
        generatedIds << movie2.id
        def result = movieService.count

        then:
        result.totalCount == 2
        result.watchedCount == 1
        result.notWatchedCount == 1
        result.ownedCount == 1
        result.notOwnedCount == 1
        result.unknownResolutionCount == 2
        result.p480Count == 0
        result.p720Count == 0
        result.p1080Count == 0
        result.p1440Count == 0
        result.p2160Count == 0
    }

    def "Test Service Get Metadata"() {
        when:
        def movie = new Movie()
        movie.name = testName1
        movie.resolution = new Resolution(StaticValues.P1080)
        movie.owned = true
        movie.watched = true
        movie = movieRepository.save(movie)
        generatedIds << movie.id
        def list = movieService.getMetadata(StaticValues.WatchedTypeTrue)
        def first = list.first()

        then:
        list.size() == 1
        first.name == testName1
        first.resolution.id == StaticValues.P1080
        first.owned
        first.watched
        first.insertedOn != null
    }

    def "Test Service Get Detail"() {
        when:
        def movie = new Movie()
        movie.name = testName1
        movie.resolution = new Resolution(StaticValues.P1080)
        movie.owned = true
        movie.watched = true
        movie.description = testDescription
        movie = movieRepository.save(movie)
        generatedIds << movie.id
        def result = movieService.getDetail(movie.id)

        then:
        result.name == testName1
        result.resolution.id == StaticValues.P1080
        result.owned
        result.watched
        result.description == testDescription
        result.insertedOn != null
    }
}