package com.github.keelar.exprk.internal

import java.math.BigDecimal

sealed class Expr {

    abstract fun <R> accept(visitor: ExprVisitor<R>): R

}

class AssignExpr(val name: Token,
                          val value: Expr) : Expr() {

    override fun <R> accept(visitor: ExprVisitor<R>): R {
        return visitor.visitAssignExpr(this)
    }

}

class LogicalExpr(val left: Expr,
                           val operator: Token,
                           val right: Expr) : Expr() {

    override fun <R> accept(visitor: ExprVisitor<R>): R {
        return visitor.visitLogicalExpr(this)
    }

}

class BinaryExpr(val left: Expr,
                          val operator: Token,
                          val right: Expr) : Expr() {

    override fun <R> accept(visitor: ExprVisitor<R>): R {
        return visitor.visitBinaryExpr(this)
    }

}

class UnaryExpr(val operator: Token,
                         val right: Expr) : Expr() {

    override fun <R> accept(visitor: ExprVisitor<R>): R {
        return visitor.visitUnaryExpr(this)
    }

}

class CallExpr(val name: String,
                        val arguments: List<Expr>) : Expr() {

    override fun <R> accept(visitor: ExprVisitor<R>): R {
        return visitor.visitCallExpr(this)
    }

}

class LiteralExpr(val value: BigDecimal) : Expr() {

    override fun <R> accept(visitor: ExprVisitor<R>): R {
        return visitor.visitLiteralExpr(this)
    }

}

class VariableExpr(val name: Token) : Expr() {

    override fun <R> accept(visitor: ExprVisitor<R>): R {
        return visitor.visitVariableExpr(this)
    }

}

class GroupingExpr(val expression: Expr) : Expr() {

    override fun <R> accept(visitor: ExprVisitor<R>): R {
        return visitor.visitGroupingExpr(this)
    }

}

interface ExprVisitor<out R> {

    fun visitAssignExpr(expr: AssignExpr): R

    fun visitLogicalExpr(expr: LogicalExpr): R

    fun visitBinaryExpr(expr: BinaryExpr): R

    fun visitUnaryExpr(expr: UnaryExpr): R

    fun visitCallExpr(expr: CallExpr): R

    fun visitLiteralExpr(expr: LiteralExpr): R

    fun visitVariableExpr(expr: VariableExpr): R

    fun visitGroupingExpr(expr: GroupingExpr): R

}