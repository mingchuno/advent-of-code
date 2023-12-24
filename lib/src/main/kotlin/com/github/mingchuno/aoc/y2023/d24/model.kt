package com.github.mingchuno.aoc.y2023.d24

data class Point(val x: Double, val y: Double)

data class Position(val x: Double, val y: Double, val z: Double)

data class VelocityVec(val vx: Int, val vy: Int, val vz: Int)

data class Hailstones(val p0: Position, val v: VelocityVec) {
    constructor(
        x0: Long,
        y0: Long,
        z0: Long,
        vx: Int,
        vy: Int,
        vz: Int
    ) : this(Position(x0.toDouble(), y0.toDouble(), z0.toDouble()), VelocityVec(vx, vy, vz))

    fun intersectionPoint(that: Hailstones): Point? {
        val slopeDelta = this.v.vy * that.v.vx - this.v.vx * that.v.vy
        if (slopeDelta == 0) {
            return null
        }
        val det = (this.v.vx * that.v.vy - this.v.vy * that.v.vx).toDouble()
        val x =
            ((this.p0.y * this.v.vx * that.v.vx) - (this.v.vy * this.p0.x * that.v.vx) +
                    (this.v.vx * that.v.vy * that.p0.x) - (that.p0.y * this.v.vx * that.v.vx))
                .toDouble() / det

        val y =
            ((this.p0.y * this.v.vx * that.v.vy) - (this.v.vy * this.p0.x * that.v.vy) +
                    (this.v.vy * that.v.vy * that.p0.x) - (that.p0.y * this.v.vy * that.v.vx))
                .toDouble() / det
        return Point(x, y)
    }
}

fun willIntersect(a: Hailstones, b: Hailstones, start: Long, end: Long): Boolean {
    val point = a.intersectionPoint(b) ?: return false
    val (x, y) = point
    val withInArea = start <= x && x <= end && start <= y && y <= end
    if (!withInArea) return false
    val aInFuture = ((x - a.p0.x) / a.v.vx) > 0
    val bInFuture = ((x - b.p0.x) / b.v.vx) > 0
    return aInFuture && bInFuture
}
