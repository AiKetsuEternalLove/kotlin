// !WITH_NEW_INFERENCE
// !CHECK_TYPE
// !DIAGNOSTICS: -UNUSED_PARAMETER,-UNUSED_VARIABLE

class A<F> {
    fun <E : F> foo1(x: E) = x
    fun <E : F?> foo2(x: E) = x

    fun <Z : F, W : Z?> bar(x: F, y: F?, z: Z, w: W) {
        foo1<F>(x)

        val x1 = foo1(x)
        x1.checkType { _<F>() }

        foo2<F>(x)

        val x2 = foo2(x)
        x2.checkType { _<F>() }

        <!INAPPLICABLE_CANDIDATE!>foo1<!><F?>(y)
        <!INAPPLICABLE_CANDIDATE!>foo1<!>(y)
        foo2<F?>(y)

        val x3 = foo2(y)
        x3.checkType { _<F?>() }

        <!INAPPLICABLE_CANDIDATE!>foo1<!><F>(y)
        <!INAPPLICABLE_CANDIDATE!>foo2<!><F>(y)

        foo1<Z>(z)

        val x4 = foo1(z)
        x4.checkType { _<Z>() }

        foo2<Z>(z)

        val x5 = foo2(z)
        x4.checkType { _<Z>() }

        <!INAPPLICABLE_CANDIDATE!>foo1<!><W>(w)
        foo1(w)
        foo2<W>(w)

        val x6 = foo2(w)
        x6.checkType { _<W>() }

        <!INAPPLICABLE_CANDIDATE!>foo1<!><W>(w)
    }
}
