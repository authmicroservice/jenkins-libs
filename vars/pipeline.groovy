def call(Closure body) {
    node {
        echo "IN A PIPELINE"
        body()
    }
}