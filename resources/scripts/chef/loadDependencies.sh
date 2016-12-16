#!/bin/bash

echo "Loading chef dependencies"
export PATH=$PATH:/home/vagrant/.rvm/rubies/ruby-2.2.4/bin/
echo "PATH=$PATH"
ruby -v

bundle install --path "~/.gem"