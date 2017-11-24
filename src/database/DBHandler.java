/**
 * @author : Juan Carlos Chaves Puertas
 * @version : 1.0
 * @copyright : Copyright (C) 2017
 * @license : MIT (expat) License
 * @maintainer : Juan Carlos Chaves Puertas
 * @email : lobolanja@gmail.com
 */
package database;

import entities.Acceso;
import entities.Codigo;
import entities.InformResult;
import entities.Oferta;
import entities.Proceso;
import entities.Result;
import entities.SubtipoDeOferta;
import entities.TipoDeOferta;
import entities.TipoDePersonal;
import entities.Unidad;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBHandler {
    private Connection connection;
    private Statement statement;
    private String dbpath = "db.db";

    public boolean connect() {
        boolean bl = true;
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.dbpath);
            this.statement = this.connection.createStatement();
        }
        catch (ClassNotFoundException var2_2) {
            System.err.println("ERROR: " + var2_2.toString());
            bl = false;
        }
        catch (SQLException var2_3) {
            System.err.println("ERROR at connect(): " + var2_3.toString());
            bl = false;
        }
        return bl;
    }

    public boolean disconnect() {
        boolean bl = true;
        try {
            this.connection.close();
            this.statement.close();
        }
        catch (SQLException var2_2) {
            System.err.println("ERROR at disconnect(): " + var2_2.toString());
            bl = false;
        }
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean insert(String string) {
        boolean bl = false;
        Class<DBHandler> class_ = DBHandler.class;
        synchronized (DBHandler.class) {
            bl = true;
            try {
                this.statement.executeUpdate(string);
            }
            catch (SQLException var4_4) {
                System.err.println("ERROR at insert(): " + var4_4.toString());
                bl = false;
            }
            return bl;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean execute(String string) {
        boolean bl = false;
        Class<DBHandler> class_ = DBHandler.class;
        synchronized (DBHandler.class) {
            bl = true;
            try {
                this.statement.execute(string);
            }
            catch (SQLException var4_4) {
                System.err.println("ERROR at execute(): " + var4_4.toString());
                bl = false;
            }
            return bl;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ResultSet select(String string) {
        ResultSet resultSet = null;
        Class<DBHandler> class_ = DBHandler.class;
        synchronized (DBHandler.class) {
            try {
                resultSet = this.statement.executeQuery(string);
            }
            catch (SQLException var4_4) {
                System.err.println("ERROR at select(): " + var4_4.toString() + "    " + string);
            }
            return resultSet;
        }
    }

    public ArrayList<Codigo> getCodes() {
        ArrayList<Codigo> arrayList = new ArrayList<Codigo>();
        try {
            ResultSet resultSet = this.select("SELECT DISTINCT C.id\n, C.idUnit, U.description\n, C.idProcess, P.description\n, C.idTypeOfStuff, T.description\nFROM codes as C, units as U, processes as P, typesOfStuff as T\nWHERE U.id = C.idUnit and P.id = C.idProcess and T.id = C.idTypeOfStuff");
            if (resultSet == null || !resultSet.next()) {
                arrayList = null;
            } else {
                do {
                    int n = resultSet.getInt(1);
                    Unidad unidad = new Unidad(resultSet.getString(2), resultSet.getString(3));
                    Proceso proceso = new Proceso("73000", "AUXILIAR ADMINISTRATIVO" /*resultSet.getString(5)*/);
                    TipoDePersonal tipoDePersonal = new TipoDePersonal(resultSet.getString(6), resultSet.getString(7));
                    arrayList.add(new Codigo(n, unidad, proceso, tipoDePersonal));
                } while (resultSet.next());
            }
        }
        catch (SQLException var2_3) {
            arrayList = null;
        }
        return arrayList;
    }

    public ArrayList<Oferta> getOffers() {
        ArrayList<Oferta> arrayList = new ArrayList<Oferta>();
        try {
            ResultSet resultSet = this.select("SELECT DISTINCT O.id\n, O.idTypeOfOffer, T1.description\nFROM offers as O, typesOfOffer as T1\nWHERE T1.id = O.idTypeOfOffer");
            if (!resultSet.next()) {
                arrayList = null;
            } else {
                do {
                    int n = resultSet.getInt(1);
                    TipoDeOferta tipoDeOferta = new TipoDeOferta(resultSet.getString(2), resultSet.getString(3));
                    SubtipoDeOferta subtipoDeOferta = new SubtipoDeOferta("0", "NOT USED");
                    arrayList.add(new Oferta(n, tipoDeOferta, subtipoDeOferta));
                } while (resultSet.next());
            }
        }
        catch (SQLException var2_3) {
            arrayList = null;
        }
        return arrayList;
    }

    public ArrayList<Acceso> getAccesses() {
        ArrayList<Acceso> arrayList = new ArrayList<Acceso>();
        try {
            ResultSet resultSet = this.select("SELECT id, description FROM access ");
            if (!resultSet.next()) {
                arrayList = null;
            } else {
                do {
                    arrayList.add(new Acceso(resultSet.getString(1), resultSet.getString(2)));
                } while (resultSet.next());
            }
        }
        catch (SQLException var2_3) {
            arrayList = null;
        }
        return arrayList;
    }

    public String getEntityDescription(String string, String string2) {
        String string3;
        ResultSet resultSet = this.select("SELECT description FROM " + string + " WHERE " + string + ".id='" + string2 + "' limit 1");
        if (resultSet == null) {
            string3 = "";
        } else {
            try {
                string3 = resultSet.getString(1);
            }
            catch (SQLException var5_5) {
                string3 = "";
            }
        }
        return string3;
    }

    public boolean setNewToZero() {
        boolean bl = false;
        bl = this.insert("UPDATE results SET new=0;");
        return bl;
    }

    public boolean insertResult(int n, int n2, int n3, int n4, String string, String string2, int n5, String string3, int n6) {
        boolean bl = false;
        bl = n5 == -1 ? this.insert("INSERT INTO results VALUES (NULL, CURRENT_TIMESTAMP, " + n + ", " + n2 + ", " + n3 + ", " + n4 + ", '" + string + "', '" + string2 + "', NULL, '" + string3 + "', " + n6 + ")") : this.insert("INSERT INTO results VALUES (NULL, CURRENT_TIMESTAMP, " + n + ", " + n2 + ", " + n3 + ", " + n4 + ", '" + string + "', '" + string2 + "', " + n5 + ", '" + string3 + "', " + n6 + ")");
        return bl;
    }

    public boolean insertResult(Result result) {
        boolean bl = true;
        boolean bl2 = false;
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        try {
            ResultSet resultSet = this.select("SELECT id, idCode, idOffer, idAccess, score, factor FROM results WHERE idCode=" + result.getIdCode() + " AND idOffer=" + result.getIdOffer() + " AND idAccess='" + result.getIdAccess() + "';");
            bl2 = false;
            while (resultSet != null && resultSet.next()) {
                bl2 = true;
                n = resultSet.getInt(5);
                n2 = resultSet.getInt(6);
                n3 = resultSet.getInt(1);
                this.execute("DELETE FROM results WHERE id=" + n3 + ";");
            }
            if (bl2 && n == result.getScore() && n2 == result.getFactor()) {
                if (this.insertResult(result.getIdCode(), result.getIdOffer(), result.getScore(), result.getFactor(), result.getScoredate(), result.getDate(), result.getNomLastYear(), result.getIdAccess(), 0)) {
                    result.setUpdated(true);
                } else {
                    bl = false;
                }
            } else if (this.insertResult(result.getIdCode(), result.getIdOffer(), result.getScore(), result.getFactor(), result.getScoredate(), result.getDate(), result.getNomLastYear(), result.getIdAccess(), 1)) {
                result.setUpdated(true);
            } else {
                bl = false;
            }
        }
        catch (SQLException var7_8) {
            bl = false;
        }
        return bl;
    }

    public ArrayList<InformResult> getInformResults() {
        ArrayList<InformResult> arrayList = new ArrayList<InformResult>();
        try {
            ResultSet resultSet = this.select("SELECT DISTINCT U.description, P.description, T.description, A.description, R.nominationsLastYear, R.score, R.factor, R.scoredate, R.date, R.new FROM codes as C, units as U, processes as P, typesOfOffer as T, access as A, results as R, offers as O WHERE R.idCode = C.id and U.id = C.idUnit and P.id = C.idProcess and T.id = O.idTypeOfOffer and A.id = R.idAccess  and R.idOffer = O.id ORDER BY R.idCode, P.id, A.id");
            if (resultSet == null || !resultSet.next()) {
                arrayList = null;
            } else {
                do {
                    arrayList.add(new InformResult(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5), resultSet.getInt(6), resultSet.getInt(7), resultSet.getString(9), resultSet.getString(8), resultSet.getInt(10) == 1));
                } while (resultSet.next());
            }
        }
        catch (SQLException var2_3) {
            arrayList = null;
        }
        return arrayList;
    }

    public String getDateLastConsult() {
        String string;
        try {
            ResultSet resultSet = this.select("select timestamp from results ORDER BY timestamp DESC limit 1");
            string = !resultSet.next() ? "No disponible" : resultSet.getString(1);
        }
        catch (SQLException var2_2) {
            string = "Error al recuperar";
        }
        return string;
    }
}

