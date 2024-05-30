<h1>MusicXMLToIReal</h1>

<h2>1. The Project</h2>

The goal of this project is to convert a <code>.musicxml</code> file into a file for [IReal Pro](https://www.irealpro.com/).
It takes the chords from the harmony defined by the <code>.musicxml</code> file.

For now, it only supports partwise musicxml with basic features, so some conversions won't work perfectly.

<h2>2. Installation Guide</h2>

Get the latest release in the <code>releases</code> directory. It is an executable <code>.jar</code> archive.<br>
Make sure you set the archive as executable.

<h2>3. Usage</h2>

There is no GUI as of yet, so you have to use it from the command line as follows:
    
    musicxml-to-ireal alpha1.0.jar path/to/file.musicxml (path/to/another/file.musicxml) ...

The program will create a <code>.html</code> file that can be opened with IReal Pro in your current working directory.<br>
In the future, support for specifying an alternative target directory will be added.
