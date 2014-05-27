# teleprint
A web-based 3D printer host.

## Stability
This software is wicked unstable. At the moment, I can't promise it'll do anything properly. Eventually I'll commit a 1.0 release, though, which will be cause for festivities.

## License
[![GPL v3](http://www.gnu.org/graphics/gplv3-88x31.png)](http://www.gnu.org/licenses/gpl.html)

This software is released under the terms of the [GNU General Public License, version 3](http://www.gnu.org/licenses/gpl.html). For more details, see LICENSE.

_Note_: the JAR in `lib` is compiled from [scala-printerface](http://github.com/ryanthejuggler/scala-printerface). It's not stable enough to start nailing down version numbers, but once I've got it in fairly working order I'll post it to Maven.

## Using
As long as you have Scala, sbt, and a good Internet connection, all you need to do is `sbt run` and then visit [localhost:9000](http://localhost:9000) in your browser.

I'd highly recommend installing this on a Raspberry Pi and configuring it to run at boot; that way, you'll be able to simply connect to your printer over the network.
