/**
 * @author : Juan Carlos Chaves Puertas
 * @version : 1.0
 * @copyright : Copyright (C) 2017
 * @license : MIT (expat) License
 * @maintainer : Juan Carlos Chaves Puertas
 * @email : lobolanja@gmail.com
 */
package procesing;

import database.DBHandler;
import entities.Codigo;
import entities.Oferta;
import entities.Proceso;
import entities.SubtipoDeOferta;
import entities.TipoDeOferta;
import entities.TipoDePersonal;
import entities.Unidad;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class QueryCache {
    private ArrayList<Codigo> codigos;
    private ArrayList<Oferta> ofertas;
    private String lastResponse;
    private String url = "http://www.juntadeandalucia.es/servicioandaluzdesalud/empleo/informe_notacorte_contratacion.php";
    private File archivo = null;
    private Connection.Response res;
    private Document doc;
    private DBHandler dbHandler = new DBHandler();

    public boolean startTablesCaching() {
        boolean bl = true;
        try {
            if (!this.connect()) {
                bl = false;
            }
            if (bl && this.res != null) {
                this.doc = this.res.parse();
                this.dbHandler.connect();
                this.codigos = this.extractCodigos();
                this.ofertas = this.extractOfertas();
            }
        }
        catch (Exception var2_2) {
            this.lastResponse.concat("\n Error: " + var2_2.toString());
            bl = false;
        }
        return bl;
    }

    private boolean connect() {
        boolean bl = true;
        try {
            this.res = Jsoup.connect((String)this.getUrl()).method(Connection.Method.POST).maxBodySize(0).execute();
        }
        catch (Exception var2_2) {
            bl = false;
        }
        return bl;
    }

    private ArrayList<Codigo> extractCodigos() throws IOException, SQLException {
        ArrayList<Codigo> arrayList = new ArrayList<Codigo>();
        Element element = this.doc.head();
        BufferedReader bufferedReader = new BufferedReader(new StringReader(element.data()));
        int n = 5;
        boolean bl = false;
        while (!bl) {
            Pattern pattern;
            Matcher matcher;
            String string = "";
            String string2 = "";
            Pattern pattern2 = Pattern.compile("\\s*?tabla_js_codigo\\[(\\d++)\\]\\s*?=\\s*?new Array.*?");
            boolean bl2 = false;
            while (!bl2 && (string2 = bufferedReader.readLine()) != null) {
                Matcher matcher2 = pattern2.matcher(string2);
                if (!matcher2.find()) continue;
                bl2 = true;
            }
            if (string2 == null) {
                bl = true;
            }
            boolean bl3 = false;
            for (int i = 0; !bl && i < n && !bl3; ++i) {
                string2 = bufferedReader.readLine();
                if (string2 == null) {
                    bl3 = true;
                    continue;
                }
                string = string.concat("\n" + string2);
            }
            if (bl3 || bl || !(matcher = (pattern = Pattern.compile("tabla_js_codigo\\[(\\d++)\\]\\['ID_UNIDAD'\\]='(.*?)';\\ntabla_js_codigo\\[\\d++\\]\\['UNIDAD'\\]='(.*?)';\\ntabla_js_codigo\\[\\d++\\]\\['ID_PROCESO'\\]='(.*?)';\\ntabla_js_codigo\\[\\d++\\]\\['PROCESO'\\]='(.*?)';\\ntabla_js_codigo\\[\\d++\\]\\['ID_TIPO_PERSONAL'\\]='(.*?)';")).matcher(string)).find()) continue;
            int n2 = Integer.parseInt(matcher.group(1));
            Unidad unidad = new Unidad(matcher.group(2), matcher.group(3));
            Proceso proceso = new Proceso(matcher.group(4), matcher.group(5));
            TipoDePersonal tipoDePersonal = new TipoDePersonal(matcher.group(6));
            arrayList.add(new Codigo(n2, unidad, proceso, tipoDePersonal));
            this.dbHandler.insert("insert or ignore into typesOfStuff values ('" + tipoDePersonal.getId() + "', '" + "NON DESCRIPTION YET" + "')");
            this.dbHandler.insert("insert or ignore into processes values ('" + proceso.getId() + "', '" + proceso.getDescription() + "')");
            this.dbHandler.insert("insert or ignore into units values ('" + unidad.getId() + "', '" + unidad.getDescription() + "')");
            this.dbHandler.insert("insert or ignore into codes values ('" + n2 + "', '" + unidad.getId() + "', '" + proceso.getId() + "', '" + tipoDePersonal.getId() + "')");
        }
        bufferedReader.close();
        return arrayList;
    }

    private ArrayList<Oferta> extractOfertas() throws IOException {
        ArrayList<Oferta> arrayList = new ArrayList<Oferta>();
        Element element = this.doc.head();
        BufferedReader bufferedReader = new BufferedReader(new StringReader(element.data()));
        int n = 3;
        boolean bl = false;
        while (!bl) {
            Pattern pattern;
            Matcher matcher;
            String string = "";
            String string2 = "";
            Pattern pattern2 = Pattern.compile("\\s*?tabla_js_oferta\\[(\\d++)\\]\\s*?=\\s*?new Array.*?");
            boolean bl2 = false;
            while (!bl2 && (string2 = bufferedReader.readLine()) != null) {
                Matcher matcher2 = pattern2.matcher(string2);
                if (!matcher2.find()) continue;
                bl2 = true;
            }
            if (string2 == null) {
                bl = true;
            }
            boolean bl3 = false;
            for (int i = 0; !bl && i < n && !bl3; ++i) {
                string2 = bufferedReader.readLine();
                if (string2 == null) {
                    bl3 = true;
                    continue;
                }
                string = string.concat("\n" + string2);
            }
            if (bl3 || bl || !(matcher = (pattern = Pattern.compile("tabla_js_oferta\\s?\\[(\\d++)\\]\\['ID_TIPO_OFERTA'\\]='(.*?)';\\ntabla_js_oferta\\s?\\[\\d++\\]\\['ID_SUBTIPO'\\]='(.*?)';\\ntabla_js_oferta\\s?\\[\\d++\\]\\['DESC_SUBTIPO'\\]='(.*?)';")).matcher(string)).find()) continue;
            int n2 = Integer.parseInt(matcher.group(1));
            TipoDeOferta tipoDeOferta = new TipoDeOferta(matcher.group(2));
            SubtipoDeOferta subtipoDeOferta = new SubtipoDeOferta(matcher.group(3), matcher.group(4));
            arrayList.add(new Oferta(n2, tipoDeOferta, subtipoDeOferta));
            this.dbHandler.insert("insert or ignore into subtypesOfOffer values ('" + subtipoDeOferta.getId() + "', '" + subtipoDeOferta.getDescription() + "')");
            this.dbHandler.insert("insert or ignore into typesOfOffer values ('" + tipoDeOferta.getId() + "', '" + "NOT IMPLEMENTED YET" + "')");
            this.dbHandler.insert("insert or ignore into offers values ('" + n2 + "', '" + tipoDeOferta.getId() + "', '" + subtipoDeOferta.getId() + "')");
        }
        bufferedReader.close();
        return arrayList;
    }

    public String getUrl() {
        return this.url;
    }
}

