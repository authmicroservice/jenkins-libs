def call(Closure body) {
    node {
        body.setDelegate({
            def foo = { str -> println "PASSED $str"}
        })
        body()
    }
}