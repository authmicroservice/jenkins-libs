package com.elevenware.jenkins.pipelines.definitions

/**
 * ShellSnippets
 *
 * Wraps short, one-line shell scripts
 */
enum ShellSnippets {

    GEM_INSTALL('bundle install --path "~/.gem"'),
    KNIFE_CHECK_ENV('bundle exec knife %s')

    ShellSnippets(String code) {
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
