package com.felisreader.core.util

import com.felisreader.core.domain.model.Sentence

object AppUtil {
    /**
     * Function that finds the n closest sentences to the input sentence from the list of sentences using levenshtein distance
     *
     * @param input The input sentence
     * @param sentences The list of sentences
     * @param n The number of closest sentences to find
     * @return The list of closest sentences
     */
    fun findClosestSentences(input: String, sentences: MutableList<String>, n: Int): List<Sentence> {
        val closestSentences: MutableList<Sentence> = mutableListOf()
        val inputWords: List<String> = input.split(" ")

        // Add the input sentence to the list of closest sentences if it is in the list of sentences
        if (inputWords.size == 1) {
            closestSentences.addAll(
                sentences.filter {
                    it.contains(input)
                }.map {
                    Sentence(it, 0)
                }
            )
            closestSentences.forEach {
                sentences.remove(it.sentence)
            }
        }


        // Find the n closest sentences
        for (i in 0 until n) {
            var closestSentence = ""
            var closestSentenceScore: Int = Int.MAX_VALUE

            // Find the closest sentence to the input
            for (sentence in sentences) {
                val sentenceWords: List<String> = sentence.split(" ")
                var sentenceScore = 0

                // Find the closest word in the sentence for each word in the input
                for (inputWord in inputWords) {
                    var closestWordScore: Int = Int.MAX_VALUE

                    // Find the closest word in the sentence for the current word in the input
                    for (sentenceWord in sentenceWords) {
                        closestWordScore = minOf(closestWordScore, levenshteinDistance(inputWord, sentenceWord))
                    }

                    sentenceScore += closestWordScore
                }

                // Update the closest sentence if the current sentence is closer
                if (sentenceScore < closestSentenceScore) {
                    closestSentence = sentence
                    closestSentenceScore = sentenceScore
                }
            }

            // Add the closest sentence to the list of closest sentences and remove it from the list of sentences
            closestSentences.add(Sentence(closestSentence, closestSentenceScore))
            sentences.remove(closestSentence)
        }

        return closestSentences.sortedBy { it.score }.take(n)
    }

    /**
     * Function that calculates the levenshtein distance between two strings
     * @param s1 The first string
     * @param s2 The second string
     * @return The levenshtein distance between the two strings
     */
    fun levenshteinDistance(s1: String, s2: String): Int {
        val s1Length: Int = s1.length
        val s2Length: Int = s2.length

        val matrix: Array<IntArray> = Array(s1Length + 1) { IntArray(s2Length + 1) }

        for (i in 0..s1Length) {
            matrix[i][0] = i
        }

        for (j in 0..s2Length) {
            matrix[0][j] = j
        }

        for (i in 1..s1Length) {
            for (j in 1..s2Length) {
                if (s1[i - 1] == s2[j - 1]) {
                    matrix[i][j] = matrix[i - 1][j - 1]
                } else {
                    matrix[i][j] = minOf(
                        matrix[i - 1][j - 1],
                        minOf(matrix[i - 1][j], matrix[i][j - 1])
                    ) + 1
                }
            }
        }

        return matrix[s1Length][s2Length]
    }
}