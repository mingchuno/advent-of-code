package com.github.mingchuno.aoc.y2024

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.*

object Day9 : Problem<Long> {

    override fun computePart1(inputFile: String): Long {
        val input = inputFile.readFileFromResource().first()
        val disk = buildDisk(input)
        var lPtr = 0
        var rPtr = disk.size - 1
        while (true) {
            // advance left ptr to next free block
            while (disk[lPtr] != null) {
                lPtr++
            }
            // advance right ptr to next data block
            while (disk[rPtr] == null) {
                rPtr--
            }
            if (lPtr > rPtr) {
                break
            }
            disk[lPtr] = disk[rPtr]
            disk[rPtr] = null
        }
        return disk.checksum()
    }

    private fun buildDisk(input: String): MutableList<Int?> {
        val disk = mutableListOf<Int?>()
        var id = 0
        input.forEachIndexed { index, c ->
            val blockSize = c.digitToInt()
            if (index % 2 == 0) {
                disk.addAll(List(blockSize) { id })
                id++
            } else {
                disk.addAll(List(blockSize) { null })
            }
        }
        return disk
    }

    private fun debugDisk(disk: List<Int?>): String =
        disk.joinToString(separator = "") { i -> i?.toString() ?: "." }

    override fun computePart2(inputFile: String): Long {
        val input = inputFile.readFileFromResource().first()
        val c = ComputePart2(input)
        c.debug()
        c.defrag()
        return c.checksum()
    }

    private class ComputePart2(input: String) {
        private val uncompressedDisk: MutableList<Int?> = mutableListOf()
        private val freeSpaceLookup: MutableList<Block> = mutableListOf()
        private val dataBlockLookup: MutableList<Block> = mutableListOf()

        private data class Block(var start: Int, val end: Int) {
            val size
                get() = end - start
        }

        init {
            // build supporting data structure
            var id = 0
            input.forEachIndexed { index, c ->
                val blockSize = c.digitToInt()
                val currentSize = uncompressedDisk.size
                if (blockSize > 0) {
                    if (index % 2 == 0) {
                        dataBlockLookup.add(Block(currentSize, currentSize + blockSize))
                        uncompressedDisk.addAll(List(blockSize) { id })
                        id++
                    } else {
                        freeSpaceLookup.add(Block(currentSize, currentSize + blockSize))
                        uncompressedDisk.addAll(List(blockSize) { null })
                    }
                }
            }
        }

        fun debug() {
            // Add println if needed
        }

        fun defrag() {
            while (dataBlockLookup.isNotEmpty()) {
                // Process block by block
                val id = dataBlockLookup.size - 1
                val dataBlock = dataBlockLookup.removeLast()
                val suitableFreeBlock = findSuitableFreeBlock(dataBlock)
                if (suitableFreeBlock != null) {
                    // Do the swap
                    for (i in
                        suitableFreeBlock.start until suitableFreeBlock.start + dataBlock.size) {
                        uncompressedDisk[i] = id
                    }
                    for (j in dataBlock.start until dataBlock.end) {
                        uncompressedDisk[j] = null
                    }
                    // update freeSpaceLookup
                    suitableFreeBlock.start += dataBlock.size
                }
                debug()
            }
        }

        private fun findSuitableFreeBlock(datablock: Block): Block? {
            return freeSpaceLookup.find { it.size >= datablock.size && it.start < datablock.start }
        }

        fun checksum(): Long = uncompressedDisk.checksum()
    }
}

typealias Disk = MutableList<Int?>

private fun Disk.checksum(): Long =
    this.mapIndexed { index, i -> i?.let { (it.toLong() * index.toLong()) } ?: 0L }.sum()
