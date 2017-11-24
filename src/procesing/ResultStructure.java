/**
 * @author : Juan Carlos Chaves Puertas
 * @version : 1.0
 * @copyright : Copyright (C) 2017
 * @license : MIT (expat) License
 * @maintainer : Juan Carlos Chaves Puertas
 * @email : lobolanja@gmail.com
 */
package procesing;

import entities.Result;
import java.io.PrintStream;
import java.util.ArrayList;

class ResultStructure {
    private ArrayList<Result> results;

    public ResultStructure() {
        this.results = new ArrayList();
    }

    public ResultStructure(ArrayList<Result> arrayList) {
        this.results = arrayList;
    }

    public synchronized void setResults(ArrayList<Result> arrayList) {
        this.results = arrayList;
    }

    public synchronized ArrayList<Result> getResults() {
        return this.results;
    }

    public synchronized void addResult(Result result) {
        System.out.println("A\u00f1adido elemento a RS por hilo " + Thread.currentThread().getName() + "\n");
        this.results.add(result);
    }
}

