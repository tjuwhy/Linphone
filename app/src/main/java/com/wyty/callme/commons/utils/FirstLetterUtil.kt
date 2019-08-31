package com.wyty.callme.commons.utils

object FirstLetterUtil {
    private val END = 63486 // 按照声母表示，这个表是在GB2312中的出现的第一个汉字，也就是说“啊”是代表首字母a的第一个汉字。
    // // i, u, v都不做声母, 自定规则跟随前面的字母
    private val charTable = charArrayOf(
        '啊',
        '芭',
        '擦',
        '搭',
        '蛾',
        '发',
        '噶',
        '哈',
        '哈',
        '击',
        '喀',
        '垃',
        '妈',
        '拿',
        '哦',
        '啪',
        '期',
        '然',
        '撒',
        '塌',
        '塌',
        '塌',
        '挖',
        '昔',
        '压',
        '匝'
    )
    // 二十六个字母区间对应二十七个端点
    // GB2312码汉字区间十进制表示
    private val table = IntArray(27)
    // 对应首字母区间表
    private val initialTable = charArrayOf(
        'a',
        'b',
        'c',
        'd',
        'e',
        'f',
        'g',
        'h',
        'h',
        'j',
        'k',
        'l',
        'm',
        'n',
        'o',
        'p',
        'q',
        'r',
        's',
        't',
        't',
        't',
        'w',
        'x',
        'y',
        'z'
    )

    // 初始化
    init {
        for (i in 0..25) {
            table[i] = gbValue(charTable[i])
            // 得到GB2312码的首字母区间端点表，十进制。
        }
        table[26] = END
        // 区间表结尾
    }

    fun getFirstLetter(sourceStr: String): String {
        var result = StringBuilder()
        val str = sourceStr.toLowerCase()
        val StrLength = str.length
        var i: Int
        try {
            i = 0
            while (i < StrLength) {
                result.append(Char2Initial(str[i]))
                i++
            }
        } catch (e: Exception) {
            result = StringBuilder()
        }

        return result.toString()
    }

    private fun Char2Initial(ch: Char): Char {
        // 对英文字母的处理：小写字母转换为大写，大写的直接返回
        if (ch >= 'a' && ch <= 'z') {
            return ch
        }
        if (ch >= 'A' && ch <= 'Z') {
            return ch
        }
        // 对非英文字母的处理：转化为首字母，然后判断是否在码表范围内，
        // 若不是，则直接返回。
        // 若是，则在码表内的进行判断。
        val gb = gbValue(ch)// 汉字转换首字母
        val BEGIN = 45217
        if (gb < BEGIN || gb > END)
        // 在码表区间之前，直接返回
        {
            return ch
        }
        var i: Int
        i = 0
        while (i < 26) {
            // 判断匹配码表区间，匹配到就break,判断区间形如“[,)”
            if (gb >= table[i] && gb < table[i + 1]) {
                break
            }
            i++
        }
        if (gb == END) {
            // 补上GB2312区间最右端
            i = 25
        }
        return initialTable[i]
        // 在码表区间中，返回首字母
    }

    private fun gbValue(ch: Char): Int {
        // 将一个汉字（GB2312）转换为十进制表示。
        var str = ""
        str += ch
        try {
            val bytes = str.toByteArray(charset("GB2312"))
            return if (bytes.size < 2) {
                0
            } else (bytes[0].toInt().shl(8) and 0xff00) + (bytes[1].toInt() and 0xff)
        } catch (e: Exception) {
            return 0
        }

    }

}
