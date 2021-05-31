# CONFIG

## A simple configuration manager

The configuration values are located in two places.
A java class containing the configurable values must provide default values. A text configuration file can overload these values.
The point of the configuration file is to change the configuration without recompiling.

The configuration file must consist of ```key = value``` lines. Commentaries are introduced with the ```#``` character. [Here is an config file example](https://raw.githubusercontent.com/PFGimenez/config/master/example/config_example.ini).

## Maven installation

If you want to use this library in one of your maven project, add this to your `pom.xml`:

      <repositories>
        <repository>
          <id>central</id>
          <url>https://repo1.maven.org/maven2</url>
        </repository>
        <repository>
          <id>github</id>
          <url>https://maven.pkg.github.com/kraken-robotics/*</url>
          <snapshots>
            <enabled>true</enabled>
          </snapshots>
        </repository>
      </repositories>

and

    <dependency>
        <groupId>pfg.config</groupId>
        <artifactId>config</artifactId>
        <version>1.4</version>
    </dependency>



## Manual compilation [![Build Status](https://travis-ci.org/PFGimenez/config.svg?branch=master)](https://travis-ci.org/PFGimenez/config)

You can compile it yourself. You will need a JDK and maven.

    $ git clone https://github.com/PFGimenez/config.git --depth 1
    $ cd config/core
    $ mvn install
