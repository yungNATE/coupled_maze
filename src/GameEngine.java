import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class GameEngine {

    private static GameMap map;
    private static Player player1;
    private static Player player2;



    public static void setUpGame(String mapName)
    {
        map = new GameMap(mapName);
        System.out.print(map.left);

        Position start = map.left.entrySet().stream().filter(v -> v.getValue().type == Tile.TypeCase.START).findFirst().orElseThrow().getKey();
        player1 = new Player(start.posX, start.posY, "ressources/cat.jpg", map);

        start = map.right.entrySet().stream().filter(v -> v.getValue().type == Tile.TypeCase.START).findFirst().orElseThrow().getKey();
        player2 = new Player(start.posX, start.posY, "ressources/cat.jpg", map);



    }
}
