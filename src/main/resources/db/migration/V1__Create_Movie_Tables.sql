DROP TABLE IF EXISTS `resolution`;

CREATE TABLE `resolution` (
  `id` smallint(5) unsigned NOT NULL,
  `description` varchar(7) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `resolution`
(`id`,`description`)
VALUES
(0,'Unknown'),
(1,'480P'),
(2,'720P'),
(3,'1080P'),
(4,'1440P'),
(5,'2160P');

DROP TABLE IF EXISTS `movie`;

CREATE TABLE `movie` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `resolution` smallint(5) unsigned NOT NULL DEFAULT '0',
  `description` text COLLATE utf8_unicode_ci,
  `inserted_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `watched` bit(1) NOT NULL DEFAULT b'0',
  `owned` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `resolution_fk_idx` (`resolution`),
  CONSTRAINT `movie_resolution_fk` FOREIGN KEY (`resolution`) REFERENCES `resolution` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;