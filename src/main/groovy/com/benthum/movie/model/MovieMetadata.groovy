package com.benthum.movie.model

import org.springframework.data.annotation.CreatedDate
import javax.persistence.*
import java.time.Instant

@Entity
@Table(name = "Movie")
class MovieMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id

    String name

    @ManyToOne
    @JoinColumn(name = 'resolution')
    Resolution resolution

    @Column(updatable = false, nullable = false)
    @CreatedDate
    Instant insertedOn

    @Column(columnDefinition = "BIT")
    Boolean watched

    @Column(columnDefinition = "BIT")
    Boolean owned
}
