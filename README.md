# Introduction
Star Rats is an asteroids-style space shooter game created with Java FX 8. This code is for demonstration purposes only, if you wish to play the game, download the project directory and open in Netbeans to compile.
## Skills Demonstrated
Game loop, canvas drawing, for loops, nested if statements, polymorphism, abstract classes, interfaces (serializable, comparable, custom), try/catch/finally blocks, file writing
## Screenshots

  <img src="https://github.com/jdiggins/java-space-game/blob/master/images/str-scrn-1.png?raw=true" alt=""/>
  <img src="https://github.com/jdiggins/java-space-game/blob/master/images/str-scrn-2.png?raw=true" alt=""/>

## Video
[![Youtube](https://img.youtube.com/vi/p6pMzaHjdHk/0.jpg)](https://www.youtube.com/watch?v=p6pMzaHjdHk)

## Details
Star Rats uses Java FX's Timeline to create a simple game loop. All graphics are rendered using a Canvas, so the graphics are redrawn each frame. All of the games objects are kept track of using the Handler class, which calls the update function to update the position and state of every game object during each iteration of the game loop. Users are able to save and load the game, a feature made possible with the use of serialization. 




