# Project Title

Mancala

## Description

 * This program allows you to play Mancala using two different rulesets: the Ayo ruleset and the traditional Kalah ruleset
 * The game is interfaced to a GUI which provides options for saving and loading games and user profiles, which track the
   number of wins and games played in each ruleset
 * There is an explanation on the game of Mancala and each ruleset at the bottom of the README

## Getting Started

### Dependencies

N/A

### Executing program

''
gradle build
gradle echo
''

copy and paste one of the instructions into the terminal to run the jar file

## Limitations

TO DO:
 * There is a known bug with setting a profile name before a game has started; this will spawn a null-pointer exception but will
   not crash the program

## Author Information

Wesley Renaud
wrenaud@uoguelph.ca

## Development History

Keep a log of what things you accomplish when.  You can use git's tagging feature to tag the versions or you can reference commits.

* 1.0
    * GUI and persistence elements fully working
    * Both rulesets working correctly

## How to play Mancala

Mancala is a two-player board game consisting of twelve pits (six on each player's side), two stores (one for each player) and a 
finite number of stones. The two players compete against one-another with the objective of putting as many stones as possible into 
their store. The game ends when at least one player has no stones to move. 

The game starts by numbering the pits one through twelve. Each player gets six pits facing them. The players' stores are placed to
the right of the final pit on their side. Each of a player's pits have a pit of the other player across from it. Players take turns 
selecting one of their pits. When a pit is selected, all of its stones are removed and distributed counter-clockwise, one at a time 
into the following pits and stores until all of the stones are gone. There is no stone placed in the opponent's store.

Kalah Rules:

Stones are distributed counter-clockwise until none are left. If the final stone lands in the current player's store they get an
extra turn. If the last stone lands in an empty pit on the current player's side, all of the stones in the pit across from it are
distributed into the player's store.

Ayo Rules:

Stones are distributed counter-clockwise until none are left. This process is repeatedly with the pit where the last stone ended
until that pit has zero stones prior to the last stone on the current move being added to it. If the last stone lands in an empty
pit on the current player's side, all of the stones in that pit plus all of the stones in the put across from it are moved into
the player's store.


## Acknowledgments

Inspiration, code snippets, etc.
* [awesome-readme](https://github.com/matiassingers/awesome-readme)
* [simple-readme] (https://gist.githubusercontent.com/DomPizzie/7a5ff55ffa9081f2de27c315f5018afc/raw/d59043abbb123089ad6602aba571121b71d91d7f/README-Template.md)
* https://www.officialgamerules.org/mancala (Mancala rules)
* https://www.mathsisfun.com/games/mancala.html (Online Mancala game - used for learning rules)
