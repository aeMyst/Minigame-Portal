package src.ca.ucalgary.seng300.gamelogic;

import src.ca.ucalgary.seng300.gamelogic.cards.CardID;
import src.ca.ucalgary.seng300.gamelogic.cards.Location;
import src.ca.ucalgary.seng300.gamelogic.players.PlayerID;
import src.ca.ucalgary.seng300.graphics.Graphic;

import java.util.Optional;

public interface IGameLogic {
    Graphic getGraphic();

    GameState getGameState();

    int getNumPlayers();

    Optional<CardID> playerGrabbedCard(Location location, PlayerID playerID);

    void playerMovedCard(Location location, CardID cardID, PlayerID playerID);

    void playerReleasedCard(Location location, CardID cardID, PlayerID playerID);
}
