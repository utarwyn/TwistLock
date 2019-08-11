<div align="center">
    <h1 align="center">TwistLock</h1>
    <h4 align="center">
        Multiplayer container management game built with sockets
        <br/>
        Second year of my technological university degree
    </h4>
</div>

### Project description

TwistLock is a game where players have to deal with *twistlocks* to fill containers with their color.\
The goal of the game is to have to most containers with its color.

> Made by **Maxime Malgorn**, **Robin Gehan**, **Félix Denis**, **Florian Cruchon** and **Leonard Jouen**.


### Play the game

There is no easy way to compile and play the game because we don't use `Maven` or `Gradle`.\
So, you have to run these commands in your terminal (from the root folder of the project):

1. `mkdir out && cd src`
2. `javac twistlock/Controleur.java -d ../out`
3. `cd ../out`
4. `cp -r ../src/twistlock/ihm/img twistlock/ihm/img`

You can now run the game by typing `java twistlock.Controleur`.

> Alternatively, you can download the compiled jar file in the **releases** section of this repository.
