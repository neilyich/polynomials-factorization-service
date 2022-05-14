package neilyich.polynomialfactorizationservice.utils

import neilyich.field.Field
import neilyich.field.element.FieldElement

class FieldCalculator<Element: FieldElement>(
    private val field: Field<Element>,
    private var currentValue: Element
) {
    fun take(e: Element): FieldCalculator<Element> {
        return FieldCalculator(field, e)
    }

    fun get(): Element = currentValue

    fun mult(e: Element): FieldCalculator<Element> {
        currentValue = field.mult(currentValue, e)
        return this
    }

    fun div(e: Element): FieldCalculator<Element> {
        currentValue = field.div(currentValue, e)
        return this
    }

    fun add(e: Element): FieldCalculator<Element> {
        currentValue = field.add(currentValue, e)
        return this
    }

    fun sub(e: Element): FieldCalculator<Element> {
        currentValue = field.sub(currentValue, e)
        return this
    }

    fun minus(): FieldCalculator<Element> {
        currentValue = field.inverseAdd(currentValue)
        return this
    }

    fun inverseMult(): FieldCalculator<Element> {
        currentValue = field.inverseMult(currentValue)
        return this
    }

    fun inverseAdd(): FieldCalculator<Element> {
        return minus()
    }
}