package com.hpcw.util

/**
 * Created by phill on 4/25/16.
 */
class Utils {

    static getCell(def x, def y) {
        def cell
        if (x < 3 && y < 3)
            cell = 0
        if (x < 3 && y in (3..5))
            cell = 1
        if (x < 3 && y > 5)
            cell = 2
        if (x in (3..5) && y < 3)
            cell = 3
        if (x in (3..5) && y in (3..5))
            cell = 4
        if (x in (3..5) && y > 5)
            cell = 5
        if (x > 5 && y < 3)
            cell = 6
        if (x > 5 && y in (3..5))
            cell = 7
        if (x > 5 && y > 5)
            cell = 8
        return cell
    }
}
