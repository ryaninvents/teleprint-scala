# teleprint

A web-based 3D printer host.

## License
[![GPL v3](http://www.gnu.org/graphics/gplv3-88x31.png)](http://www.gnu.org/licenses/gpl.html)

This library is released under the terms of the [GNU General Public License, version 3](http://www.gnu.org/licenses/gpl.html). For more details, see LICENSE.

_Note_: the JAR in `lib` is compiled from [scala-printerface](http://github.com/ryanthejuggler/scala-printerface). I just haven't gotten around to putting it up on Maven.

## Using

As long as you have Scala, sbt, and a good Internet connection, all you need to do is `sbt run` and then visit [localhost:9000](http://localhost:9000) in your browser.

I'd highly recommend installing this on a Raspberry Pi and configuring it to run at boot; that way, you'll be able to simply connect to your printer over the network.
