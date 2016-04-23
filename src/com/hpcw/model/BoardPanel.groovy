package com.hpcw.model

import groovy.beans.Bindable
import javax.swing.BorderFactory
import javax.swing.JPanel
import javax.swing.JTextField
import java.awt.Font
import java.awt.GridLayout
import java.awt.Color

/**
 * Created by phill on 4/21/16.
 */
class BoardPanel extends JPanel {

    public CustomTextField[][] fields = new CustomTextField[9][9]
    @Bindable String[][] data = new String[9][9]

    public BoardPanel() {
        super()
        this.setLayout(new GridLayout(cols: 3, rows: 3))
        // Add the 9 bordered Cells
        def panels = new JPanel[9]
        int i = 0
        9.times { x ->
            JPanel p = new JPanel()
            p.setLayout(new GridLayout(cols: 3, rows: 3))
            p.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            panels[x] = p
            // Add 9 text fields to each 3x3 Cell
            9.times { y ->
//                textField id: 'textField', text: bind('text', source: textModel, mutual: true)
                CustomTextField textField = new CustomTextField()
                textField.setHorizontalAlignment(JTextField.CENTER)
                textField.setFont(new Font("SansSerif", Font.BOLD, 22))
                fields[x][y] = textField
//                textField text: bind('text', source: {data[x][y]}, mutual: true)
            }
            this.add(p)
        }
        // 9 cells do 3 times 3 times
        def panel
        9.times { row ->
            9.times { col ->
                // Panel 0
                if (row < 3 && col < 3)
                    panel = 0
                // Panel 1
                if (row < 3 && col in (3..5))
                    panel = 1
                // Panel 2
                if (row < 3 && col > 5)
                    panel = 2

                // Panel 3: rows 3..5
                if (row in (3..5) && col < 3)
                    panel = 3
                if (row in (3..5) && col in (3..5))
                    panel = 4
                if (row in (3..5) && col > 5)
                    panel = 5

                // Panel 7: rows > 5
                if (row > 5 && col < 3)
                    panel = 6
                if (row > 5 && col in (3..5))
                    panel = 7
                if (row > 5 && col > 5)
                    panel = 8

                panels[panel].add(fields[row][col])
//                println "Panel $panel gets field ${fields[row][col].text}"
            }
        }
    }

    class CustomTextField extends JTextField {

        public CustomTextField() {
            super()
        }

        @Override
        void setText(String str) {
            if(str.isInteger() && str.toInteger() > 0 && str.toInteger() < 10) {
                super.setText(str)
            }
        }
    }
}
