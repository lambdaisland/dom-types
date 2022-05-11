# dom-types

<!-- badges -->
[![cljdoc badge](https://cljdoc.org/badge/com.lambdaisland/dom-types)](https://cljdoc.org/d/com.lambdaisland/dom-types) [![Clojars Project](https://img.shields.io/clojars/v/com.lambdaisland/dom-types.svg)](https://clojars.org/com.lambdaisland/dom-types)
<!-- /badges -->

Print handlers and datafy/nav for built-in browser types

## Features

Include `lambdaisland.dom-types` in your ClojureScript build to get print and pprint handlers for various built-in types. This way instead of printing `#object[HTMLElement]` you'll get `#js/Element [:div "hello"]`.

These are currently implemented:

- Text
- Element
- DocumentFragment
- HTMLDocument
- XMLDocument
- Document
- Window
- Location
- HTMLCollection
- NodeList
- KeyboardEvent
- TouchEvent
- Touch
- TouchList
- PointerEvent
- MouseEvent
- WheelEvent
- DragEvent
- Blob
- File

This project is a [stamp collection](https://twitter.com/plexus/status/1372179545139335170). Being exhaustive is not a stated goal, but pull requests that add other common built-in types are welcome.

<!-- installation -->
## Installation

To use the latest release, add the following to your `deps.edn` ([Clojure CLI](https://clojure.org/guides/deps_and_cli))

```
com.lambdaisland/dom-types {:mvn/version "0.5.37"}
```

or add the following to your `project.clj` ([Leiningen](https://leiningen.org/))

```
[com.lambdaisland/dom-types "0.5.37"]
```
<!-- /installation -->

## Rationale

## Usage

<!-- opencollective -->
## Lambda Island Open Source

<img align="left" src="https://github.com/lambdaisland/open-source/raw/master/artwork/lighthouse_readme.png">

&nbsp;

dom-types is part of a growing collection of quality Clojure libraries created and maintained
by the fine folks at [Gaiwan](https://gaiwan.co).

Pay it forward by [becoming a backer on our Open Collective](http://opencollective.com/lambda-island),
so that we may continue to enjoy a thriving Clojure ecosystem.

You can find an overview of our projects at [lambdaisland/open-source](https://github.com/lambdaisland/open-source).

&nbsp;

&nbsp;
<!-- /opencollective -->

<!-- contributing -->
## Contributing

Everyone has a right to submit patches to dom-types, and thus become a contributor.

Contributors MUST

- adhere to the [LambdaIsland Clojure Style Guide](https://nextjournal.com/lambdaisland/clojure-style-guide)
- write patches that solve a problem. Start by stating the problem, then supply a minimal solution. `*`
- agree to license their contributions as MPL 2.0.
- not break the contract with downstream consumers. `**`
- not break the tests.

Contributors SHOULD

- update the CHANGELOG and README.
- add tests for new functionality.

If you submit a pull request that adheres to these rules, then it will almost
certainly be merged immediately. However some things may require more
consideration. If you add new dependencies, or significantly increase the API
surface, then we need to decide if these changes are in line with the project's
goals. In this case you can start by [writing a pitch](https://nextjournal.com/lambdaisland/pitch-template),
and collecting feedback on it.

`*` This goes for features too, a feature needs to solve a problem. State the problem it solves, then supply a minimal solution.

`**` As long as this project has not seen a public release (i.e. is not on Clojars)
we may still consider making breaking changes, if there is consensus that the
changes are justified.
<!-- /contributing -->

<!-- license -->
## License

Copyright &copy; 2021 Arne Brasseur and Contributors

Licensed under the term of the Mozilla Public License 2.0, see LICENSE.
<!-- /license -->