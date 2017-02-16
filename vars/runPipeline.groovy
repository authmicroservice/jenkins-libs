import com.elevenware.jenkins.pipelines.PipelineContext
import com.elevenware.jenkins.pipelines.util.PipelineRegistry
import com.elevenware.jenkins.pipelines.util.PlatformRegistry
import com.elevenware.jenkins.pipelines.definitions.ChefSteps

def call(String pipelineName, Closure body) {

    node {
//        gitCommit = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()
//        shortCommit = gitCommit.take(6)
    }

    def pipelineDef = PipelineRegistry.instance.create(pipelineName)

    PipelineContext ctx = new PipelineContext()
    body.delegate = ctx
    body.resolveStrategy = Closure.DELEGATE_ONLY
    body()

    ctx.gitCommit = gitCommit
    ctx.shortCommit = shortCommit

    def platformName = ctx.platform
    def platform = PlatformRegistry.instance.create(platformName)
    ctx.setPlatformImplementation(platform)
    ctx.chefSteps = new ChefSteps()

    pipelineDef.run(ctx)

}