package com.benthum.movie

import com.benthum.movie.model.MovieRequest
import groovy.json.JsonOutput

class StaticValues {
    //Max Lengths
    public final static int MovieNameMaxLength = 255

    //Response Strings
    public final static String CreateMovieError = "There was an error processing the request to create movie."
    public final static String UpdateMovieError = "There was an error processing the request to update movie."
    public final static String GetMovieError = "There was an error processing the request to get movie."

    //Dynamic Response String
    static String UnableToCreateMovie(MovieRequest request) {
        "Unable to process request to create movie for request: ${JsonOutput.toJson(request)}."
    }
    static String UnableToUpdateMovie(Long id, MovieRequest request) {
        "Unable to process request to update movie for id: ${id}, request: ${JsonOutput.toJson(request)}."
    }
    static String UnableToGetMovie(Long id) {
        "Unable to process request to get movie for id: ${id}."
    }
    static String MovieNotFound(Long id) {
        "Unable to find movie id: ${id}."
    }
    static String InvalidMovieName(String name) {
        "The movie name was found to be either null, empty, or too long. name: ${name}."
    }
    static String DuplicateMovieName(String name) {
        "The movie name was found to already be registered. name: ${name}"
    }
}