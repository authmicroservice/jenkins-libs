import com.elevenware.jenkins.pipelines.PipelineContext
import com.elevenware.jenkins.pipelines.definitions.PipelineRegistry

def call(String pipelineName, Closure body) {

    echo "SCM $scm"

    def pipelineDef = PipelineRegistry.instance.create(pipelineName)

    PipelineContext ctx = new PipelineContext(pipelineName)
    body.delegate = ctx
    body.resolveStrategy = Closure.DELEGATE_ONLY
    body()

    pipelineDef.run(ctx)

}