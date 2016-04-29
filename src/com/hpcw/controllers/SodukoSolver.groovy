package com.hpcw.controllers

import com.hpcw.model.BoardData
import com.hpcw.util.*
import com.hpcw.view.UI

import java.awt.Color

/**
 * Created by phill on 4/22/16.
 */
class SodukoSolver {

    def ui
    def boardData
    def fileParser

    static void main(args) {
        SodukoSolver solver = new SodukoSolver()
    }

    public SodukoSolver() {
        boardData = new BoardData()
        ui = new UI(this)
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
        boardData.setData(fileParser.data, ui.fields)
    }

    void solve() {
        checkBlocks()
//        crossCheck()
        boardData.updateBlocksPossibles()
    }

    void checkBlocks() {
        def changed = false
        9.times { x ->
            9.times { y ->
                def block = boardData.blocks[x][y]
                if(block.isEmpty) {
                    changed = checkBlockIsSolved(block)
                    changed = checkBlockCellValues(block, x, y)
                }
            }
        }
        if(changed) {
            checkBlocks()
        }
//        if( boardData.isSolved() ) {
//            ui.showCompleteDialog()
//        }
    }

    def checkBlockIsSolved(def block) {
        def changed = false
        if (block.possibles.size == 1) {
            boardData.setBlockVal(block, block.possibles[0])
            changed = true
            boardData.updateBlocksPossibles()
        }
        changed
    }

    def checkBlockCellValues(block, row, col) {
        def changed = false
        def cell = Utils.getCell(row, col)
        def cellBlocks = boardData.getBlocksByCell(cell)
        cellBlocks.remove(block)
        block.possibles.each { blockVal ->
            // Do any of the cells blocks also have this value? if not, it's solved
            if( (cellBlocks.findAll { it.possibles.contains(blockVal) }).size() == 0 ) {
                boardData.setBlockVal(block, blockVal)
                changed = true
            }
        }
        changed
    }

    void crossCheck() {
        // foreach empty block, check the intersection of rows, cols, cells
        // example from 1.txt
        // x: 4, y: 3 is 5
        // rows: 4:[1, 2, 3, 4, 5, 6, 9]
        // cols: 3:[4, 5, 9]
        // Cell: 4:[3, 4, 5, 6, 9]
        // Solution: Remove other columns possibilities to leave just 5
        9.times { x ->
            9.times { y ->
                def block = boardData.blocks[x][y]
                if(block.isEmpty) {
                    def rowsBlocks = boardData.getBlocksByRow(x)
                    def colsBlocks = boardData.getBlocksByCol(y)
                    def cellBlocks = boardData.getBlocksByCell(Utils.getCell(x,y))




                    def row = boardData.rows[x]
                    def col = boardData.cols[y]
                    def cell = boardData.cells[Utils.getCell(x,y)]
                    def possibles = row.intersect(col).intersect(cell)
                    if(possibles.size() == 1) {
                        println "Found one at: $x,$y Possibles: ${possibles}"
                        println "Rows: ${boardData.rows[x]}"
                        println "Cols: ${boardData.cols[y]}"
                        println "Cells: ${boardData.cells[Utils.getCell(x,y)]}"
                    }


//                    List<Integer> possibles = new ArrayList<Integer>(boardData.allVals)
//                    // Now check row,col,cell possibles against blocks
//                    possibles.removeAll(boardData.rows[x])
//                    possibles.removeAll(boardData.cols[y])
//                    possibles.removeAll(boardData.cells[Utils.getCell(x,y)])
//                    if(possibles.size() == 1) {
//                        println "Found one at: $x,$y Possibles: ${possibles}"
//                        println "Rows: ${boardData.rows[x]}"
//                        println "Cols: ${boardData.cols[y]}"
//                        println "Cells: ${boardData.cells[Utils.getCell(x,y)]}"
//                    }

//                    println "Remaining possibles are: $possibles"
                }
            }
        }
    }



}
