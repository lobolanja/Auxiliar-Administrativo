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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import procesing.Token;

public class TokenPool {
    private final int maxErrors = 9;
    private final ArrayList<Token> tokenList = new ArrayList();

    public TokenPool(int n) {
        this.resizePool(n);
    }

    public int getPoolSize() {
        return this.tokenList.size();
    }

    public final boolean resizePool(int n) {
        boolean bl = true;
        while (bl && n > this.tokenList.size()) {
            Token token = new Token();
            token.refresh();
            if (!token.isInitialized()) {
                bl = false;
                continue;
            }
            this.tokenList.add(token);
        }
        while (n < this.tokenList.size() && bl) {
            this.tokenList.remove(this.getMostUsed());
        }
        return bl;
    }

    private Token getMostUsed() {
        Iterator<Token> iterator = this.tokenList.iterator();
        Token token = iterator.next();
        while (iterator.hasNext()) {
            Token token2 = iterator.next();
            if (!token2.isInitialized() || token2.getTimesUsed() <= token.getTimesUsed()) continue;
            token = token2;
        }
        return token;
    }

    public Map<String, String> getNextValidCookies() {
        for (Token token : this.tokenList) {
            if (!token.isInitialized() || token.getNumOfErrors() >= this.maxErrors) continue;
            return token.getCookies();
        }
        return null;
    }

    public Map<String, String> getLessUsedCookies() {
        Iterator<Token> iterator = this.tokenList.iterator();
        Token token = iterator.next();
        while (iterator.hasNext()) {
            Token token2 = iterator.next();
            if (!token2.isInitialized() || token2.getNumOfErrors() >= this.maxErrors || token2.getNumOfErrors() >= token.getNumOfErrors() || token2.getTimesUsed() >= token.getTimesUsed()) continue;
            token = token2;
        }
        return token.getCookies();
    }

    public void renewAll() {
        for (Token token : this.tokenList) {
            token.refresh();
        }
    }

    public void printAllTokens() {
        for (Token token : this.tokenList) {
            System.out.println("_____________________________________");
            System.out.println("idBolsa=" + token.getVldBolsa());
            System.out.println("cookies=" + token.getCookies());
            System.out.println("_____________________________________");
        }
    }
}

