/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package odia.bhasa.ui;

//Side Panel

import java.awt.Dimension;
import javax.swing.JPanel;

    class SidePanel extends JPanel
    {
        static int minHeight=MainWindow.minHeight,spWidth=MainWindow.spWidth;
        SidePanel()
        {
            //Set preferred size
            Dimension d=new Dimension();
            d.height=minHeight;
            d.width=spWidth;
            setPreferredSize(d);
        }
    }