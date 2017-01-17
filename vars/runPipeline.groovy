import com.elevenware.jenkins.pipelines.PipelineContext

def call(String pipelineName, Closure body) {

    PipelineContext context = new PipelineContext()
    body.delegate = context
    body.resolveStrategy = Closure.DELEGATE_ONLY
    body()

    echo "Pipeline type: ${ctx.pipeline}"
    echo "App Name: ${ctx.appName}"
    echo "Role: ${ctx.role}"
    echo "Platform: ${ctx.platform}"
    echo  "Cookbook: ${ctx.cookbookName}"

}