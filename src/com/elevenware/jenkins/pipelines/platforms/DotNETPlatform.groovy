package com.elevenware.jenkins.pipelines.platforms

import com.elevenware.jenkins.pipelines.PipelineContext
import com.elevenware.jenkins.pipelines.helpers.ChefSteps

def build(PipelineContext context) {
    echo "building ${context.appName}"
    dir(context.appName) {
        sh "dotnet restore"
        sh "dotnet publish --output ${workspace}/app --configuration Release"
        sh "dotnet pack --output ${workspace}/package --configuration Release" 
    }
}

def test(PipelineContext context) {
    echo "add tests for ${context.appName}"
}

def publish(PipelineContext context) {
    echo "Publish ${context.appName} to Nexus: ${workspace}/app"
    archiveArtifacts artifacts: 'app/**'
    nexusPublisher nexusInstanceId: 'ThomasCookNexus',
	    	   nexusRepositoryId: "${context.appName}", 
	           packages: [
			[$class: 'MavenPackage', 
			 mavenAssetList: [
				       [classifier: '', 
					extension: '', 
					filePath: "${context.appName}.zip"]
			       ],
			       mavenCoordinate: [
				       artifactId: "${context.appName}", 
				       groupId: "com.thomascook.${context.appName}", 
				       packaging: 'zip', 
				       version: context.appVersion]
			      ]
		  ] // packages
}

def deploy(PipelineContext context, String env) {
    echo "deploy ${context.appName}"
    echo "Deploying ${appName} to $env"
    def chefUri = context.chefRepoUri
    def credentials = context.chefRepoCredentials
    def cookbookDir = context.cookbookDir
    git url: chefUri, credentialsId: credentials
    ChefSteps chefSteps = context.chefSteps
    chefSteps.installChefDependencies(context)
    chefSteps.environmentPin(context, env)
    chefSteps.runChefClient(context, env)
}

def getVersion() {
    "1.0.2"
}
