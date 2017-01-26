# A sample Guardfile
# More info at https://github.com/guard/guard#readme

guard :gradle do
  watch(%r{^src/(.+)\.*$}) { |m| m[1].split('.')[0].split('/')[-1] }
  watch(%r{^test/(.+)\.*$}) { |m| m[1].split('.')[0].split('/')[-1] }
  watch('build.gradle')
end
