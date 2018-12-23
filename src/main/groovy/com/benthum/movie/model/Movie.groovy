package com.benthum.movie.model

import org.springframework.data.annotation.CreatedDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import java.time.Instant

@Entity
class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    String name

    @Column(columnDefinition="Text")
    String description

    Instant updatedOn

    @Column(updatable = false, nullable = false)
    @CreatedDate
    Instant enteredOn
}
