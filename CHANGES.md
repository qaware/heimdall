# Change Log

## Unreleased
### Changed
* Salt is created on Windows platforms with the `Windows-PRNG` algorithm
* Java 8 is now required to use Heimdall
* Removed dependency on common-codec, use native Java 8 Base64 decoder / encoder

## [1.5.1] - 2017-08-03
### Changed
* Fixed pom.xml for Maven Central publication

## [1.5] - 2017-08-03
### Changed
* Changed groupId of artifact to `de.qaware.heimdall`
* Extended pom.xml to comply with Maven Central publishing requirements
* Changed distribution management to QAware OSS Bintray repository

## [1.4] - 2016-05-03
### Changed
* Added dependency on commons-codec for Android support. See https://github.com/qaware/heimdall/issues/4

## [1.3] - 2015-11-20
### Changed
* Removed the dependency on Google Guava
* Binaries are now hosted on [jCenter](https://bintray.com/bintray/jcenter)

## [1.2] - 2015-06-30
### Added
Imported project.