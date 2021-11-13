public final class GameEngine {

    private GameMap currentMap;
    private Player player1;
    private Player player2;
    public void setUpGame(String map)
    {
        currentMap.setUpLabyrinths(map);
        System.out.print(currentMap.left);
    }
}
