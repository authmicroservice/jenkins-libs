#!/bin/bash

echo "Loading chef dependencies"
export PATH=$PATH:/var/lib/jenkins/.rvm/rubies/ruby-2.2.6/bin/
whoami
echo "PATH=$PATH"
ruby -v

bundle install --path "~/.gem"