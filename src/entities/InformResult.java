/**
 * @author : Juan Carlos Chaves Puertas
 * @version : 1.0
 * @copyright : Copyright (C) 2017
 * @license : MIT (expat) License
 * @maintainer : Juan Carlos Chaves Puertas
 * @email : lobolanja@gmail.com
 */
package entities;

public class InformResult {
    private String centro;
    private String categoria;
    private String tipo_vinculacion;
    private String sistema_acceso;
    private int nomb_anyo;
    private int ult_contr_nota;
    private int factor;
    private String ult_contr_fecha;
    private String fecha_consult;
    private boolean recent;

    public InformResult() {
    }

    public InformResult(String string, String string2, String string3, String string4, int n, int n2, int n3, String string5, String string6, boolean bl) {
        this.centro = string;
        this.categoria = string2;
        this.tipo_vinculacion = string3;
        this.sistema_acceso = string4;
        this.nomb_anyo = n;
        this.ult_contr_nota = n2;
        this.factor = n3;
        this.ult_contr_fecha = string5;
        this.fecha_consult = string6;
        this.recent = bl;
    }

    public String getCentro() {
        return this.centro;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public String getTipo_vinculacion() {
        return this.tipo_vinculacion;
    }

    public String getSistema_acceso() {
        return this.sistema_acceso;
    }

    public int getNomb_anyo() {
        return this.nomb_anyo;
    }

    public int getUlt_contr_nota() {
        return this.ult_contr_nota;
    }

    public int getFactor() {
        return this.factor;
    }

    public String getUlt_contr_fecha() {
        return this.ult_contr_fecha;
    }

    public String getFecha_consult() {
        return this.fecha_consult;
    }

    public boolean isRecent() {
        return this.recent;
    }

    public void setRecent(boolean bl) {
        this.recent = bl;
    }
}

