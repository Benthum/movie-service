package com.benthum.movie.repository

import com.benthum.movie.model.Movie
import com.benthum.movie.model.Resolution
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MovieRepository extends CrudRepository<Movie, Integer> {
    Movie findOneByName(String name)
    Long countByWatchedTrue()
    Long countByWatchedFalse()
    Long countByOwnedTrue()
    Long countByOwnedFalse()
    Long countByResolution(Resolution resolution)
}