package com.example.moviesplatform.domain;

public enum Genre {
    ACTION,
    ADVENTURE,
    ANIMATION,
    BIOGRAPHY,
    COMEDY,
    CRIME,
    DOCUMENTARY,
    DRAMA,
    FAMILY,
    FANTASY,
    HISTORY,
    HORROR,
    MUSIC,
    MUSICAL,
    MYSTERY,
    ROMANCE,
    SCI_FI,
    SPORT,
    THRILLER,
    WAR,
    WESTERN,
    SHORT,
    NEWS,
    REALITY_TV,
    TALK_SHOW,
    GAME_SHOW,
    FILM_NOIR,
    ADULT,
    UNKNOWN;

    public static Genre fromStringSafe(String raw) {
        if (raw == null || raw.isBlank() || raw.equals("\\N")) {
            return UNKNOWN;
        }
        String normalized = raw.trim().toUpperCase().replace('-', '_').replace(' ', '_');
        if (normalized.equals("SCIENCE_FICTION") || normalized.equals("SCI_FI")) {
            normalized = "SCI_FI";
        }
        try {
            return Genre.valueOf(normalized);
        } catch (IllegalArgumentException ex) {
            return UNKNOWN;
        }
    }
}

