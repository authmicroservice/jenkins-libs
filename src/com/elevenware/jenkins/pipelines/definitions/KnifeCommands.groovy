package com.elevenware.jenkins.pipelines.definitions

import com.elevenware.jenkins.pipelines.PipelineContext

/**
 * KnifeCommands
 *
 * Wraps commonly used knife commands
 */
class KnifeCommands {

    static String checkEnvExists(String environment) {
        return "bundle exec knife environment show ${environment} --attribute name > /dev/null"
    }

    static String pinEnvironment(PipelineContext ctx, String targetEnvironment) {
        StringBuilder pinCmdBuilder = new StringBuilder().append("bundle exec knife exec -E ")
                .append("\"env = environments.find('name:${targetEnvironment}').first;")
                .append("env.default_attributes['apps'] ||= {};")
                .append("env.default_attributes['apps']['${ctx.appName}'] = '${ctx.appSpec}';")
                .append("env.save;\"")
        return pinCmdBuilder.toString()
    }

}
