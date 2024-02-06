# Project Title

Mancala

## Description

This new version of Mancala expands on the interface and playability of the game, while refactoring the classes of the program to
make the units more cohesive, while also reducing coupling. The game now employs two rule sets: the standard Kalah rules and the
new Ayo rules which iteratively moves the stones. There is also a feature to connect individual games to a player or user profile, 
which counts the number of games played and wins within in each rule set. 
We refactored the units of code by dissecting the board class into two parts. We split the board class into two parts: the game 
rules, and the data structure. The two rule sets extend the abstract gameRules class, and the Pit and Store classes implement the 
interfact, countable, which allows most parts of the game to use these classes without knowing the specific implementations of 
stores and pits.

## Getting Started

### Dependencies

N/A

### Executing program

''
gradle build
gradle echo
''

copy and paste one of the instructions into the terminal

## Limitations

TO DO:
 * Implement GUI
 * Implement user profile system

## Author Information

Wesley Renaud
wrenaud@uoguelph.ca

## Development History

Keep a log of what things you accomplish when.  You can use git's tagging feature to tag the versions or you can reference commits.

* 1.0
    * Added fix to start new game with new user
    * Finished end of game issues with buttons
    * Added buttons to see each of the user stats
* 0.8
    * Fixed issue with loading a prevous game
    * Added javadocs to a few places without one
    * Fixed issue with Ayo turn/move not ending 
    * Working with displays and GUI functionality 
    * Checked tests of Ayo/Kalah rules methods
* 0.7
    * Working on file choosing/loads and saves
    * Working on action listners for game moves
    * Working on GUI component, basic is done
* 0.6
    * Fixed logical issue with swapping turns
    * Fixed logical issue with captruing stones
    * Added TextUI to test new game features
* 0.5
    * Removed redundant PMD errors of classes 
    * Fixed bugs with AyoRules moving logic
    * Finished writing test cases of all classes
    * Touched up/formatted javadocs comments  
    * Refactor/cut down over MancalaGame class 
    * Remove/move board class into Gamerules 
* 0.4
    * Finished testing of almost all classes
    * Finished logic methods for KalahRules class
    * Removed PMD errors down to below 100
    * Created steal and extra turn mechanics
    * Completed javadocs for most methods
* 0.3
    * Added countable interface to Pit and Store
    * Removed PMD errors from new classes
    * Finalized game save and load mechanics
* 0.2
    * Started to add save/load options in UI
    * Added UserProfile class for player records
    * Added saver class for saving/loaded games
    * Added serializable interface to all classes
* 0.1
    * Reduced PMD errors from 344 down to ~90
    * Refactored / broke down complicated methods
    * Sorted all method in all classes

## Acknowledgments

Inspiration, code snippets, etc.
* [awesome-readme](https://github.com/matiassingers/awesome-readme)
* [simple-readme] (https://gist.githubusercontent.com/DomPizzie/7a5ff55ffa9081f2de27c315f5018afc/raw/d59043abbb123089ad6602aba571121b71d91d7f/README-Template.md)
* http://localhost:3000 (textbook)
* http://localhost:8000/docs (javadocs)
* file:///Users/wesleyrenaud/Desktop/School/CIS*2430/Documents/docs/mancala/MancalaGame.html (Mancala javadocs)
* https://www.officialgamerules.org/mancala (Mancala rules)
* https://www.mathsisfun.com/games/mancala.html (Online Mancala game - used for learning rules)