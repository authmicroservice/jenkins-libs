package com.elevenware.jenkins.pipelines.definitions

import com.elevenware.jenkins.pipelines.PipelineContext

/**
 * KnifeCommands
 *
 * Wraps commonly used knife commands
 */
class KnifeCommands {

    static String checkEnvExists(String environment) {
        return "chef exec bundle exec knife environment show ${environment} --attribute name > /dev/null"
    }

    static String pinEnvironment(PipelineContext ctx, String targetEnvironment) {
        StringBuilder pinCmdBuilder = new StringBuilder().append("chef exec bundle exec knife exec -E ")
                .append("\"env = environments.find('name:${targetEnvironment}').first;")
                .append("env.default_attributes['apps'] ||= {};")
                .append("env.default_attributes['apps']['${ctx.appName}'] = '${ctx.appSpec}';")
                .append("env.save;\"")
        return pinCmdBuilder.toString()
    }

    static String lookUpNodes(String role, String targetEnvironment) {
        new StringBuilder('chef exec bundle exec knife search "').append("role:${role} AND chef_environment:${targetEnvironment}")
                        .append('"\\\n --attribute ipaddress --format json').toString()
    }

    static String runChefClient(String role, String targetEnvironment) {
        new StringBuilder('chef exec bundle exec knife ssh "')
            .append("role:${role} AND chef_environment:${targetEnvironment}").append('"\\\n')
            .append("                        --attribute ipaddress \\\n")
            .append("                        --no-host-key-verify \\\n")
            .append("                        --ssh-user jenkins \\\n")
            .append("                        -i /home/jenkins/.ssh/cloud-user \\\n")
            .append("                       'sudo chef-client'").toString()
    }

}
