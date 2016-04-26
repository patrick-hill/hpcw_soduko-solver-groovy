package com.hpcw.model

import com.hpcw.util.Utils
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
        9.times { row ->
            9.times { col ->
                panels[Utils.getCell(row,col)].add(fields[row][col])
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
