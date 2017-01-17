import com.elevenware.jenkins.pipelines.PipelineContext

def call(String pipelineName, Closure body) {

    PipelineContext ctx = new PipelineContext(pipelineName)
    body.delegate = ctx
    body.resolveStrategy = Closure.DELEGATE_ONLY
    body()

    echo "Pipeline type: ${ctx.pipeline}"
    echo "App Name: ${ctx.appName}"
    echo "Role: ${ctx.role}"
    echo "Platform: ${ctx.platform}"
    echo  "Cookbook: ${ctx.cookbookName}"

}