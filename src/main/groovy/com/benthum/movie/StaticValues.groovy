package com.benthum.movie

class StaticValues {
    //Response Strings
    final static String InternalServerError = "There was a problem processing your request"
    final static String UnableToUpdateMovie = "The requested movie was not found. Please create the movie before trying to update it."

    //Resolution Types
    final static int Unknown = 0
    final static int P480 = 1
    final static int P720 = 2
    final static int P1080 = 3
    final static int P1440 = 4
    final static int P2160 = 5
}
