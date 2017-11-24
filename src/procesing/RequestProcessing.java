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
import entities.Acceso;
import entities.Codigo;
import entities.Oferta;
import entities.Result;
import graphics.CampaignUIinfo;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import procesing.PostProcessing;
import procesing.RequestingThread;
import procesing.ResultStructure;
import procesing.TokenPool;

public class RequestProcessing
implements Runnable {
    private boolean debug = true;
    private boolean out = true;//http://www.juntadeandalucia.es/servicioandaluzdesalud/empleo/informe_notacorte_contratacion.php
    private String baseUrl = "http://www.juntadeandalucia.es/servicioandaluzdesalud/empleo/informe_notacorte_contratacion.php";
    private DBHandler dbHandler;
    private ArrayList<Codigo> codes;
    private ArrayList<Oferta> offers;
    private ArrayList<Acceso> accesses;
    private ArrayList<Result> results;
    private int numOfThreads;
    private CampaignUIinfo uiInfo;
    private TokenPool tokenPool;

    public RequestProcessing(DBHandler dBHandler, CampaignUIinfo campaignUIinfo, int n) {
        this.dbHandler = dBHandler;
        this.numOfThreads = n;
        this.uiInfo = campaignUIinfo;
        int n2 = n / 1;
        n2 = n2 > 1 ? n2 : 1;
        this.tokenPool = new TokenPool(n2);
    }

    @Override
    public void run() {
        this.uiInfo.changeButtonStatus(false);
        this.uiInfo.updateLog("Empezando consulta...");
        this.dbHandler.connect();
        this.results = new ArrayList();
        this.codes = this.dbHandler.getCodes();
        this.offers = this.dbHandler.getOffers();
        this.accesses = this.dbHandler.getAccesses();
        if (this.codes != null && this.offers != null && this.dbHandler != null) {
            int n;
            this.uiInfo.setTotalReqs(this.codes.size() * this.offers.size() * this.accesses.size());
            this.uiInfo.setNumReqs(0);
            ArrayList<RequestingThread> arrayList = new ArrayList<RequestingThread>();
            ResultStructure resultStructure = new ResultStructure();
            ArrayList<Thread> arrayList2 = new ArrayList<Thread>();
            int n2 = 1 + this.codes.size() / this.numOfThreads;
            int n3 = 0;
            for (int i = 0; i < this.numOfThreads; ++i) {
                ArrayList<Codigo> arrayList3 = new ArrayList<Codigo>();
                for (int j = 0; j < n2 && n3 < this.codes.size(); ++n3, ++j) {
                    arrayList3.add(this.codes.get(n3));
                }
                arrayList.add(new RequestingThread(this.dbHandler, this.uiInfo, arrayList3, (ArrayList)this.offers.clone(), (ArrayList)this.accesses.clone(), resultStructure, this.tokenPool));
                arrayList2.add(new Thread((Runnable)arrayList.get(i), "RT_" + i));
                if (!this.debug) continue;
                this.uiInfo.updateLog("Creado hilo RT_" + i + "...");
            }
            for (n = 0; n < arrayList2.size(); ++n) {
                ((Thread)arrayList2.get(n)).start();
            }
            for (n = 0; n < arrayList2.size(); ++n) {
                try {
                    ((Thread)arrayList2.get(n)).join();
                    continue;
                }
                catch (InterruptedException var8_11) {
                    this.uiInfo.stopRunning();
                    this.uiInfo.updateLog("Error al terminar hilo " + ((Thread)arrayList2.get(n)).getName() + ".");
                }
            }
            this.results = this.uiInfo.isRunning() ? resultStructure.getResults() : null;
        }
        if (this.uiInfo.isRunning()) {
            this.store();
        }
        this.dbHandler.disconnect();
        this.uiInfo.updateLog(" Creando informe...");
        try {
            new PostProcessing().doInform();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RequestProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.uiInfo.writeMsnLog("\n   El archivo se ha subido \n ");
    }

    private boolean store() {
        boolean bl = true;
        this.dbHandler.setNewToZero();
        for (Result result : this.results) {
            this.dbHandler.insertResult(result);
        }
        this.uiInfo.updateLog(" Terminado: " + this.results.size() + " resultados con nota");
        return bl;
    }

    public boolean isDebug() {
        return this.debug;
    }

    public void setDebug(boolean bl) {
        this.debug = bl;
    }

    public boolean isOut() {
        return this.out;
    }

    public void setOut(boolean bl) {
        this.out = bl;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public void setBaseUrl(String string) {
        this.baseUrl = string;
    }

    public DBHandler getDbHandler() {
        return this.dbHandler;
    }

    public void setDbHandler(DBHandler dBHandler) {
        this.dbHandler = dBHandler;
    }

    public boolean sendRequests(int n) {
        boolean bl = true;
        return bl;
    }

    private boolean sendRequest(int n, int n2) {
        boolean bl = true;
        return bl;
    }
}

