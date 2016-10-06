import com.elevenware.jenkins.pipelines.FooDelegate

def call(Closure body) {

        body.setDelegate(new FooDelegate())
        body.setResolveStrategy(Closure.DELEGATE_FIRST)
        body()
}

static void main(args) {
    call {
        foo('bar')
    }
}