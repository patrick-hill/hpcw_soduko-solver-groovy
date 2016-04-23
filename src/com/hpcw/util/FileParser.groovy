package com.hpcw.util

/**
 * Created by phill on 4/21/16.
 */
class FileParser {

    public def data

    void parseFile(File file) {
        data = new String[9][9]
        def name = file.getName()
        if(name.lastIndexOf('.') > -1) {
            def extension = name.substring(name.lastIndexOf('.')+1)
            switch(extension.toLowerCase()) {
                case 'txt':
                    parseTextFile(file)
                    break
                case 'csv':
                    parseCSV()
                    break
                case 'tsv':
                    parseTSV()
                    break
                default:
                    println "Unknown file extension"
            }
        }
    }

    void parseTextFile(def file) {
        def i = 0
        file.eachLine { String line ->
            line = line.trim()
            data[i] = (String[]) line.toCharArray()
            println "Data $i is: ${data[i]}"
            i++
        }
    }

    void parseCSV() {

    }

    void parseTSV() {

    }

}
