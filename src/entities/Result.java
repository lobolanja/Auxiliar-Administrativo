/**
 * @author : Juan Carlos Chaves Puertas
 * @version : 1.0
 * @copyright : Copyright (C) 2017
 * @license : MIT (expat) License
 * @maintainer : Juan Carlos Chaves Puertas
 * @email : lobolanja@gmail.com
 */
package entities;

public class Result {
    private boolean updated = false;
    private int idCode;
    private int idOffer;
    private int score;
    private int factor;
    private String scoredate;
    private String date;
    private int nomLastYear;
    private String idAccess;

    public boolean isUpdated() {
        return this.updated;
    }

    public void setUpdated(boolean bl) {
        this.updated = bl;
    }

    public int getIdCode() {
        return this.idCode;
    }

    public void setIdCode(int n) {
        this.idCode = n;
    }

    public int getIdOffer() {
        return this.idOffer;
    }

    public void setIdOffer(int n) {
        this.idOffer = n;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int n) {
        this.score = n;
    }

    public int getFactor() {
        return this.factor;
    }

    public void setFactor(int n) {
        this.factor = n;
    }

    public String getScoredate() {
        return this.scoredate;
    }

    public void setScoredate(String string) {
        this.scoredate = string;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String string) {
        this.date = string;
    }

    public int getNomLastYear() {
        return this.nomLastYear;
    }

    public void setNomLastYear(int n) {
        this.nomLastYear = n;
    }

    public String getIdAccess() {
        return this.idAccess;
    }

    public void setIdAccess(String string) {
        this.idAccess = string;
    }
}

