#!/bin/bash

echo "Loading chef dependencies"
echo "PATH=$PATH"
ruby -v

bundle install --path "~/.gem"