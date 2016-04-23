package com.hpcw.view

import com.hpcw.controllers.FileParser
import com.hpcw.controllers.Startup
import com.hpcw.model.BoardData
import com.hpcw.model.BoardPanel
import groovy.swing.SwingBuilder
import javafx.scene.paint.Color

import javax.swing.BorderFactory
import javax.swing.JFileChooser
import javax.swing.JFormattedTextField
import javax.swing.JTextField
import java.awt.event.ActionEvent


/**
 * Created by phill on 4/21/16.
 */
class UI {

    def title = 'HPCW Soduko Solver'
    def swing = new SwingBuilder()
    def boardPanel
    def boardData = new BoardData()
    def frame

    def fields
    def puzzleName
    def output

    public UI() {}

    void run() {
        frame = swing.frame(
                title:title,
                location:[300,300],
                size:[600,400],
                defaultCloseOperation:javax.swing.WindowConstants.EXIT_ON_CLOSE) {
            menuBar {
                menu(text:'File') {
                    menuItem() { action(name:'New', closure:{ println("Clicked 'New'") }) }
                    menuItem() { action(name:'Open', closure:{ loadFile() }) }
                    menuItem() { action(name:'Save', enabled:true, closure:{ println("Clicked 'Save'") }) }
                    separator()
                    menuItem() { action(name:'Close', enabled:true, closure:{ closeWindow() }) }
                }
                menu(text:'Load Samples', enabled:false) {
                    menuItem() { action(name:'Easy 1', closure:{ println("Clicked ") }) }
                    menuItem() { action(name:'Easy 2', closure:{ showMVCDemo() }) }
                    menuItem() { action(name:'Difficult', closure:{ showTableLayoutDemo() }) }
                }
                menu(text:'Help') {
                    menuItem() { action(name:'About', closure:{ showAbout() }) }
                }
            }
            splitPane {
                panel(border:BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), 'Options')) {
                    borderLayout()
                    vbox(constraints:NORTH) {
                        panel {
                            borderLayout()
                            label(text:'Puzzle Name', constraints:WEST, toolTipText:'Enter A Puzzle Name')
                            textField(text: puzzleName, constraints:CENTER, toolTipText:'Puzzle Name')
                        }
                        panel {
                            borderLayout()
                            label(text:'Solve Method', constraints:WEST, toolTipText:'Choose a solving method')
                            comboBox(enabled: false, items:['Heuristic', 'Brute-force'], constraints:CENTER, toolTipText:'Choose solving method')
                        }
                        button(text:'Solve', actionPerformed:{ event -> println("closure fired with event: " + event) })
                        panel {
                            borderLayout()
                            label(text:'Output', constraints:WEST)
                        }
                    }
                    scrollPane(constraints:CENTER, border:BorderFactory.createRaisedBevelBorder()) {
                        textArea(text:output, enabled: false, toolTipText:"Click 'Solve' and watch the solved steps here")
                    }
                }
                scrollPane {
                    panel(boardPanel = new BoardPanel())
                }
            }
        }
        frame.show()
    }

    void closeWindow() {
        frame.dispose()
    }

    void loadFile() {
        File file = openFileDialog()
        println "loadFile file is: ${file.path}"
        def data = new FileParser(file).parseFile()
        boardData.setData(data)
        updateFields()
    }

    void updateFields() {
        // get fields
        fields = boardPanel.fields
        9.times { x ->
            9.times { y ->
                fields[x][y].text = boardData.data[x][y]
                if(boardData.data[x][y] != 0) {
                    fields[x][y].setBackground(Color.ALICEBLUE)
                }
            }
        }

    }

    def openFileDialog() {
        def fileDialog = new JFileChooser(dialogTitle: "Choose File", fileSelectionMode: JFileChooser.FILES_ONLY, currentDirectory: new File(System.getProperty("user.dir")))
        fileDialog.showOpenDialog()
        def file = fileDialog.selectedFile
        println "Files is $file"
        file
    }

    void showAbout() {
        // this version doesn't auto-size & position the dialog
        /*
        def dialog = swing.dialog(owner:frame, title:'About GroovySwing') {
            optionPane(message:'Welcome to the wonderful world of GroovySwing')
        }
        */
        def pane = swing.optionPane(message:'Coming Soon ;)')
        def dialog = pane.createDialog(frame, "About $title")
        dialog.show()
    }

}