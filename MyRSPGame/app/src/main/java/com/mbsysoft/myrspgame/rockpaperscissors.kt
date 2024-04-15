package com.mbsysoft.myrspgame

fun main() {
    var computerChoice = ""
    var playerChoice = ""

    println("rock, paper, Scissors? Enter your choice!")
    playerChoice = readln()

    val randomNum = (1..3).random()

    computerChoice = when (randomNum) {
        1 -> {
            "rock"
        }
        2 -> {
            "paper"
        }
        else -> {
            "Scissors"
        }
    }
    println(computerChoice)

    val winner = when{
        playerChoice == computerChoice -> "Tie"
        playerChoice == "Rock"&& computerChoice == "Scissor" -> "Player"
        playerChoice == "paper"&& computerChoice == "Rock" -> "Player"
        playerChoice == "Scissor"&& computerChoice == "paper" -> "Player"
        else -> "computer"
    }

    if (winner == "Tie") {
        println("Tie")
    } else {
        println("$winner won!")
    }
}