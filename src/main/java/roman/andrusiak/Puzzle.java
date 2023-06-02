package roman.andrusiak;

import java.awt.image.BufferedImage;

public class Puzzle {
    private Puzzle puzzleLeft;
    private Puzzle puzzleUp;
    private Puzzle puzzleRight;
    private Puzzle puzzleDown;
    private BufferedImage image;

    public Puzzle(BufferedImage image) {
        this.puzzleLeft = null;
        this.puzzleDown = null;
        this.puzzleRight = null;
        this.puzzleUp = null;
        this.image = image;
    }

    public Puzzle getPuzzleLeft() {
        return puzzleLeft;
    }

    public void setPuzzleLeft(Puzzle puzzleLeft) {
        this.puzzleLeft = puzzleLeft;
    }

    public Puzzle getPuzzleUp() {
        return puzzleUp;
    }

    public void setPuzzleUp(Puzzle puzzleUp) {
        this.puzzleUp = puzzleUp;
    }

    public Puzzle getPuzzleRight() {
        return puzzleRight;
    }

    public void setPuzzleRight(Puzzle puzzleRight) {
        this.puzzleRight = puzzleRight;
    }

    public Puzzle getPuzzleDown() {
        return puzzleDown;
    }

    public void setPuzzleDown(Puzzle puzzleDown) {
        this.puzzleDown = puzzleDown;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
