#!/bin/bash

echo "Loading chef dependencies"
export PATH=$PATH:/var/lib/jenkins/.rvm/gems/ruby-1.9.3-p551/bin
ruby -v

gem install bundler

bundle install --path "~/.gem"