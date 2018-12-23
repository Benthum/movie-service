package com.benthum.movie.repository

import com.benthum.movie.model.Movie
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MovieRepository extends CrudRepository<Movie, Long> {
    Optional<Movie> findOneByName(String name)
}