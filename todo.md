czesci rzeczy sie nie udalo :(( za malo czasu w zyciu<br>
repo jezeli Pan zainteresowany :D https://github.com/Sasakiai/pjatk-pacman

nieukonczone rzeczy:
- pole do ilosci wierszy/kolumn
- algorytm do generacji labiryntu
- A*/Dijkstra dla duchow
- skalowalnosc okna

# graphics:
    - player
    - ghosts
    - powerups
    - dots

# views:
    - [panel] input for board size
    - [panel] highscores JList
    - [view] game over view

# models:
    - entity:
        - player
        - ghost
        - powerup
    - scoreManager:
        - timer
        - lives
        - points
    - highScoreEntry
    - highScoreDB

# controllers:
    - MainController:
        - make it an actual main controller which contains all other ones
    - GameController:
        - game state management
        - gameplay loop:
            - player movement
            - ghost movement
            - powerup distribution
            - keybindings
            - threading
    - HighScoiresController:
        - rw highs cores
    - InputHandler:
        - Handle player inputs
    - ThreadManager:
        - Manage and synchronize different threads

# hierarchy?:
    - MainController
        - MainMenuView
        - GameController
            - GameView
            - BoardModel
            - Player, Ghost, PowerUp
            - ThreadManager
            - InputHandler
        - HighScoresController
            - HighScoresView(Panel?)
            - HighScoresDB

# pomocy xd