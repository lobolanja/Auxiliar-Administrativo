/**
 * @author : Juan Carlos Chaves Puertas
 * @version : 1.0
 * @copyright : Copyright (C) 2017
 * @license : MIT (expat) License
 * @maintainer : Juan Carlos Chaves Puertas
 * @email : lobolanja@gmail.com
 */
package procesing;

import entities.Acceso;
import entities.Codigo;
import entities.Oferta;
import entities.Proceso;
import entities.Result;
import entities.TipoDeOferta;
import entities.TipoDePersonal;
import entities.Unidad;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import procesing.NotConnectionException;

public class Request {
    private String url = "http://www.juntadeandalucia.es/servicioandaluzdesalud/empleo/informe_notacorte_contratacion.php";
    private Codigo code;
    private Oferta offer;
    private Acceso access;
    private Connection.Response res;
    private Document doc;
    private Result result;
    private Map<String, String> cookies;

    public Request(Codigo codigo, Oferta oferta, Acceso acceso, Map<String, String> map) {
        this.code = codigo;
        this.offer = oferta;
        this.access = acceso;
        this.result = new Result();
        this.result.setIdCode(codigo.getId());
        this.result.setIdOffer(oferta.getId());
        this.result.setIdAccess(acceso.getId());
        this.cookies = map;
    }

    public boolean execute() throws NotConnectionException {
        boolean bl = true;
        boolean bl2 = false;
        boolean bl3 = false;
        int n = 0;
        while (!bl2) {
            if (n >= 0) {
                // empty if block
            }
            try {
                if (this.connect()) {
                    bl2 = true;
                }
            }
            catch (IOException var5_5) {
                System.out.println("Error connecting: " + var5_5.toString());
            }
            ++n;
        }
        if (!bl2 || !bl || this.res == null) {
            bl = false;
            throw new NotConnectionException();
        }
        n = 0;
        while (!bl3) {
            if (n >= 0) {
                // empty if block
            }
            try {
                this.doc = this.res.parse();
                if (this.doc != null) {
                    bl3 = true;
                }
            }
            catch (IOException var5_6) {
                System.out.println("Error parsing: " + var5_6.toString());
                throw new NotConnectionException();
            }
            ++n;
        }
        if (!this.analize()) {
            bl = false;
        }
        if (!bl) {
            this.setResult(null);
        }
        return bl;
    }

    private boolean connect() throws IOException {
        boolean bl = true;
        if (this.getCode() != null && this.getOffer() != null) {
            System.setProperty("javax.net.ssl.trustStore", "./ks.jks");
            this.res = Jsoup.connect((String)this.getUrl()).data(new String[]{"tipo_personal", this.getCode().getTipo().getId(), "centro", this.getCode().getUnidad().getId(), "combo_categoria", this.getCode().getProceso().getId(), "t_vinculacion", this.getOffer().getTipo().getId(), "sistema_acceso", this.getAccess().getId(), "h_personal", this.getCode().getTipo().getDescription(), "h_centro", this.getCode().getUnidad().getDescription(), "h_categoria", this.getCode().getProceso().getDescription(), "h_vinculacion", this.getOffer().getTipo().getDescription(), "h_tipo_acceso", this.getAccess().getDescription()}).header("Cache-Control", "max-age=0").header("Origin", "https://ws035.juntadeandalucia.es").header("Referer", "https://ws035.juntadeandalucia.es/bolsa/http/informe_notacorte_contratacion.php").cookies(this.cookies).method(Connection.Method.POST).maxBodySize(0).timeout(10000).execute();
        } else {
            bl = false;
        }
        return bl;
    }

    private boolean analize() throws NotConnectionException {
        boolean bl = true;
        Pattern pattern = Pattern.compile("(NOTA)");
        Matcher matcher = pattern.matcher(this.res.body());
        if (!matcher.find()) {
            System.out.println("ERROR en p\u00e1gina");
            throw new NotConnectionException();
        }
        Pattern pattern2 = Pattern.compile("NOTA --> ([0-9]+\\.?[0-9]{0,3})<br><br>FECHA DE CORTE --> ([0-9]{1,2}/[0-9]{1,2}/[0-9]{2,4}) <br><br> FECHA --> ([0-9]{1,2}/[0-9]{1,2}/[0-9]{2,4})");
        Matcher matcher2 = pattern2.matcher(this.res.body());
        if (matcher2.find()) {
            String string = matcher2.group(1);
            String string2 = matcher2.group(2);
            String string3 = matcher2.group(3);
            if (string != null && string2 != null && string3 != null) {
                this.getResult().setIdCode(this.getCode().getId());
                this.getResult().setIdOffer(this.getOffer().getId());
                this.getResult().setScore((int)(Float.parseFloat(string.replaceAll(",", ".")) * 1000.0f));
                this.getResult().setFactor(1000);
                this.getResult().setScoredate(string2);
                this.getResult().setDate(string3);
            } else {
                bl = false;
            }
            Pattern pattern3 = Pattern.compile("N\u00famero de nombramientos realizados en el \u00faltimo a\u00f1o a contar de la fecha de hoy:\\s*?(\\d++)");
            Matcher matcher3 = pattern3.matcher(this.res.body());
            if (matcher3.find()) {
                this.getResult().setNomLastYear(Integer.parseInt(matcher3.group(1)));
            } else {
                this.getResult().setNomLastYear(-1);
            }
        } else {
            bl = false;
        }
        return bl;
    }

    public String getUrl() {
        return this.url;
    }

    public Codigo getCode() {
        return this.code;
    }

    public void setCode(Codigo codigo) {
        this.code = codigo;
    }

    public Oferta getOffer() {
        return this.offer;
    }

    public void setOffer(Oferta oferta) {
        this.offer = oferta;
    }

    public Result getResult() {
        return this.result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Acceso getAccess() {
        return this.access;
    }

    public void setAccess(Acceso acceso) {
        this.access = acceso;
    }
}

