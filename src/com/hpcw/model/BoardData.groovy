package com.hpcw.model

import com.hpcw.util.Utils

import java.awt.Color

/**
 * Created by phill on 4/22/16.
 */
class BoardData {

    def allVals = ['1','2','3','4','5','6','7','8','9']
    def blocks, rows, cols, cells, emptyBlocks

    public BoardData() {
        blocks = new BoardBlock[9][9]
        rows = new HashMap<Integer, List<Integer>>()
        cols = new HashMap<Integer, List<Integer>>()
        cells = new HashMap<Integer, List<Integer>>()
        emptyBlocks = new ArrayList<BoardBlock>()
    }

    void setData(def fileData, def fields) {
        9.times { x->
            9.times { y->
                blocks[x][y] = new BoardBlock(x,y,fileData[x][y], fields[x][y])
            }
        }

        updateHelpers()
        updateBlocksPossibles()
    }

    void updateHelpers() {
        9.times { x ->
            rows[x] = new ArrayList<Integer>(allVals)
            cols[x] = new ArrayList<Integer>(allVals)
            cells[x] = new ArrayList<Integer>(allVals)
        }
        9.times { x ->
            9.times { y ->
                def block = blocks[x][y]
                rows[x].remove(block.val)
                cols[x].remove(blocks[y][x].val)
                cells[Utils.getCell(x,y)].remove(block.val)
                if(block.val == 0)
                    emptyBlocks.add(block)
            }
        }
        println "SetData complete:"
        println "Rows: $rows"
        println "Cols: $cols"
        println "Cells: $cells"
    }

    void setBlockVal(def block, def val) {
        block.val = val
        block.field.setBackground(Color.CYAN)
        updateBlocksPossibles()
    }

    def updateBlocksPossibles() {
        9.times { x ->
            9.times { y ->
                def block = blocks[x][y]
                block.possibles.removeAll(getBlocksNotPossible(x,y))
                println "Possibles for: [$x,$y] Val: ${block.val} are: ${block.possibles}"
            }
        }
    }

    def getBlocksNotPossible(def row, def col) {
        def list = []
        9.times { x ->
            9.times { y ->
                def block = blocks[x][y]
                if(x == row || y == col || Utils.getCell(row,col) == Utils.getCell(x,y)) {
                    list << block.val
                }
            }
        }
        list
    }

    def getBlocksByRow(def row, def all=false) {
        def list = []
        blocks[row].each { block ->
            if(all) {
                list << block
            } else {
                if(block.isEmpty)
                    list << block
            }
        }
        list
    }

    def getBlocksByCol(def col, def all=false) {
        def list = []
        9.times { row ->
        def block = blocks[row][col]
            if (all) {
                list << block
            } else {
                if (block.isEmpty)
                    list << block
            }
        }
        list
    }

    def getBlocksByCell(def cell, def all=false) {
        def list = []
        9.times { x ->
            9.times { y ->
                if(Utils.getCell(x,y) == cell) {
                    def block = blocks[x][y]
                    if (all) {
                        list << block
                    } else {
                        if (block.isEmpty)
                            list << block
                    }
                }
            }
        }
        list
    }

    def isSolved() {
        def isSolved = true
        9.times { x ->
            9.times { y ->
                if( isSolved && blocks[x][y].isEmpty) {
                    isSovled = false
                }
            }
        }
        isSolved
    }



    class BoardBlock {

        def row, col, val, field, isEmpty = true
        def possibles = ['1', '2', '3', '4', '5', '6', '7', '8', '9']

        public BoardBlock(def row, def col, def val, field) {
            this.row = row
            this.col = col
            this.val = val
            this.field = field
            if(isValidVal()) {
                possibles = []
                field.setBackground(Color.lightGray)
                field.text = val
                isEmpty = false
            }
        }

        def setVal(def val) {
            this.val = val
            field.text = val
            possibles = []
        }

        def isValidVal() {
            return possibles.contains(val)
        }
    }
}
