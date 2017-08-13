package com.benthum.movie.repository

import com.benthum.movie.model.MovieMetadata
import com.benthum.movie.model.Resolution
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MovieMetadataRepository extends CrudRepository<MovieMetadata, Integer> {
    List<MovieMetadata> findAllByOwnedTrue()
    List<MovieMetadata> findAllByOwnedFalse()
    List<MovieMetadata> findAllByWatchedTrue()
    List<MovieMetadata> findAllByWatchedFalse()
    List<MovieMetadata> findAllByResolution(Resolution resolution)
}