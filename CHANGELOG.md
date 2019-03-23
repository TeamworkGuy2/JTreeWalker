# Change Log
All notable changes to this project will be documented in this file.
This project does its best to adhere to [Semantic Versioning](http://semver.org/).


--------
### [0.3.0](N/A) - 2019-03-22
#### Changed
* Removed lombok dependency
* Added missing interface and method documentation
* Renamed `IndexedSubtreeConsumer<T>` -> `IndexedTreeConsumer<T>`
* Renamed `SubtreeConsumer<T>` -> `TreeConsumer<T>`
* Renamed `SubtreeTransformer` -> `TreeTransformer`
* `KeyTreeLike` now has 2 generic parameters and `getParent()`, `getChild()`, and `getChildren()` return type `KeyTreeLike<K, D>` instead of an intermediate `P` generic type. If needed, customize the return types by creating a sub-interface with more specific return types.
* Marked `SimpleTreeUtil` as `final`

#### Removed
* Removed `IndexedTree` (moved `size()` into `TreeLike`)
* Removed `KeyTree` (moved `size()` into `KeyTreeLike`)


--------
### [0.2.0](https://github.com/TeamworkGuy2/JTreeWalker/commit/9e79649698288b8f98762159d751eec25bc15371) - 2018-09-23
#### Changed
* Upgrade to Java 10
* Upgrade to JUnit 5
* Update dependencies:
  * jcollection-util@0.7.5
* `TreeLike.getChildren()` returns `ListReadOnly` instead of `List`
  * `*Parameters` classes updated to use `ListReadOnly`


--------
### [0.1.2](https://github.com/TeamworkGuy2/JTreeWalker/commit/0b346a946ec71c3083882dca4f2c9225fac64615) - 2016-10-03
#### Changed
* Fixed some compiler warnings


--------
### [0.1.1](https://github.com/TeamworkGuy2/JTreeWalker/commit/eb66409ed08d8371a607bb91a60324e001b66723) - 2016-06-26
#### Changed
* Added JCollectionInterface, JCollectionBuilders, and JTuples dependencies
* Updated JCollectionFiller to latest 0.5.x version
* Updated jcollection-util dependency to latest 0.7.0 version
* Moved tests to new test directory
* Switched from versions.md format to CHANGELOG.md, see http://keepachangelog.com/


--------
### [0.1.0](https://github.com/TeamworkGuy2/JTreeWalker/commit/6becc2bdd208227b747005a8673a1a3473999ee9) - 2016-1-19
#### Added
* Initial version, code moved from jcollection-util to this stand alone library
