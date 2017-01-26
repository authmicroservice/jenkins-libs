# -*- encoding: utf-8 -*-
# stub: hitimes 1.2.4 ruby lib
# stub: ext/hitimes/c/extconf.rb

Gem::Specification.new do |s|
  s.name = "hitimes".freeze
  s.version = "1.2.4"

  s.required_rubygems_version = Gem::Requirement.new(">= 0".freeze) if s.respond_to? :required_rubygems_version=
  s.require_paths = ["lib".freeze]
  s.authors = ["Jeremy Hinegardner".freeze]
  s.date = "2016-05-03"
  s.description = "A fast, high resolution timer library for recording peformance metrics. * (http://github.com/copiousfreetime/hitimes) * (http://github.com.org/copiousfreetime/hitimes) * email jeremy at copiousfreetime dot org * `git clone url git://github.com/copiousfreetime/hitimes.git`".freeze
  s.email = "jeremy@copiousfreetime.org".freeze
  s.extensions = ["ext/hitimes/c/extconf.rb".freeze]
  s.extra_rdoc_files = ["CONTRIBUTING.md".freeze, "HISTORY.md".freeze, "Manifest.txt".freeze, "README.md".freeze]
  s.files = ["CONTRIBUTING.md".freeze, "HISTORY.md".freeze, "Manifest.txt".freeze, "README.md".freeze, "ext/hitimes/c/extconf.rb".freeze]
  s.homepage = "http://github.com/copiousfreetime/hitimes".freeze
  s.licenses = ["ISC".freeze]
  s.rdoc_options = ["--main".freeze, "README.md".freeze, "--markup".freeze, "tomdoc".freeze]
  s.required_ruby_version = Gem::Requirement.new(">= 1.9.3".freeze)
  s.rubygems_version = "2.6.10".freeze
  s.summary = "A fast, high resolution timer library for recording peformance metrics.".freeze

  s.installed_by_version = "2.6.10" if s.respond_to? :installed_by_version

  if s.respond_to? :specification_version then
    s.specification_version = 4

    if Gem::Version.new(Gem::VERSION) >= Gem::Version.new('1.2.0') then
      s.add_development_dependency(%q<rake>.freeze, ["~> 10.4"])
      s.add_development_dependency(%q<minitest>.freeze, ["~> 5.5"])
      s.add_development_dependency(%q<rdoc>.freeze, ["~> 4.2"])
      s.add_development_dependency(%q<json>.freeze, ["~> 1.8"])
      s.add_development_dependency(%q<rake-compiler>.freeze, ["~> 0.9"])
      s.add_development_dependency(%q<rake-compiler-dock>.freeze, ["~> 0.4"])
      s.add_development_dependency(%q<simplecov>.freeze, ["~> 0.9"])
    else
      s.add_dependency(%q<rake>.freeze, ["~> 10.4"])
      s.add_dependency(%q<minitest>.freeze, ["~> 5.5"])
      s.add_dependency(%q<rdoc>.freeze, ["~> 4.2"])
      s.add_dependency(%q<json>.freeze, ["~> 1.8"])
      s.add_dependency(%q<rake-compiler>.freeze, ["~> 0.9"])
      s.add_dependency(%q<rake-compiler-dock>.freeze, ["~> 0.4"])
      s.add_dependency(%q<simplecov>.freeze, ["~> 0.9"])
    end
  else
    s.add_dependency(%q<rake>.freeze, ["~> 10.4"])
    s.add_dependency(%q<minitest>.freeze, ["~> 5.5"])
    s.add_dependency(%q<rdoc>.freeze, ["~> 4.2"])
    s.add_dependency(%q<json>.freeze, ["~> 1.8"])
    s.add_dependency(%q<rake-compiler>.freeze, ["~> 0.9"])
    s.add_dependency(%q<rake-compiler-dock>.freeze, ["~> 0.4"])
    s.add_dependency(%q<simplecov>.freeze, ["~> 0.9"])
  end
end
