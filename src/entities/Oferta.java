/**
 * @author : Juan Carlos Chaves Puertas
 * @version : 1.0
 * @copyright : Copyright (C) 2017
 * @license : MIT (expat) License
 * @maintainer : Juan Carlos Chaves Puertas
 * @email : lobolanja@gmail.com
 */
package entities;

import entities.Acceso;
import entities.SubtipoDeOferta;
import entities.TipoDeOferta;

public class Oferta {
    private int id;
    private TipoDeOferta tipo;
    private SubtipoDeOferta subtipo;
    private Acceso acceso;

    public Oferta(int n) {
        this.id = n;
    }

    public Oferta(int n, TipoDeOferta tipoDeOferta, SubtipoDeOferta subtipoDeOferta) {
        this.id = n;
        this.tipo = tipoDeOferta;
        this.subtipo = subtipoDeOferta;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int n) {
        this.id = n;
    }

    public TipoDeOferta getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoDeOferta tipoDeOferta) {
        this.tipo = tipoDeOferta;
    }

    public SubtipoDeOferta getSubtipo() {
        return this.subtipo;
    }

    public void setSubtipo(SubtipoDeOferta subtipoDeOferta) {
        this.subtipo = subtipoDeOferta;
    }

    public Acceso getAcceso() {
        return this.acceso;
    }

    public void setAcceso(Acceso acceso) {
        this.acceso = acceso;
    }
}

