import com.elevenware.jenkins.pipelines.PipelineContext
import com.elevenware.jenkins.pipelines.util.PipelineRegistry
import com.elevenware.jenkins.pipelines.util.PlatformRegistry

def call(String pipelineName, Closure body) {

    node {
        gitCommit = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()
        //shortCommit = gitCommit.take(6)
        echo "SHORT $gitCommit"
    }


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