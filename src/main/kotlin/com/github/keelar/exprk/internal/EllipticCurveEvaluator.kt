package com.github.keelar.exprk.internal

import com.github.keelar.exprk.EllipticCurveExpressionValue
import com.github.keelar.exprk.ExpressionException
import com.github.keelar.exprk.IntEllipticCurveExpressionValue
import com.github.keelar.exprk.PointEllipticCurveExpressionValue
import neilyich.polynomialfactorizationservice.ellipticcurve.EllipticCurve
import neilyich.util.pow

class EllipticCurveEvaluator(
    private val curve: EllipticCurve
): ExprVisitor<EllipticCurveExpressionValue> {
    private val variables: LinkedHashMap<String, EllipticCurveExpressionValue> = linkedMapOf()
    private val functions: MutableMap<String, Function> = mutableMapOf()

    fun define(name: String, value: EllipticCurveExpressionValue) {
        variables += name to value
    }

    fun define(name: String, expr: Expr): EllipticCurveEvaluator {
        define(name.lowercase(), eval(expr))

        return this
    }

    fun addFunction(name: String, function: Function): EllipticCurveEvaluator {
        functions += name.lowercase() to function

        return this
    }

    override fun visitAssignExpr(expr: AssignExpr): EllipticCurveExpressionValue {
        val value = eval(expr.value)

        define(expr.name.lexeme, value)

        return value
    }

    override fun visitLogicalExpr(expr: LogicalExpr): EllipticCurveExpressionValue {
        throw ExpressionException(
            "Invalid logical operator '${expr.operator.lexeme}'")
    }

    override fun visitBinaryExpr(expr: BinaryExpr): EllipticCurveExpressionValue {
        val left = eval(expr.left)
        val right = eval(expr.right)

        if (left is IntEllipticCurveExpressionValue) {
            if (right is IntEllipticCurveExpressionValue) {
                return IntEllipticCurveExpressionValue(when (expr.operator.type) {
                    TokenType.PLUS -> left.value + right.value
                    TokenType.MINUS -> left.value - right.value
                    TokenType.STAR -> left.value * right.value
                    TokenType.SLASH -> left.value / right.value
                    TokenType.MODULO -> left.value % right.value
                    TokenType.EXPONENT -> left.value.pow(right.value)
                    TokenType.EQUAL_EQUAL -> if (left.value == right.value) 1 else 0
                    TokenType.NOT_EQUAL -> if (left.value != right.value) 1 else 0
                    TokenType.GREATER -> if (left.value > right.value) 1 else 0
                    TokenType.GREATER_EQUAL -> if (left.value >= right.value) 1 else 0
                    TokenType.LESS -> if (left.value < right.value) 1 else 0
                    TokenType.LESS_EQUAL -> if (left.value <= right.value) 1 else 0
                    else -> throw ExpressionException(
                        "Invalid binary operator '${expr.operator.lexeme}'")
                })
            } else {
                right as PointEllipticCurveExpressionValue
                return PointEllipticCurveExpressionValue(when (expr.operator.type) {
                    TokenType.STAR -> curve.times(right.p, left.value)
                    else -> throw ExpressionException(
                        "Invalid binary operator '${expr.operator.lexeme}'")
                })
            }
        } else {
            left as PointEllipticCurveExpressionValue
            return if (right is IntEllipticCurveExpressionValue) {
                PointEllipticCurveExpressionValue(when (expr.operator.type) {
                    TokenType.STAR -> curve.times(left.p, right.value)
                    else -> throw ExpressionException(
                        "Invalid binary operator '${expr.operator.lexeme}'")
                })
            } else {
                right as PointEllipticCurveExpressionValue
                PointEllipticCurveExpressionValue(when (expr.operator.type) {
                    TokenType.PLUS -> curve.add(left.p, right.p)
                    TokenType.MINUS -> curve.sub(left.p, right.p)
                    else -> throw ExpressionException(
                        "Invalid binary operator '${expr.operator.lexeme}'")
                })
            }
        }
    }

    override fun visitUnaryExpr(expr: UnaryExpr): EllipticCurveExpressionValue {
        val right = eval(expr.right)

        return when (expr.operator.type) {
            TokenType.MINUS -> {
                when (right) {
                    is IntEllipticCurveExpressionValue -> IntEllipticCurveExpressionValue(-right.value)
                    is PointEllipticCurveExpressionValue -> PointEllipticCurveExpressionValue(-right.p)
                    else -> throw ExpressionException("Unknown value type")
                }
            }
            else -> throw ExpressionException("Invalid unary operator")
        }
    }

    override fun visitCallExpr(expr: CallExpr): EllipticCurveExpressionValue {
        val name = expr.name
        val function = functions[name.lowercase()] ?:
        throw ExpressionException("Undefined function '$name'")

        return IntEllipticCurveExpressionValue(
            function.call(
                expr.arguments.map {
                    (eval(it) as IntEllipticCurveExpressionValue).value.toBigDecimal()
                }
            ).intValueExact()
        )
    }

    override fun visitLiteralExpr(expr: LiteralExpr): EllipticCurveExpressionValue {
        return IntEllipticCurveExpressionValue(expr.value.intValueExact())
    }

    override fun visitVariableExpr(expr: VariableExpr): EllipticCurveExpressionValue {
        val name = expr.name.lexeme

        return variables[name.lowercase()] ?:
        throw ExpressionException("Undefined variable '$name'")    }

    override fun visitGroupingExpr(expr: GroupingExpr): EllipticCurveExpressionValue {
        return eval(expr.expression)
    }


    fun eval(expr: Expr): EllipticCurveExpressionValue {
        return expr.accept(this)
    }
}