package com.benthum.movie.model

import com.benthum.movie.StaticValues
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Resolution {
    @Id
    @Column(columnDefinition="SMALLINT")
    Integer id

    String description

    Resolution(Integer type) {
        id = type
    }

    Resolution() {
        id = StaticValues.Unknown
    }
}
