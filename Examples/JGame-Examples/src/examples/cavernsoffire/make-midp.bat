del /S /Q classes-examples-tmp >devnull
mkdir classes-examples-tmp
del /S /Q classes-examples >devnull
mkdir classes-examples

echo "Using WTK_HOME=%WTK_HOME%"

set PREVERIFY=%WTK_HOME%/bin/preverify
set CLDCAPI=%WTK_HOME%/lib/cldcapi11.jar
set MIDPAPI=%WTK_HOME%/lib/midpapi20.jar

set APPNAME=CavernsOfFireMidlet
set MANIFESTNAME=examples\cavernsoffire\manifest_midp

javac -bootclasspath %CLDCAPI%;%MIDPAPI% -source 1.3 -target 1.3 -classpath classes-midp examples/cavernsoffire/CavernsOfFire.java -d classes-examples-tmp

%PREVERIFY% -classpath %CLDCAPI%;%MIDPAPI%;classes-midp -d classes-examples  classes-examples-tmp


copy examples\cavernsoffire\media-midp.tbl classes-examples\examples\cavernsoffire
copy examples\cavernsoffire\cavetile*.jpg classes-examples\examples\cavernsoffire\

copy examples\cavernsoffire\tomatogames-mobile-splash-2-120-dither-crop.png classes-examples\examples\cavernsoffire\
copy examples\cavernsoffire\crosshairs.png classes-examples\examples\cavernsoffire\
copy examples\cavernsoffire\firecavernsprites.png classes-examples\examples\cavernsoffire\
copy examples\cavernsoffire\ballbullet-tr.png classes-examples\examples\cavernsoffire\
copy examples\cavernsoffire\cavewalls.png classes-examples\examples\cavernsoffire\

copy examples\cavernsoffire\explo18k.wav classes-examples\examples\cavernsoffire\
copy examples\cavernsoffire\exploatari2-8k.wav classes-examples\examples\cavernsoffire\
copy examples\cavernsoffire\signal2-8k.wav classes-examples\examples\cavernsoffire\

echo "Jaring preverified class files..."
jar cmf %MANIFESTNAME% %APPNAME%.jar -C classes-examples examples -C classes-midp jgame

echo "Please update MIDlet-Jar-Size in jad manually..."

echo MIDlet-Jar-Size: >a.tmp
dir %APPNAME%.jar >b.tmp
type %MANIFESTNAME% a.tmp b.tmp >%APPNAME%.jad

