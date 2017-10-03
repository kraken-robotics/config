# CONFIG

## A simple configuration manager

The configuration values are located in two places.
A java class containing the configurable values must provide default values. A text configuration file can overload these values.
The point of the configuration file is to change the configuration without recompiling.

The configuration file must consist of ```key = value``` lines. Commentaries are introduced with the ```#``` character. [Here is an config file example](https://raw.githubusercontent.com/PFGimenez/config/master/example/config_example.ini).

## Downloading / compiling

[![Build Status](https://travis-ci.org/PFGimenez/config.svg?branch=master)](https://travis-ci.org/PFGimenez/config)

You can find the latest compiled .jar here : https://github.com/PFGimenez/config/releases/download/v1.2/config-1.2.jar.

Otherwise, you can compile it yourself. You will need a JDK and maven.

    $ git clone https://github.com/PFGimenez/config.git --depth 1
    $ cd config
    $ mvn install

The jar file will be located in the `target` directory.
