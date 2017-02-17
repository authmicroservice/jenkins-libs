package com.elevenware.jenkins.pipelines.definitions

/**
 * ShellSnippets
 *
 * Wraps short, one-line shell scripts and also makes them
 * usable anywhere a String might be used
 */
enum ShellSnippets implements CharSequence {

    GEM_INSTALL('bundle install --path "~/.gem"')

    ShellSnippets(String code) {
        this.code = code
    }

    String code

    @Override
    int length() {
        return code.length()
    }

    @Override
    char charAt(int index) {
        return code.charAt(index)
    }

    @Override
    CharSequence subSequence(int start, int end) {
        return code.subSequence(start, end)
    }

    @Override
    String toString() {
        return code
    }
}
