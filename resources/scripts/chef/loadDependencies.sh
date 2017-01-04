#!/bin/bash

echo "Loading chef dependencies"
export PATH=$PATH:/var/lib/jenkins/.rvm/rubies/ruby-2.2.6/bin
ruby -v

gem install bundler

bundle install --path "~/.gem"