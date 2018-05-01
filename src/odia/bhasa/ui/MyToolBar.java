/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package odia.bhasa.ui;

//Toolbar

import java.awt.Dimension;
import javax.swing.JToolBar;

    class MyToolBar extends JToolBar
    {
        static int toolBarHt=35;
        MyToolBar()
        {
            //Set preferred size
            Dimension d=new Dimension();
            d.height=toolBarHt;
            d.width=200;
            setPreferredSize(d);
        }
    }