# Dev Barcamp



## Building 

To build the website from source you will need:

* JDK 1.7
* boot-clj

Then, in a terminal in the project directory do:

    boot production build target
    cp -R target/* <github page's repo>/

To deploy to Github Pages

    cd <github page's repo>
    git commit -am "your message"
    git push origin master

## License

Copyright © 2017 Albert Lai

Distributed under the Eclipse Public License, the same as Clojure.
