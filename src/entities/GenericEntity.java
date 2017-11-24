/**
 * @author : Juan Carlos Chaves Puertas
 * @version : 1.0
 * @copyright : Copyright (C) 2017
 * @license : MIT (expat) License
 * @maintainer : Juan Carlos Chaves Puertas
 * @email : lobolanja@gmail.com
 */
package entities;

public class GenericEntity {
    private String id;
    private String description;

    public GenericEntity() {
        this.id = "";
        this.description = "";
    }

    public GenericEntity(String string) {
        this.id = string;
        this.description = "";
    }

    public GenericEntity(String string, String string2) {
        this.id = string;
        this.description = string2;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String string) {
        this.id = string;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String string) {
        this.description = string;
    }
}

