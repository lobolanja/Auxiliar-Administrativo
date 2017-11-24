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
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import procesing.NotConnectionException;
import procesing.Request;
import procesing.ResultStructure;
import procesing.TokenPool;

class RequestingThread
implements Runnable {
    private boolean debug = true;
    private boolean out = true;
    private String baseUrl = "http://www.juntadeandalucia.es/servicioandaluzdesalud/empleo/informe_notacorte_contratacion.php";
    private DBHandler dbHandler;
    private ArrayList<Codigo> codes;
    private ArrayList<Oferta> offers;
    private ArrayList<Acceso> accesses;
    private ResultStructure rs;
    private CampaignUIinfo uiInfo;
    private TokenPool tp;

    public RequestingThread(DBHandler dBHandler, CampaignUIinfo campaignUIinfo, ArrayList<Codigo> arrayList, ArrayList<Oferta> arrayList2, ArrayList<Acceso> arrayList3, ResultStructure resultStructure, TokenPool tokenPool) {
        this.uiInfo = campaignUIinfo;
        this.dbHandler = dBHandler;
        this.codes = arrayList;
        this.offers = arrayList2;
        this.accesses = arrayList3;
        this.rs = resultStructure;
        this.tp = tokenPool;
    }

    @Override
    public void run() {
        if (this.debug) {
            this.uiInfo.updateLog("Iniciado hilo: " + Thread.currentThread().getName());
        }
        Iterator<Codigo> iterator = this.codes.iterator();
        while (iterator.hasNext() && this.uiInfo.isRunning()) {
            Codigo codigo = iterator.next();
            Iterator<Oferta> iterator2 = this.offers.iterator();
            while (iterator2.hasNext() && this.uiInfo.isRunning()) {
                Oferta oferta = iterator2.next();
                Iterator<Acceso> iterator3 = this.accesses.iterator();
                while (iterator3.hasNext() && this.uiInfo.isRunning()) {
                    Acceso acceso = iterator3.next();
                    boolean bl = false;
                    boolean bl2 = false;
                    int n = 0;
                    Request request = new Request(codigo, oferta, acceso, this.tp.getLessUsedCookies());
                    while (!bl) {
                        try {
                            bl2 = request.execute();
                            bl = true;
                        }
                        catch (NotConnectionException var11_12) {
                            System.out.println("Error while executing request." + ++n + " intents. Re-trying...");
                        }
                    }
                    if (bl2) {
                        Result result = request.getResult();
                        this.rs.addResult(result);
                        this.uiInfo.increaseNumFounds();
                    }
                    this.uiInfo.increaseNumReqs();
                }
            }
        }
        if (this.debug) {
            this.uiInfo.updateLog("Terminado hilo: " + Thread.currentThread().getName());
        }
    }
}

