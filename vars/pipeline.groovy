import com.elevenware.jenkins.pipelines.PipelineBuilder
import com.elevenware.jenkins.pipelines.Pipeline
import com.elevenware.jenkins.pipelines.PipelineBuilderDelegate

def call(Closure body) {
    PipelineBuilder builder = new PipelineBuilder()
    body.setDelegate(new PipelineBuilderDelegate(builder))
    body.setResolveStrategy(Closure.DELEGATE_FIRST)
    body()

    Pipeline pipeline = builder.pipeline
    pipeline.generate()

}