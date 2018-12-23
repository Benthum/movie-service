package com.benthum.movie.model

enum ValidationEnum {
    UnknownInvalid,
    Valid,
    UnableToCreateMovie,
    UnableToUpdateMovie,
    UnableToGetMovie,
    CreateMovieError,
    UpdateMovieError,
    GetMovieError,
    MovieNotFound,
    DuplicateMovieName,
    InvalidMovieName
}