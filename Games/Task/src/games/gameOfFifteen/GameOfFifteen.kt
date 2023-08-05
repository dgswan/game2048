package games.gameOfFifteen

import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game
import games.game2048.moveValuesInRowOrColumn

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
    object : Game {
        private val board = createGameBoard<Int?>(4)

        override fun initialize() {
            val permutation = initializer.initialPermutation
            with (board) {
                getAllCells().zip(permutation)
                    .forEach { (cell, value) -> set(cell, value) }
            }
        }

        override fun canMove(): Boolean = true

        override fun hasWon(): Boolean {
            return with(board) {
                getAllCells().mapNotNull { get(it) }.zipWithNext().all { (prev, curr) -> prev < curr }
            }
        }

        override fun processMove(direction: Direction) {
            with(board) {
                val newDirection = when (direction) {
                    Direction.RIGHT -> Direction.LEFT
                    Direction.DOWN -> Direction.UP
                    Direction.LEFT -> Direction.RIGHT
                    Direction.UP -> Direction.DOWN
                }
                val emptyCell = find { it == null }
                emptyCell?.getNeighbour(newDirection)
                    ?.let {
                        val movedValue = get(it)
                        set(it, null)
                        set(emptyCell, movedValue)
                    }
            }
        }

        override fun get(i: Int, j: Int): Int? {
            return with (board) {
                get(getCell(i, j))
            }
        }

    }

