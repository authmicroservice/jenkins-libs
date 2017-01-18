import com.elevenware.jenkins.pipelines.PipelineContext
import com.elevenware.jenkins.pipelines.definitions.PipelineRegistry

def call(String pipelineName, Closure body) {

    def pipelineDef = PipelineRegistry.instance.create(pipelineName)

    PipelineContext ctx = new PipelineContext(pipelineName)
    body.delegate = ctx
    body.resolveStrategy = Closure.DELEGATE_ONLY
    body()

    pipelineDef.run(ctx)

    echo "Pipeline type: ${ctx.pipeline}"
    echo "App Name: ${ctx.appName}"
    echo "Role: ${ctx.role}"
    echo "Platform: ${ctx.platform}"
    echo  "Cookbook: ${ctx.cookbookName}"

}