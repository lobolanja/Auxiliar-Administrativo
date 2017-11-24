/**
 * @author : Juan Carlos Chaves Puertas
 * @version : 1.0
 * @copyright : Copyright (C) 2017
 * @license : MIT (expat) License
 * @maintainer : Juan Carlos Chaves Puertas
 * @email : lobolanja@gmail.com
 */
package graphics;

import database.DBHandler;
import general.Constants;
import graphics.CampaignUIinfo;
import graphics.TextLog;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jdesktop.layout.GroupLayout;
import procesing.PostProcessing;
import procesing.QueryCache;
import procesing.RequestProcessing;

public class MainWindow
extends JFrame {
    static QueryCache queryCache;
    static DBHandler dbHandler;
    TextLog textLog;
    private JButton bDownloadLastQueryInform;
    private JButton bStartNewQuery;
    private JLabel jDateLastConsult;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JScrollPane jScrollPane1;
    private JSeparator jSeparator1;
    private JSeparator jSeparator2;
    private JSeparator jSeparator3;
    private JTabbedPane jTabbedPane2;
    private JTextArea mainLog;
    private JLabel num_founds;
    private JLabel num_reqs;
    private JSpinner numberOfThreadsSelect;
    private JLabel sjLabel68;
    private JLabel total_reqs;

    public MainWindow() {
        this.initComponents();
        dbHandler.connect();
        this.jDateLastConsult.setText(dbHandler.getDateLastConsult());
        dbHandler.disconnect();
        this.textLog = new TextLog(this.mainLog, this.num_reqs);
    }

    private void initComponents() {
        this.jTabbedPane2 = new JTabbedPane();
        this.jPanel2 = new JPanel();
        this.jLabel1 = new JLabel();
        this.bDownloadLastQueryInform = new JButton();
        this.jSeparator1 = new JSeparator();
        this.jLabel3 = new JLabel();
        this.bStartNewQuery = new JButton();
        this.jScrollPane1 = new JScrollPane();
        this.mainLog = new JTextArea();
        this.jDateLastConsult = new JLabel();
        this.jLabel8 = new JLabel();
        this.num_reqs = new JLabel();
        this.jLabel9 = new JLabel();
        this.total_reqs = new JLabel();
        this.jLabel2 = new JLabel();
        this.num_founds = new JLabel();
        this.jLabel11 = new JLabel();
        this.jPanel3 = new JPanel();
        this.jLabel10 = new JLabel();
        this.jPanel1 = new JPanel();
        this.jLabel4 = new JLabel();
        this.jSeparator2 = new JSeparator();
        this.jLabel6 = new JLabel();
        this.jSeparator3 = new JSeparator();
        this.sjLabel68 = new JLabel();
        this.numberOfThreadsSelect = new JSpinner();
        this.jLabel5 = new JLabel();
        this.jLabel7 = new JLabel();
        this.jLabel12 = new JLabel();
        this.setDefaultCloseOperation(3);
        this.setTitle("Auxiliar Administrativo nota de corte");
        this.setBackground(new Color(192, 192, 192));
        this.setMaximumSize(new Dimension(577, 378));
        this.setMinimumSize(new Dimension(577, 378));
        this.setResizable(false);
        this.jLabel1.setText("Fecha de \u00faltima consulta completa:");
        this.bDownloadLastQueryInform.setText("Generar informe de \u00faltima consulta");
        this.bDownloadLastQueryInform.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                try {
                    MainWindow.this.bDownloadLastQueryInformMouseClicked(mouseEvent);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        this.bDownloadLastQueryInform.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MainWindow.this.bDownloadLastQueryInformActionPerformed(actionEvent);
            }
        });
        this.jLabel3.setText("Nueva campa\u00f1a de consulta");
        this.bStartNewQuery.setText("Empezar");
        this.bStartNewQuery.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MainWindow.this.bStartNewQueryActionPerformed(actionEvent);
            }
        });
        this.mainLog.setColumns(20);
        this.mainLog.setRows(5);
        this.jScrollPane1.setViewportView(this.mainLog);
        this.jDateLastConsult.setText("___");
        this.jLabel8.setText("Realizadas");
        this.num_reqs.setText("0");
        this.jLabel9.setText("consultas de ");
        this.total_reqs.setText("0");
        this.jLabel2.setText(", encontrados ");
        this.num_founds.setText("0");
        this.jLabel11.setText("resultados");
        GroupLayout groupLayout = new GroupLayout((Container)this.jPanel2);
        this.jPanel2.setLayout((LayoutManager)groupLayout);
        groupLayout.setHorizontalGroup((GroupLayout.Group)groupLayout.createParallelGroup(1).add((GroupLayout.Group)groupLayout.createSequentialGroup().addContainerGap().add((GroupLayout.Group)groupLayout.createParallelGroup(1).add((Component)this.jSeparator1).add((Component)this.jScrollPane1).add((GroupLayout.Group)groupLayout.createSequentialGroup().add((Component)this.jLabel1).addPreferredGap(1).add((Component)this.jDateLastConsult, -2, 145, -2).addPreferredGap(1).add((Component)this.bDownloadLastQueryInform)).add((GroupLayout.Group)groupLayout.createSequentialGroup().add((GroupLayout.Group)groupLayout.createParallelGroup(1).add((GroupLayout.Group)groupLayout.createSequentialGroup().add((Component)this.jLabel8).addPreferredGap(1).add((Component)this.num_reqs, -1, 43, 32767).addPreferredGap(0).add((Component)this.jLabel9).addPreferredGap(0).add((Component)this.total_reqs, -1, -1, 32767)).add((Component)this.jLabel3, -2, 177, -2)).addPreferredGap(0).add((Component)this.jLabel2).addPreferredGap(0).add((Component)this.num_founds, -1, 50, 32767).addPreferredGap(0).add((Component)this.jLabel11).add(114, 114, 114).add((Component)this.bStartNewQuery))).addContainerGap()));
        groupLayout.setVerticalGroup((GroupLayout.Group)groupLayout.createParallelGroup(1).add((GroupLayout.Group)groupLayout.createSequentialGroup().addContainerGap().add((GroupLayout.Group)groupLayout.createParallelGroup(3).add((Component)this.jLabel1).add((Component)this.bDownloadLastQueryInform).add((Component)this.jDateLastConsult)).add(40, 40, 40).add((Component)this.jSeparator1, -2, 10, -2).addPreferredGap(0).add((Component)this.jLabel3).add(11, 11, 11).add((GroupLayout.Group)groupLayout.createParallelGroup(3).add((Component)this.jLabel8).add((Component)this.num_reqs).add((Component)this.jLabel9).add((Component)this.total_reqs).add((Component)this.bStartNewQuery).add((Component)this.jLabel2).add((Component)this.num_founds).add((Component)this.jLabel11)).addPreferredGap(1).add((Component)this.jScrollPane1, -1, 192, 32767).addContainerGap()));
        this.jTabbedPane2.addTab("Inicio", this.jPanel2);
        this.jLabel10.setBackground(new Color(0, 102, 51));
        this.jLabel10.setForeground(new Color(204, 204, 204));
        this.jLabel10.setHorizontalAlignment(0);
        this.jLabel10.setText("Actualmente no disponible");
        GroupLayout groupLayout2 = new GroupLayout((Container)this.jPanel3);
        this.jPanel3.setLayout((LayoutManager)groupLayout2);
        groupLayout2.setHorizontalGroup((GroupLayout.Group)groupLayout2.createParallelGroup(1).add(0, 580, 32767).add((GroupLayout.Group)groupLayout2.createParallelGroup(1).add((GroupLayout.Group)groupLayout2.createSequentialGroup().addContainerGap().add((Component)this.jLabel10, -1, 560, 32767).addContainerGap())));
        groupLayout2.setVerticalGroup((GroupLayout.Group)groupLayout2.createParallelGroup(1).add(0, 350, 32767).add((GroupLayout.Group)groupLayout2.createParallelGroup(1).add((GroupLayout.Group)groupLayout2.createSequentialGroup().add(168, 168, 168).add((Component)this.jLabel10).addContainerGap(168, 32767))));
        this.jTabbedPane2.addTab("Hist\u00f3rico", this.jPanel3);
        this.jLabel4.setText("General");
        this.jLabel6.setBackground(new Color(0, 102, 51));
        this.jLabel6.setForeground(new Color(204, 204, 204));
        this.jLabel6.setHorizontalAlignment(0);
        this.jLabel6.setText("Actualmente no disponible");
        this.sjLabel68.setText("Programaci\u00f3n");
        this.numberOfThreadsSelect.setModel(new SpinnerNumberModel(50, 1, 500, 1));
        this.numberOfThreadsSelect.setToolTipText("Un mayor n\u00famero de hilos implica m\u00e1s consultas simult\u00e1neas y, si la red y el equipo lo permiten, un menor tiempo para finalizar el barrido de consultas.\n\nUn n\u00famero de hilos demasiado grande saturar\u00e1 el sistema y la red.");
        this.numberOfThreadsSelect.setValue(5);
        this.numberOfThreadsSelect.addChangeListener(new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                MainWindow.this.numberOfThreadsSelectStateChanged(changeEvent);
            }
        });
        this.jLabel5.setText("N\u00famero de hilos");
        this.jLabel7.setForeground(new Color(102, 102, 102));
        this.jLabel7.setText("v" + Constants.getPROGRAM_VERSION());
        this.jLabel12.setForeground(new Color(102, 102, 102));
        this.jLabel12.setText("Build " + Constants.getBUILT_DATE());
        GroupLayout groupLayout3 = new GroupLayout((Container)this.jPanel1);
        this.jPanel1.setLayout((LayoutManager)groupLayout3);
        groupLayout3.setHorizontalGroup((GroupLayout.Group)groupLayout3.createParallelGroup(1).add((GroupLayout.Group)groupLayout3.createSequentialGroup().addContainerGap().add((GroupLayout.Group)groupLayout3.createParallelGroup(1).add((GroupLayout.Group)groupLayout3.createSequentialGroup().add((GroupLayout.Group)groupLayout3.createParallelGroup(1).add((Component)this.jLabel4).add((Component)this.sjLabel68).add((GroupLayout.Group)groupLayout3.createSequentialGroup().add(14, 14, 14).add((Component)this.jLabel5).addPreferredGap(1).add((Component)this.numberOfThreadsSelect, -1, 113, 32767))).add(423, 423, 423)).add((GroupLayout.Group)groupLayout3.createSequentialGroup().add((GroupLayout.Group)groupLayout3.createParallelGroup(1).add(2, (Component)this.jLabel6, -1, -1, 32767).add((Component)this.jSeparator2).add((Component)this.jSeparator3)).addContainerGap()).add((GroupLayout.Group)groupLayout3.createSequentialGroup().add((Component)this.jLabel7).addPreferredGap(0, -1, 32767).add((Component)this.jLabel12).addContainerGap()))));
        groupLayout3.setVerticalGroup((GroupLayout.Group)groupLayout3.createParallelGroup(1).add((GroupLayout.Group)groupLayout3.createSequentialGroup().addContainerGap().add((Component)this.jLabel4).addPreferredGap(1).add((GroupLayout.Group)groupLayout3.createParallelGroup(3).add((Component)this.numberOfThreadsSelect, -2, -1, -2).add((Component)this.jLabel5)).add(32, 32, 32).add((Component)this.jSeparator2, -2, 10, -2).addPreferredGap(0).add((Component)this.sjLabel68).addPreferredGap(0).add((Component)this.jLabel6).add(18, 18, 18).add((Component)this.jSeparator3, -2, 10, -2).addPreferredGap(0, 159, 32767).add((GroupLayout.Group)groupLayout3.createParallelGroup(3).add((Component)this.jLabel7).add((Component)this.jLabel12)).addContainerGap()));
        this.jTabbedPane2.addTab("Configuraci\u00f3n", this.jPanel1);
        GroupLayout groupLayout4 = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout((LayoutManager)groupLayout4);
        groupLayout4.setHorizontalGroup((GroupLayout.Group)groupLayout4.createParallelGroup(1).add((Component)this.jTabbedPane2));
        groupLayout4.setVerticalGroup((GroupLayout.Group)groupLayout4.createParallelGroup(1).add((Component)this.jTabbedPane2));
        this.pack();
    }

    private void bStartNewQueryActionPerformed(ActionEvent actionEvent) {
        boolean bl = false;
        RequestProcessing requestProcessing = new RequestProcessing(dbHandler, new CampaignUIinfo(this.mainLog, this.num_reqs, this.num_founds, this.total_reqs, this.bStartNewQuery), (Integer)this.numberOfThreadsSelect.getValue());
        new Thread((Runnable)requestProcessing, "requestProcess").start();
    }

    private void bDownloadLastQueryInformActionPerformed(ActionEvent actionEvent) {
    }

    private void bDownloadLastQueryInformMouseClicked(MouseEvent mouseEvent) throws FileNotFoundException {
        new PostProcessing().doInform();
    }

    private void numberOfThreadsSelectStateChanged(ChangeEvent changeEvent) {
    }

    public static void main(String[] arrstring) {
        try {
            UIManager.LookAndFeelInfo[] arrlookAndFeelInfo = UIManager.getInstalledLookAndFeels();
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException var1_2) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, var1_2);
        }
        catch (InstantiationException var1_3) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, var1_3);
        }
        catch (IllegalAccessException var1_4) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, var1_4);
        }
        catch (UnsupportedLookAndFeelException var1_5) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, var1_5);
        }
        queryCache = new QueryCache();
        dbHandler = new DBHandler();
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                MainWindow mainWindow = new MainWindow();
                mainWindow.setVisible(true);
            }
        });
    }

}

