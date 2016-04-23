package com.hpcw.controllers

import com.hpcw.view.UI

/**
 * Created by phill on 4/21/16.
 */
public class Startup {

    static void main(args) {
        UI gui = new UI()
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                gui.run()
            }
        })
    }

}
