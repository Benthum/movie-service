ALTER TABLE `movie`
ADD INDEX `movie_watched` (`watched` ASC),
ADD INDEX `movie_owned` (`owned` ASC),
ADD INDEX `movie_name` (`name` ASC);