# CONFIG

## A simple configuration manager

The configuration values are located in two places.
An java enum of the configurable values must provide default values. A text configuration file can overload these values.
The point of the configuration file is to change the configuration without recompiling.

The configuration file must consist of ```key = value``` lines. Commentaries are introduced with the ```#``` character. [Here is an config file example](https://raw.githubusercontent.com/PFGimenez/config/master/config_example.ini).

## Downloading / compiling

You can find the latest compiled .jar here : https://github.com/PFGimenez/config/releases/tag/latest.

Otherwise, you can compile it yourself. You will need a JDK and ```ant```.

    $ git clone https://github.com/PFGimenez/config.git --depth 1
    $ cd config
    $ ant

Two files will be created :
- config.jar, containing the compiled code .class
- config-sources.jar, containing the sources .java

An example is available with the files [ConfigInfoExamples.java](https://raw.githubusercontent.com/PFGimenez/config/master/src/config/ConfigInfoExamples.java) and [config_example.ini](https://raw.githubusercontent.com/PFGimenez/config/master/config_example.ini).

