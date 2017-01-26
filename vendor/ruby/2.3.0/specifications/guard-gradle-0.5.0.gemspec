# -*- encoding: utf-8 -*-
# stub: guard-gradle 0.5.0 ruby lib

Gem::Specification.new do |s|
  s.name = "guard-gradle".freeze
  s.version = "0.5.0"

  s.required_rubygems_version = Gem::Requirement.new(">= 0".freeze) if s.respond_to? :required_rubygems_version=
  s.require_paths = ["lib".freeze]
  s.authors = ["Bryan Ricker".freeze, "Andrew Glover".freeze]
  s.date = "2014-09-15"
  s.description = "Continuous Testing Guard plugin for Gradle".freeze
  s.email = ["bricker88@gmail.com".freeze, "ajglover@gmail.com".freeze]
  s.homepage = "https://github.com/bricker/guard-gradle".freeze
  s.licenses = ["MIT".freeze]
  s.rubygems_version = "2.6.10".freeze
  s.summary = "Build & test your Java projects continuously as you work.".freeze

  s.installed_by_version = "2.6.10" if s.respond_to? :installed_by_version

  if s.respond_to? :specification_version then
    s.specification_version = 4

    if Gem::Version.new(Gem::VERSION) >= Gem::Version.new('1.2.0') then
      s.add_runtime_dependency(%q<guard>.freeze, ["~> 2.6.1"])
      s.add_development_dependency(%q<test-unit>.freeze, [">= 2.5.5"])
      s.add_development_dependency(%q<mocha>.freeze, [">= 1.0.0"])
    else
      s.add_dependency(%q<guard>.freeze, ["~> 2.6.1"])
      s.add_dependency(%q<test-unit>.freeze, [">= 2.5.5"])
      s.add_dependency(%q<mocha>.freeze, [">= 1.0.0"])
    end
  else
    s.add_dependency(%q<guard>.freeze, ["~> 2.6.1"])
    s.add_dependency(%q<test-unit>.freeze, [">= 2.5.5"])
    s.add_dependency(%q<mocha>.freeze, [">= 1.0.0"])
  end
end
