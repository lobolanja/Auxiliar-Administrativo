/**
 * @author : Juan Carlos Chaves Puertas
 * @version : 1.0
 * @copyright : Copyright (C) 2017
 * @license : MIT (expat) License
 * @maintainer : Juan Carlos Chaves Puertas
 * @email : lobolanja@gmail.com
 */
package procesing;

import java.io.PrintStream;
import java.util.Map;
import procesing.TokenMiner;

class Token {
    private Map<String, String> cookies;
    private String vldBolsa;
    private int timesUsed = 0;
    private int numOfErrors = 0;
    private Status status = Status.UNINITIALIZED;
    private Thread t;

    Token() {
    }

    Map<String, String> getCookies() {
        return this.cookies;
    }

    void setCookies(Map<String, String> map) {
        this.cookies = map;
    }

    String getVldBolsa() {
        return this.vldBolsa;
    }

    void setVldBolsa(String string) {
        this.vldBolsa = string;
    }

    void incTimesUsed() {
        ++this.timesUsed;
    }

    int getTimesUsed() {
        return this.timesUsed;
    }

    void setTimesUsed(int n) {
        this.timesUsed = n;
    }

    void clearTimesUsed() {
        this.timesUsed = 0;
    }

    int getNumOfErrors() {
        return this.numOfErrors;
    }

    void clearNumOfErrors() {
        this.numOfErrors = 0;
    }

    void incNumOfErrors() {
        ++this.numOfErrors;
    }

    boolean refresh() {
        boolean bl = true;
        try {
            this.initializeMiner();
            this.joinMiner();
            if (this.getStatus() != Status.INITIALIZED) {
                bl = false;
            }
        }
        catch (InterruptedException var2_2) {
            System.err.println("Error fetching token.");
            bl = false;
        }
        catch (Exception var2_3) {
            System.err.println(var2_3.toString());
        }
        return bl;
    }

    private void initializeMiner() {
        this.t = new Thread((Runnable)new TokenMiner(this), "ThreadMiner");
        this.t.start();
    }

    private void joinMiner() throws InterruptedException {
        this.t.join();
    }

    public Status getStatus() {
        return this.status;
    }

    public boolean isInitialized() {
        return this.status == Status.INITIALIZED;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    static enum Status {
        UNINITIALIZED,
        FETCHING,
        INITIALIZED;
        

        private Status() {
        }
    }

}

