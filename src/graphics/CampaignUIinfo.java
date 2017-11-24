/**
 * @author : Juan Carlos Chaves Puertas
 * @version : 1.0
 * @copyright : Copyright (C) 2017
 * @license : MIT (expat) License
 * @maintainer : Juan Carlos Chaves Puertas
 * @email : lobolanja@gmail.com
 */
package graphics;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class CampaignUIinfo {
    private JTextArea log;
    private JLabel num_reqs;
    private JLabel total_reqs;
    private JLabel num_founds;
    private JButton button;
    private int current_num_reqs;
    private boolean running;

    public CampaignUIinfo(JTextArea jTextArea, JLabel jLabel, JLabel jLabel2, JLabel jLabel3, JButton jButton) {
        this.log = jTextArea;
        this.num_reqs = jLabel;
        this.total_reqs = jLabel3;
        this.num_founds = jLabel2;
        this.button = jButton;
        this.current_num_reqs = 0;
        this.running = true;
    }

    public synchronized void updateLog(final String string) {
        try {
            SwingUtilities.invokeAndWait(new Runnable(){

                @Override
                public void run() {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    Date date = new Date();
                    String string2 = simpleDateFormat.format(date);
                    CampaignUIinfo.this.log.append(string2 + ": " + string + "\n");
                }
            });
        }
        catch (Exception var2_2) {
            System.out.println("ERROR while updating writing to log\n");
        }
    }

    public synchronized void writeMsnLog(final String string) {
        try {
            SwingUtilities.invokeAndWait(new Runnable(){

                @Override
                public void run() {
                    CampaignUIinfo.this.log.append(string);
                }
            });
        }
        catch (Exception var2_2) {
            System.out.println("ERROR while updating writing to log\n");
        }
    }

    public synchronized void changeButtonStatus(final boolean bl) {
        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                CampaignUIinfo.this.button.setEnabled(bl);
            }
        });
    }

    public synchronized void increaseNumReqs() {
        try {
            SwingUtilities.invokeAndWait(new Runnable(){

                @Override
                public void run() {
                    int n = Integer.parseInt(CampaignUIinfo.this.num_reqs.getText());
                    CampaignUIinfo.this.num_reqs.setText("" + ++n);
                }
            });
        }
        catch (Exception var1_1) {
            System.out.println("ERROR while updating num_reqs\n");
        }
    }

    public synchronized void setNumReqs(final int n) {
        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                int n2 = n;
                CampaignUIinfo.this.num_reqs.setText("" + n2);
            }
        });
    }

    public synchronized void increaseNumFounds() {
        try {
            SwingUtilities.invokeAndWait(new Runnable(){

                @Override
                public void run() {
                    int n = Integer.parseInt(CampaignUIinfo.this.num_founds.getText());
                    CampaignUIinfo.this.num_founds.setText("" + ++n);
                }
            });
        }
        catch (Exception var1_1) {
            System.out.println("ERROR while updating num_reqs\n");
        }
    }

    public synchronized void setNumFounds(final int n) {
        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                int n2 = n;
                CampaignUIinfo.this.num_founds.setText("" + n2);
            }
        });
    }

    public synchronized void setTotalReqs(final int n) {
        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                int n2 = n;
                CampaignUIinfo.this.total_reqs.setText("" + n2);
            }
        });
    }

    public boolean isRunning() {
        return this.running;
    }

    public void setRunning(boolean bl) {
        this.running = bl;
    }

    public void stopRunning() {
        this.setRunning(false);
    }

}

