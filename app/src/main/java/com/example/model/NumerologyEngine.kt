package com.example.model

import kotlin.math.roundToInt

enum class CalculationMode(
    val id: String,
    val title: String,
    val subTitle: String,
    val icon: String,
    val formTitle: String,
    val formSub: String,
    val resultLabel: String
) {
    LOVE(
        id = "love",
        title = "Prem Milan",
        subTitle = "Love Calculate — pyaar ka success score",
        icon = "💑",
        formTitle = "Prem Milan 💞",
        formSub = "Dono ka pyaar kitna successful hoga, ank khud bata denge",
        resultLabel = "Prem Milan Result"
    ),
    DATE(
        id = "date",
        title = "Tithi Milan",
        subTitle = "Date Calculate — janam tithi ka ank milan",
        icon = "📅",
        formTitle = "Tithi Milan 📅",
        formSub = "Janam tithi ke ank se rishte ka mel dekhein",
        resultLabel = "Tithi Milan Result"
    ),
    NAME(
        id = "name",
        title = "Naam Ank",
        subTitle = "Name Calculate — naam ki numerology match",
        icon = "🔤",
        formTitle = "Naam Ank 🔤",
        formSub = "Naam ki numerology se dosti-pyaar ka connection",
        resultLabel = "Naam Ank Result"
    )
}

enum class CompatibilityTier(
    val label: String,
    val verdict: String,
    val pros: List<String>,
    val cons: List<String>,
    val remedies: List<String>
) {
    EXCELLENT(
        label = "Shaandar Jodi 🌟",
        verdict = "Bahut hi khaas milan hai!",
        pros = listOf(
            "Ek dusre ko bina kahe samajhne ki kala aapme hai",
            "Trust aur respect ka level bahut mazboot hai",
            "Baat cheet naturally khul kar aur smooth hoti hai"
        ),
        cons = listOf(
            "Itna comfort kabhi chhoti baaton ko ignore karwa sakta hai",
            "Ek dusre se expectations thodi zyada na badhayein"
        ),
        remedies = listOf(
            "Har Shukrawar thodi der sirf dono ke liye nikaalein, phone door rakhein",
            "Dono kisi shubh din ek jaisa rang pehnein, bandhan aur mazboot hoga"
        )
    ),
    GOOD(
        label = "Achhi Jodi 💞",
        verdict = "Ank thik-thak sanket de rahe hain, bas thoda dhyan chahiye.",
        pros = listOf(
            "Dono mein caring aur samajhne ka nature hai",
            "Mushkil samay mein ek dusre ka saath dete ho",
            "Rishte mein aage badhne ka acha potential hai"
        ),
        cons = listOf(
            "Kabhi kabhi ego beech mein aa sakta hai",
            "Patience thoda aur badhane ki zarurat hai"
        ),
        remedies = listOf(
            "Mangalwar ko kisi zaroori baat pe khul kar charcha karein",
            "Ek dusre ke favorite rang ka chhota gift dein"
        )
    ),
    AVERAGE(
        label = "Thik-Thak Jodi 🌤️",
        verdict = "Mehnat se ye rishta aur nikhrega.",
        pros = listOf(
            "Rishta nibhane ki sacchi chaah dono mein hai",
            "Sahi samajh se bahut kuch behtar ho sakta hai"
        ),
        cons = listOf(
            "Baat cheet mein kabhi kabhi gap aa sakta hai",
            "Priorities alag ho sakti hain, samjhaute ki zarurat"
        ),
        remedies = listOf(
            "Roz sirf 10 minute bina distraction ke baat karein",
            "Somvar ko shanti aur samajh ke liye thoda dhyaan lagayein"
        )
    ),
    GROWING(
        label = "Badhta Rishta 🌱",
        verdict = "Ank abhi shuruaat dikha rahe hain, mehnat rang layegi.",
        pros = listOf(
            "Rishta banaye rakhne ki chaah dikh rahi hai",
            "Sahi koshish se bade badlaav mumkin hain"
        ),
        cons = listOf(
            "Abhi thodi zyada samajh aur dhairya chahiye",
            "Bharosa banane mein thoda waqt lag sakta hai"
        ),
        remedies = listOf(
            "Bina judge kiye ek dusre ki baat dhyan se sunein",
            "Guruvaar ko sakaratmak soch ke saath din shuru karein"
        )
    )
}

data class MatchResult(
    val mode: CalculationMode,
    val nameA: String,
    val dobA: String,
    val nameB: String,
    val dobB: String,
    val score: Int,
    val tier: CompatibilityTier,
    val wishText: String
)

object NumerologyEngine {
    private val chaldeanMap = mapOf(
        'A' to 1, 'I' to 1, 'J' to 1, 'Q' to 1, 'Y' to 1,
        'B' to 2, 'K' to 2, 'R' to 2,
        'C' to 3, 'G' to 3, 'L' to 3, 'S' to 3,
        'D' to 4, 'M' to 4, 'T' to 4,
        'E' to 5, 'H' to 5, 'N' to 5, 'X' to 5,
        'U' to 6, 'V' to 6, 'W' to 6,
        'O' to 7, 'Z' to 7,
        'F' to 8, 'P' to 8
    )

    private val H = arrayOf(
        intArrayOf(),
        intArrayOf(0, 90, 75, 82, 60, 78, 70, 65, 58, 85),
        intArrayOf(0, 75, 88, 70, 80, 62, 84, 60, 72, 68),
        intArrayOf(0, 82, 70, 90, 65, 85, 68, 78, 60, 74),
        intArrayOf(0, 60, 80, 65, 86, 58, 76, 62, 84, 55),
        intArrayOf(0, 78, 62, 85, 58, 90, 60, 80, 65, 72),
        intArrayOf(0, 70, 84, 68, 76, 60, 92, 58, 78, 66),
        intArrayOf(0, 65, 60, 78, 62, 80, 58, 88, 55, 76),
        intArrayOf(0, 58, 72, 60, 84, 65, 78, 55, 90, 60),
        intArrayOf(0, 85, 68, 74, 55, 72, 66, 76, 60, 94)
    )

    fun reduceNum(n: Int): Int {
        var current = n
        while (current > 9) {
            current = current.toString().map { it.digitToInt() }.sum()
        }
        return if (current == 0) 1 else current
    }

    fun nameNumber(name: String): Int {
        var sum = 0
        name.uppercase().forEach { ch ->
            chaldeanMap[ch]?.let { sum += it }
        }
        val reduced = reduceNum(sum)
        return if (reduced == 0) 1 else reduced
    }

    fun dobNumber(dob: String): Int {
        var sum = 0
        dob.forEach { ch ->
            if (ch.isDigit()) sum += ch.digitToInt()
        }
        val reduced = reduceNum(sum)
        return if (reduced == 0) 1 else reduced
    }

    private fun jsHashCode(str: String): Long {
        var hash = 0L
        for (ch in str) {
            hash = (hash * 31L + ch.code.toLong()) and 0xFFFFFFFFL
        }
        return hash
    }

    private fun getVariation(str: String): Int {
        val hash = jsHashCode(str)
        return ((hash % 9L).toInt()) - 4 // -4..+4
    }

    private fun clamp(n: Int, min: Int, max: Int): Int {
        return maxOf(min, minOf(max, n))
    }

    fun calculateScore(mode: CalculationMode, nameA: String, dobA: String, nameB: String, dobB: String): MatchResult {
        val lp1 = dobNumber(dobA)
        val lp2 = dobNumber(dobB)
        val nn1 = nameNumber(nameA)
        val nn2 = nameNumber(nameB)

        val score = when (mode) {
            CalculationMode.LOVE -> {
                val base = (H[lp1][lp2] + H[nn1][nn2]) / 2.0
                val v = getVariation(nameA + dobA + nameB + dobB)
                clamp((base + v).roundToInt(), 42, 99)
            }
            CalculationMode.DATE -> {
                val base = H[lp1][lp2].toDouble()
                val v = getVariation("D$dobA$dobB$nameA$nameB")
                clamp((base + v).roundToInt(), 42, 99)
            }
            CalculationMode.NAME -> {
                val base = H[nn1][nn2].toDouble()
                val v = getVariation("N$nameA$nameB$dobA$dobB")
                clamp((base + v).roundToInt(), 42, 99)
            }
        }

        val tier = when {
            score >= 85 -> CompatibilityTier.EXCELLENT
            score >= 70 -> CompatibilityTier.GOOD
            score >= 55 -> CompatibilityTier.AVERAGE
            else -> CompatibilityTier.GROWING
        }

        val wishOpening = when (tier) {
            CompatibilityTier.EXCELLENT -> "$nameA aur $nameB, aapka bandhan taaron jaisa chamke."
            CompatibilityTier.GOOD -> "$nameA aur $nameB, thodi samajh se ye rishta aur khoobsurat banega."
            CompatibilityTier.AVERAGE -> "$nameA aur $nameB, dhairya aur pyaar se sab thik hoga."
            CompatibilityTier.GROWING -> "$nameA aur $nameB, har mazboost rishta yahin se shuru hota hai."
        }
        val wishText = "$wishOpening Yaad rahein — ank sirf ek dishaa dikhate hain, asli kahani trust, communication aur pyaar se likhi jaati hai. Hamari shubhkamnayein hamesha aapke saath hain. 🙏✨"

        return MatchResult(
            mode = mode,
            nameA = nameA,
            dobA = dobA,
            nameB = nameB,
            dobB = dobB,
            score = score,
            tier = tier,
            wishText = wishText
        )
    }
}
