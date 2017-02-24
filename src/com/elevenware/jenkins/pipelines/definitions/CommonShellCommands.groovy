package com.elevenware.jenkins.pipelines.definitions

enum CommonShellCommands {

    GEM_INSTALL('bundle install --path "~/.gem"')

    CommonShellCommands(String code) {
        this.code = code
    }

    String code

    @Override
    String toString() {
        return code
    }

    String format(args) {
        return String.format(code, args)
    }

}
