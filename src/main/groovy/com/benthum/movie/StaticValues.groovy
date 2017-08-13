package com.benthum.movie

class StaticValues {
    //Response Strings
    final static String InternalServerError = "There was a problem processing your request"
    final static String UnableToUpdateMovie = "The requested movie was not found. Please create the movie before trying to update it."
    final static String UnableToGetMetadata = "The requested metadata type was not found. Please select a supported metadata type."

    //Resolution Types
    final static int Unknown = 0
    final static int P480 = 1
    final static int P720 = 2
    final static int P1080 = 3
    final static int P1440 = 4
    final static int P2160 = 5

    //Types of metadata
    final static int WatchedTypeFalse = 0
    final static int WatchedTypeTrue = 1
    final static int OwnedTypeFalse = 2
    final static int OwnedTypeTrue = 3
    final static int ResolutionTypeUnknown = 4
    final static int ResolutionType480P = 5
    final static int ResolutionType720P = 6
    final static int ResolutionType1080P = 7
    final static int ResolutionType1440P = 8
    final static int ResolutionType2160P = 8
}
