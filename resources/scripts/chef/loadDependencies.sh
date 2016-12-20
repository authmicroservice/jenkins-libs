#!/bin/bash

echo "Loading chef dependencies"
whoami
echo "PATH=$PATH"
ruby -v

bundle install --path "~/.gem"