package com.benthum.movie.service

import com.benthum.movie.StaticValues
import com.benthum.movie.model.Movie
import com.benthum.movie.model.MovieCount
import com.benthum.movie.model.MovieMetadata
import com.benthum.movie.model.Resolution
import com.benthum.movie.repository.MovieMetadataRepository
import com.benthum.movie.repository.MovieRepository
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Slf4j
class MovieService {

    @Autowired
    MovieRepository movieRepository

    @Autowired
    MovieMetadataRepository movieMetadataRepository

    void create(String createMovie) {
        def mapper = new ObjectMapper()
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
        def input = mapper.readValue(createMovie, Movie.class)
        def movie = new Movie()
        movie.name = input.name

        if(input.resolution != null) {
            movie.resolution = new Resolution(input.resolution.id)
        }
        movie.description = input.description

        if(input.watched != null) {
            movie.watched = input.watched
        }

        if(input.owned != null) {
            movie.owned = input.owned
        }

        movieRepository.save(movie)
    }

    boolean update(String updateMovie) {
        def mapper = new ObjectMapper()
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
        def input = mapper.readValue(updateMovie, Movie.class)
        def movie
        if(input.id != null){
            movie = movieRepository.findOne(input.id)
        }
        else if(input.name != null) {
            movie = movieRepository.findOneByName(input.name)
        }
        else {
            return false
        }
        if(movie == null) {
            return false
        }
        movie.name = input.name
        if(input.resolution != null) {
            movie.resolution = new Resolution(input.resolution.id)
        }
        if(input.description != null) {
            movie.description = input.description
        }
        if(input.watched != null) {
            movie.watched = input.watched
        }
        if(input.owned != null) {
            movie.owned = input.owned
        }

        movieRepository.save(movie)
    }

    MovieCount getCount() {
        MovieCount count = new MovieCount()

        count.totalCount = movieRepository.count()
        count.watchedCount = movieRepository.countByWatchedTrue()
        count.notWatchedCount = movieRepository.countByWatchedFalse()
        count.ownedCount = movieRepository.countByOwnedTrue()
        count.notOwnedCount = movieRepository.countByOwnedFalse()
        count.unknownResolutionCount = movieRepository.countByResolution(new Resolution(StaticValues.Unknown))
        count.p480Count = movieRepository.countByResolution(new Resolution(StaticValues.P480))
        count.p720Count = movieRepository.countByResolution(new Resolution(StaticValues.P720))
        count.p1080Count = movieRepository.countByResolution(new Resolution(StaticValues.P1080))
        count.p1440Count = movieRepository.countByResolution(new Resolution(StaticValues.P1440))
        count.p2160Count = movieRepository.countByResolution(new Resolution(StaticValues.P2160))

        return count
    }

    List<MovieMetadata> getMetadata(Integer type) {
        switch (type) {
            case StaticValues.WatchedTypeFalse:
                return movieMetadataRepository.findAllByWatchedFalse()
            case StaticValues.WatchedTypeTrue:
                return movieMetadataRepository.findAllByWatchedTrue()
            case StaticValues.OwnedTypeFalse:
                return movieMetadataRepository.findAllByOwnedFalse()
            case StaticValues.OwnedTypeTrue:
                return movieMetadataRepository.findAllByOwnedTrue()
            case StaticValues.ResolutionTypeUnknown:
                return movieMetadataRepository.findAllByResolution(new Resolution(StaticValues.Unknown))
            case StaticValues.ResolutionType480P:
                return movieMetadataRepository.findAllByResolution(new Resolution(StaticValues.P480))
            case StaticValues.ResolutionType720P:
                return movieMetadataRepository.findAllByResolution(new Resolution(StaticValues.P720))
            case StaticValues.ResolutionType1080P:
                return movieMetadataRepository.findAllByResolution(new Resolution(StaticValues.P1080))
            case StaticValues.ResolutionType1440P:
                return movieMetadataRepository.findAllByResolution(new Resolution(StaticValues.P1440))
            case StaticValues.ResolutionType2160P:
                return movieMetadataRepository.findAllByResolution(new Resolution(StaticValues.P2160))
            default:
                return null
        }
    }

    Movie getDetail(Integer id) {
        return movieRepository.findOne(id)
    }
}
