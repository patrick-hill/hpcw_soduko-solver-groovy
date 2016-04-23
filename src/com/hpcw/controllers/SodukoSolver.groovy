package com.hpcw.controllers

import com.hpcw.model.BoardData
import com.hpcw.util.*
import com.hpcw.view.UI

/**
 * Created by phill on 4/22/16.
 */
class SodukoSolver {

    def ui
    def boardData
    def fileParser
    def data

    static void main(args) {
        SodukoSolver solver = new SodukoSolver()
    }

    public SodukoSolver() {
        ui = new UI(this)
        boardData = new BoardData()
        fileParser = new FileParser()

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ui.run()
            }
        })
    }

    void loadFile(File f) {
        println "File is : ${f.path}"
        fileParser.parseFile(f)
        println "data is: ${fileParser.data}"
        boardData.setData(fileParser.data)
        updateFields()
    }

    void updateFields() {
        9.times { x ->
            9.times { y ->
                ui.boardPanel.fields[x][y].text = fileParser.data[x][y]
            }
        }
    }

}
