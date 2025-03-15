# Jungle

**Team member:** Qijun He, Youpeng Zhang

## About

- This is the final project of 2023 Spring course "Java A" in SUSTC, lectured by Dr. Jianqiao Yu.
- We spent about 1-2 weeks to extend the demo to a complete playable game, and got the chance of lecture presentation (to show of our work)
- At last we got a full score (100/100) on this project and bonus (12/20)



## Why our project succeeded?

1. Organized objects management: strictly design of each class and apply class methods
2. Implementation of AI: we applied two AI algorithms in this game, greedy algorithm and $\alpha$-$\beta$ pruning algorithm, to enhance single-player experience. (we were then beginner of Java as a freshman. Both of us knew nothing about coding)
3. Fast learning of frontend: Our lecture about GUI is at week 15, but DDL of project is week 16, so we learn GUI by ourselves in advance.



## To play this game

### Basic Rules

- There are eight types of chesses in the chessboard, from the strongest to weakest are
  - Elephant → Lion → Tiger → Leopard → Wolf → Dog → Cat → Rat.
- The stronger can **EAT** the waeker, **EXCEPT** that Rat can eat Elephant but Elephant cannot eat Rat.
- The same type of chesses can eat each other.

### Special Rules

- **Traps & Dens**
  - There are **TRAP** cells besides **DENS**. When one player move a chess to his opponent's DEN or eat up all the enemies, **he wins**.
  - But when his chesses step in TRAPs, **all** chesses of his opponent can eat them.
  - All chess can move to adjacent cells for one time.
- **Rivers**
  - There are RIVER cells in chessboard.
  - Only **RAT**s can step into RIVERs, but it can't *step out rivers* and *eat Elephant* at the same time.
  - Only Lion and Tiger can jump across the RIVERs(toward straight line and to the nearest bank), and they can *jump* and *eat* what they can eat **AT THE SAME TIME**.
  - BUT if a Rat in RIVERs is in their(Tigers, Lions) way, they can't jump across the RIVERs.



## Optimizations

1. We had already add Double-Player mode, but it's unstable. So that version wasn't published.
2. UI is made by `Swing`. If we use `JavaFX` or others, it looks better.
3. AI algorithm should be improved.



