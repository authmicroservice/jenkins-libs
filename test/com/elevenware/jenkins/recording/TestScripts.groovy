package com.elevenware.jenkins.recording

def simplest() {
    echo "Hello, world!"
}

def passParam(String string) {
    echo string
}

def inStage() {
    stage('stage1') {
        echo "Hello, world!"
    }
}

def inNode() {
    node {
        echo "Hello, world!"
    }
}

def stageInNode() {
    node {
        stage('stage1') {
            echo "Hello, world!"
        }
    }
}

def mockResponse() {
    String string = libraryResource 'some_path.txt'
    echo string
}


def simple(String env, Map config) {
    def role = config.role
    String rootDeployScript = libraryResource 'scripts/tracer.sh'
    echo "Deploying ${role} to ${env}"
    println "SCRIPR $rootDeployScript"
    def deployScript = rootDeployScript.replace('${ENVIRONMENT}', env)
    sh "${deployScript}"
}