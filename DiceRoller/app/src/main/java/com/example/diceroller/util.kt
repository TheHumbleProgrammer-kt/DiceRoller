package com.example.diceroller

data class Result(var intString: String = "")

/**
 * Takes the last character of the Result and switches it with the provided resText
 **/
fun Result.updateResult(resText: String) {

    val resList = intString.toCharArray()

    resList[resList.size - 1] = resText.single()

    intString = String(resList)
}