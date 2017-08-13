package com.benthum.movie.model

import org.springframework.data.annotation.CreatedDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import java.time.Instant

@Entity
class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id

    String name

    @ManyToOne
    @JoinColumn(name = 'resolution')
    Resolution resolution = new Resolution()

    @Column(columnDefinition="Text")
    String description

    @Column(updatable = false, nullable = false)
    @CreatedDate
    Instant insertedOn

    @Column(columnDefinition = "BIT")
    Boolean watched = false

    @Column(columnDefinition = "BIT")
    Boolean owned = false
}
