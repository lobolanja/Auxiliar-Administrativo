/**
 * @author : Juan Carlos Chaves Puertas
 * @version : 1.0
 * @copyright : Copyright (C) 2017
 * @license : MIT (expat) License
 * @maintainer : Juan Carlos Chaves Puertas
 * @email : lobolanja@gmail.com
 */
package graphics;

import javax.swing.JLabel;
import javax.swing.JTextArea;

public class TextLog {
    String msg;
    int num_req;
    int num_total;
    boolean valueSet = false;
    JTextArea logText;
    JLabel req;

    public TextLog(JTextArea jTextArea, JLabel jLabel) {
        this.logText = jTextArea;
        this.req = jLabel;
        this.num_req = 0;
        this.num_total = 0;
    }

    public synchronized String get() {
        if (!this.valueSet) {
            try {
                this.wait();
            }
            catch (InterruptedException var1_1) {
                // empty catch block
            }
        }
        this.logText.append(this.msg);
        this.valueSet = false;
        this.notify();
        return this.msg;
    }

    public synchronized void put(String string) {
        if (this.valueSet) {
            try {
                this.wait();
            }
            catch (InterruptedException var2_2) {
                // empty catch block
            }
        }
        this.msg = string;
        this.valueSet = true;
        this.notify();
    }

    public synchronized void set_num_req(int n) {
        if (this.valueSet) {
            try {
                this.wait();
            }
            catch (InterruptedException var2_2) {
                // empty catch block
            }
        }
        this.num_req = n;
        this.valueSet = true;
        this.notify();
    }

    public synchronized void set_num_total(int n) {
        if (this.valueSet) {
            try {
                this.wait();
            }
            catch (InterruptedException var2_2) {
                // empty catch block
            }
        }
        this.num_total = n;
        this.valueSet = true;
        this.notify();
    }

    public synchronized void get_num_req() {
        if (!this.valueSet) {
            try {
                this.wait();
            }
            catch (InterruptedException var1_1) {
                // empty catch block
            }
        }
        this.req.setText("" + this.num_req + " de " + this.num_total);
        this.valueSet = false;
        this.notify();
    }
}

