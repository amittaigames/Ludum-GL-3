# Ludum GL 3

### Main Goal

The main goal of Ludum GL 3 is to provide an easy to use, light-weight game engine, good enough for new or advanced developers.
The engine is written to have full control of all aspects of the game: graphics, audio, and input. Eventually there could
be physics support, and other elements that some people might not want to write themselves.

### Dependencies

* LWJGL 		(lwjgl.jar)
* LWJGL Util	(lwjgl_util.jar)
* LWJGL Test	(lwjgl_test.jar)
* Slick Util	(slick-util.jar)

LWJGL natives are included with the game engine, will be extracted upon first run, and loaded every time.
The version of LWJGL that is used is **2.9.3**

### Version History

Current Version: **0.14**

|Version|Description|
|:------|:----------|
|0.1|Create window and render meshes|
|0.2|Plane wrapper around mesh|
|0.3|Keyboard implementation|
|0.4|Textures and string drawing|
|0.5|Mouse input|
|0.6|Natives self load|
|0.7|Sound playback (WAV support only as of now)|
|0.8|README added|
|0.9|Screenshots added|
|0.10|Fixed some bugs (audio natives, screenshots), and added "debugging"|
|0.11|Added maths and matrices|
|0.12|Added point math|
|0.13|Added textured mesh rotation and more window functions|
|0.14|Added a lighting system that is experimental|