/**
 * @author : Juan Carlos Chaves Puertas
 * @version : 1.0
 * @copyright : Copyright (C) 2017
 * @license : MIT (expat) License
 * @maintainer : Juan Carlos Chaves Puertas
 * @email : lobolanja@gmail.com
 */
package graphics;

import java.awt.Container;
import java.awt.LayoutManager;
import javax.swing.GroupLayout;
import javax.swing.JPanel;

public class NewJPanel
extends JPanel {
    public static void main(String[] arrstring) {
        new NewJPanel();
    }

    public NewJPanel() {
        this.initComponents();
    }

    private void initComponents() {
        GroupLayout groupLayout = new GroupLayout(this);
        this.setLayout(groupLayout);
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 400, 32767));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 300, 32767));
    }
}

