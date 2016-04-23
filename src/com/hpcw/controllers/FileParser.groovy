package com.hpcw.controllers

/**
 * Created by phill on 4/21/16.
 */
class FileParser {

    private File file
    private String[][] data = new String[9][9]

    public FileParser(File f) {
        this.file = f
    }

    def parseFile() {
        // Determine file type: first by extension then by content if needed
        def name = file.getName()
        // File Extension Present?
        if(name.lastIndexOf('.') > -1) {
            def extension = name.substring(name.lastIndexOf('.'))
            println "ext is: $extension"

        } else {

        }
        def i = 0
        file.eachLine { String line ->
            data[i] = (String[]) line.toCharArray()
            println "data $i is: ${data[i]}"
            i++
        }
//        println "data is: $data"
        validateData()
    }

    def validateData() {
    }

    void parseTextFile() {
        // can either be a list of digits of space delimited
        println "text is: ${file.text}"
        println "index 0 is: ${file.text[0]}"
//        if(file.text[0].eachLine { line ->
//            if(line.length() == 9) {
//                parse
//            }
//        }
    }

    void parseCSV() {

    }

    void parseTSV() {

    }

}
