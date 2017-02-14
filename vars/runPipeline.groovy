import com.elevenware.jenkins.pipelines.PipelineContext
import com.elevenware.jenkins.pipelines.util.PipelineRegistry
import com.elevenware.jenkins.pipelines.util.PlatformRegistry

def call(String pipelineName, Closure body) {

    def pipelineDef = PipelineRegistry.instance.create(pipelineName)

    PipelineContext ctx = new PipelineContext()
    body.delegate = ctx
    body.resolveStrategy = Closure.DELEGATE_ONLY
    body()

    def platformName = ctx.platform
    def platform = PlatformRegistry.instance.create(platformName)
    ctx.setPlatformImplementation(platform)

    pipelineDef.run(ctx)

}