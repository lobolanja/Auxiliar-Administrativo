/**
 * @author : Juan Carlos Chaves Puertas
 * @version : 1.0
 * @copyright : Copyright (C) 2017
 * @license : MIT (expat) License
 * @maintainer : Juan Carlos Chaves Puertas
 * @email : lobolanja@gmail.com
 */
package entities;

import entities.Proceso;
import entities.TipoDePersonal;
import entities.Unidad;

public class Codigo {
    private int id;
    private Unidad unidad;
    private Proceso proceso;
    private TipoDePersonal tipo;

    public Codigo(int n) {
        this.id = n;
    }

    public Codigo(int n, Unidad unidad, Proceso proceso, TipoDePersonal tipoDePersonal) {
        this.id = n;
        this.unidad = unidad;
        this.proceso = proceso;
        this.tipo = tipoDePersonal;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int n) {
        this.id = n;
    }

    public Unidad getUnidad() {
        return this.unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public Proceso getProceso() {
        return this.proceso;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    public TipoDePersonal getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoDePersonal tipoDePersonal) {
        this.tipo = tipoDePersonal;
    }
}

