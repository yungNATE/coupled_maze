import java.util.concurrent.TimeUnit;

enum Direction {UP, DOWN, RIGHT, LEFT}

abstract public class Entity {

    static int STEP_PIXELS = 50;
    Fenetre fenetre;
    Image icon;

    Position pos;

    Entity(int posX, int posY, String url, Fenetre f) {
        icon = new Image(url);
        pos = new Position(posX, posY);
        fenetre = f;
    }

    public void move(Direction direction) throws InterruptedException, CloneNotSupportedException {
        switch (direction) {
            case DOWN:
                moveAnimation(Direction.DOWN);
                break;

            case UP:
                moveAnimation(Direction.UP);
                break;

            case RIGHT:
                moveAnimation(Direction.RIGHT);
                break;

            case LEFT:
                moveAnimation(Direction.LEFT);
                break;
        }
    }


    private void moveAnimation(Direction d) throws InterruptedException, CloneNotSupportedException {
        Position currentPosition = pos;

        for (int i = 1; i <= STEP_PIXELS; i++) {
            moveOnePixel(d, currentPosition);
        }
    }
    
    public void hitWall(Direction direction) throws CloneNotSupportedException, InterruptedException {
        switch (direction) {
            case DOWN:
                hitWallAnimation(Direction.DOWN, Direction.UP);
                break;

            case UP:
                hitWallAnimation(Direction.UP, Direction.DOWN);
                break;

            case RIGHT:
                hitWallAnimation(Direction.RIGHT, Direction.LEFT);
                break;

            case LEFT:
                hitWallAnimation(Direction.LEFT, Direction.RIGHT);
                break;
        }
    }

    public void fall(){
        try {

            // Loading the Image
            BufferedImage image = ImageIO.read(imageFile);

            AffineTransform af = AffineTransform.getTranslateInstance(100, 100);

            // Rotating the Image in 90 degree
            af.rotate(Math.toRadians(90), image.getWidth() /2, image.getHeight()/2);

            Graphics2D graphics2d = (Graphics2D)g;

            graphics2d.drawImage(image, af, null);

            graphics2d.dispose();

        } catch (IOException e) {

            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    };

    private void hitWallAnimation(Direction d, Direction opposite) throws CloneNotSupportedException, InterruptedException {
        Position currentPosition = pos;

        for (int i = 1; i <= STEP_PIXELS/2; i++) {
            moveOnePixel(d, currentPosition);
        }
        for (int i = 1; i <= STEP_PIXELS/8; i++) {
            moveOnePixel(opposite, currentPosition);
        }
        for (int i = 1; i <= STEP_PIXELS/8; i++) {
            moveOnePixel(d, currentPosition);
        }
        for (int i = 1; i <= STEP_PIXELS/2; i++) {
            moveOnePixel(opposite, currentPosition);
        }
    }

    private void moveOnePixel(Direction d, Position currentPosition) throws CloneNotSupportedException, InterruptedException {
        Position oldPosition;
        oldPosition = (Position) currentPosition.clone();
        TimeUnit.MILLISECONDS.sleep(20);
        icon.effacer(fenetre, oldPosition.posX, oldPosition.posY);
        if (d == Direction.DOWN) icon.afficher(fenetre, currentPosition.posX, ++currentPosition.posY);
        else if (d == Direction.UP) icon.afficher(fenetre, currentPosition.posX, --currentPosition.posY);
        else if (d == Direction.RIGHT) icon.afficher(fenetre, ++currentPosition.posX, currentPosition.posY);
        else icon.afficher(fenetre, --currentPosition.posX, currentPosition.posY);
    }

}
