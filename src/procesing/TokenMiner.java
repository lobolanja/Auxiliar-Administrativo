/**
 * @author : Juan Carlos Chaves Puertas
 * @version : 1.0
 * @copyright : Copyright (C) 2017
 * @license : MIT (expat) License
 * @maintainer : Juan Carlos Chaves Puertas
 * @email : lobolanja@gmail.com
 */
package procesing;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import procesing.Token;

class TokenMiner
implements Runnable {
    private final Token token;
    private boolean vldBolsaFound = false;
    private boolean cookiesFound = false;

    TokenMiner(Token token) {
        this.token = token;
    }

    @Override
    public void run() {
        try {
            Connection.Response response;
            this.token.setStatus(Token.Status.FETCHING);
            try {
                response = Jsoup.connect((String)"http://www.juntadeandalucia.es/servicioandaluzdesalud/profesionales/personaltemporal/bempleo.asp").maxBodySize(0).timeout(10000).execute();
                Pattern pattern = Pattern.compile("http:\\/\\/www.juntadeandalucia.es\\/servicioandaluzdesalud\\/principal\\/empleo.asp\\?IdBolsa=([0-9]+)");
                Matcher matcher = pattern.matcher(response.body());
                if (matcher.find()) {
                    this.token.setVldBolsa(matcher.group(1));
                    this.vldBolsaFound = true;
                }
            }
            catch (IOException var1_2) {
                System.out.println("Token miner can't connect to bempleo.asp.");
                throw new IOException();
            }
            if (this.vldBolsaFound) {
                try {
                    System.setProperty("javax.net.ssl.trustStore", "./ks.jks");
                    response = Jsoup.connect((String)("https://ws035.juntadeandalucia.es/bolsa/http/index.php?vIdBolsa=" + this.token.getVldBolsa())).maxBodySize(0).timeout(10000).execute();
                    if (response.cookies() != null) {
                        this.token.setCookies(response.cookies());
                        this.cookiesFound = true;
                    }
                }
                catch (IOException var1_3) {
                    System.out.println("Token miner can't connect to bolsa/http/index.php.");
                    throw new IOException();
                }
            }
            if (this.cookiesFound && this.vldBolsaFound) {
                this.token.clearTimesUsed();
                this.token.clearNumOfErrors();
                this.token.setStatus(Token.Status.INITIALIZED);
            }
        }
        catch (IOException var1_4) {
            System.err.println("Error in TokenMiner execution: " + var1_4.toString());
            this.token.setStatus(Token.Status.UNINITIALIZED);
        }
        catch (Exception var1_5) {
            System.err.println("From TokenMiner:" + var1_5.toString());
            this.token.setStatus(Token.Status.UNINITIALIZED);
        }
    }
}

