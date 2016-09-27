def call(Closure body) {
    node('windows') {
        body()
    }
}