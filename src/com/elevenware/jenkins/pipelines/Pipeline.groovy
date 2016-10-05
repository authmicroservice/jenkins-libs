package com.elevenware.jenkins.pipelines

class Pipeline implements Serializable {

    void generate() {

        new SimpleStage().create('test app', 'Integration')
        new SimpleStage().create('test app', 'QA')
        new SimpleStage().create('test app', 'Staging')
        new SimpleStage().create('test app', 'Production')

    }

}
