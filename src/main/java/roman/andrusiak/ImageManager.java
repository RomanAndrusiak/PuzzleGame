package roman.andrusiak;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageManager {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 1024;
    public static final int NumPuzzles = 16;

    /**
     * Resizes an image located at the specified URL to a predefined width and height.
     * The method reads the image file, resizes it using the specified width and height,
     * and returns the resized image as a BufferedImage object.
     *
     * @param url the URL of the image file to resize
     * @return the resized image as a BufferedImage object
     * @throws IOException if an error occurs while reading or resizing the image
     */
    public static BufferedImage resizeImage(String url) throws IOException {
        File inputFile = new File(url);
        BufferedImage inputImage = ImageIO.read(inputFile);
        BufferedImage resizedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(inputImage, 0, 0, WIDTH, HEIGHT, null);
        graphics2D.dispose();
        return resizedImage;
    }

    /**
     * Compares the left edge of one puzzle with the right edge of another puzzle.
     * The method takes two Puzzle objects and compares the corresponding pixels along the left and right edges
     * of their respective images. It counts the number of matching pixels and returns the count.
     *
     * @param puzzle1 the first Puzzle object to compare
     * @param puzzle2 the second Puzzle object to compare
     * @return the number of matching pixels between the left edge of puzzle1 and the right edge of puzzle2
     */
    public static int compareLeftWithRight(Puzzle puzzle1, Puzzle puzzle2) {
        BufferedImage image1 = puzzle1.getImage();
        BufferedImage image2 = puzzle2.getImage();
        int height = image1.getHeight();
        int count = 0;
        for (int y = 0; y < height; y++) {
            int pixel1 = image1.getRGB(0, y);
            int pixel2 = image2.getRGB(image2.getWidth() - 1, y);
            if (pixel1 == pixel2) {
                count++;
            }


        }
        return count;
    }

    /**
     * Compares the right edge of one puzzle with the left edge of another puzzle.
     * <p>
     * The method takes two Puzzle objects and compares the corresponding pixels along the right and left edges
     * <p>
     * of their respective images. It counts the number of matching pixels and returns the count.
     *
     * @param puzzle1 the first Puzzle object to compare
     * @param puzzle2 the second Puzzle object to compare
     * @return the number of matching pixels between the right edge of puzzle1 and the left edge of puzzle2
     */
    public static int compareRightWithLeft(Puzzle puzzle1, Puzzle puzzle2) {
        BufferedImage image1 = puzzle1.getImage();
        BufferedImage image2 = puzzle2.getImage();
        int height = image1.getHeight();
        int count = 0;
        for (int y = 0; y < height; y++) {
            int pixel1 = image1.getRGB(image1.getWidth() - 1, y);
            int pixel2 = image2.getRGB(0, y);

            if (pixel1 == pixel2) {
                count++;
            }

        }

        return count;
    }

    /**
     * Compares the bottom edge of one puzzle with the top edge of another puzzle.
     * The method takes two Puzzle objects and compares the corresponding pixels along the bottom and top edges
     * of their respective images. It counts the number of matching pixels and returns the count.
     *
     * @param puzzle1 the first Puzzle object to compare
     * @param puzzle2 the second Puzzle object to compare
     * @return the number of matching pixels between the bottom edge of puzzle1 and the top edge of puzzle2
     */
    public static int compareBottomWithTop(Puzzle puzzle1, Puzzle puzzle2) {
        BufferedImage image1 = puzzle1.getImage();
        BufferedImage image2 = puzzle2.getImage();
        int width = image1.getWidth();
        int count = 0;
        for (int x = 0; x < width; x++) {
            int pixel1 = image1.getRGB(x, image1.getHeight() - 1);
            int pixel2 = image2.getRGB(x, 0);

            if (pixel1 == pixel2) {
                count++;
            }
        }
        return count;
    }

    /**
     * Compares the top edge of one puzzle with the bottom edge of another puzzle.
     * The method takes two Puzzle objects and compares the corresponding pixels along the top and bottom edges
     * of their respective images. It counts the number of matching pixels and returns the count.
     *
     * @param puzzle1 the first Puzzle object to compare
     * @param puzzle2 the second Puzzle object to compare
     * @return the number of matching pixels between the top edge of puzzle1 and the bottom edge of puzzle2
     */
    public static int compareTopWithBottom(Puzzle puzzle1, Puzzle puzzle2) {
        BufferedImage image1 = puzzle1.getImage();
        BufferedImage image2 = puzzle2.getImage();
        int width = image1.getWidth();
        int count = 0;
        for (int x = 0; x < width; x++) {


            int pixel1 = image1.getRGB(x, 0);
            int pixel2 = image2.getRGB(x, image2.getHeight() - 1);

            if (pixel1 == pixel2) {
                count++;
            }
        }
        return count;
    }

    /**
     * Splits a given image into multiple puzzle pieces.
     * The method divides the image into a 4x4 grid and extracts each grid cell as a puzzle piece.
     * The resulting puzzle pieces are returned as an array of BufferedImages.
     *
     * @param image the BufferedImage to be split into puzzle pieces
     * @return an array of BufferedImages representing the puzzle pieces
     */
    public static BufferedImage[] splitImage(BufferedImage image) {

        int puzzleCount = 0;
        BufferedImage[] puzzles = new BufferedImage[NumPuzzles];
        int startX = 0;
        int startY = 0;
        int width = WIDTH / 4;
        int height = HEIGHT / 4;
        while (puzzleCount < NumPuzzles) {
            BufferedImage puzzle = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int y = startY; y < startY + height; y++) {
                for (int x = startX; x < startX + width; x++) {
                    int rgb = image.getRGB(x, y);
                    int puzzleX = x - startX;
                    int puzzleY = y - startY;
                    puzzle.setRGB(puzzleX, puzzleY, rgb);
                }
            }
            puzzles[puzzleCount] = puzzle;
            puzzleCount++;
            startX += width;
            if (startX >= image.getWidth()) {
                startX = 0;
                startY += height;
            }
        }
        return puzzles;
    }

    /**
     * Saves a BufferedImage object as a file at the specified file path.
     * The method writes the image data to a file in PNG format.
     *
     * @param image    the BufferedImage object to be saved as a file
     * @param filePath the file path where the image should be saved
     */
    public static void saveImage(BufferedImage image, String filePath) {
        try {
            File outputFile = new File(filePath);
            ImageIO.write(image, "png", outputFile);
        } catch (IOException e) {
            System.err.println("Problem with image saving" + e.getMessage());
        }
    }

    /**
     * Fills the neighbor connections for each puzzle piece in the given array.
     * The method compares each puzzle piece with every other piece to determine its neighbors
     * based on matching edges. The puzzle piece with the closest match in each direction
     * (left, right, top, bottom) is set as the neighbor for the current puzzle piece.
     *
     * @param puzzles an array of Puzzle objects representing the puzzle pieces
     * @return an array of Puzzle objects with updated neighbor connections
     */
    public static Puzzle[] fillCompares(Puzzle[] puzzles) {
        for (int i = 0; i < puzzles.length; i++) {
            int leftMax = 1;
            int rightMax = 1;
            int topMax = 1;
            int bottomMax = 1;
            for (int j = 0; j < puzzles.length; j++) {
                if (leftMax < compareLeftWithRight(puzzles[i], puzzles[j])) {
                    puzzles[i].setPuzzleLeft(puzzles[j]);
                    leftMax = compareLeftWithRight(puzzles[i], puzzles[j]);
                } else if (compareRightWithLeft(puzzles[i], puzzles[j]) > rightMax) {
                    puzzles[i].setPuzzleRight(puzzles[j]);
                    rightMax = compareRightWithLeft(puzzles[i], puzzles[j]);
                } else if (compareBottomWithTop(puzzles[i], puzzles[j]) > bottomMax) {
                    puzzles[i].setPuzzleDown(puzzles[j]);
                    bottomMax = compareBottomWithTop(puzzles[i], puzzles[j]);
                } else if (compareTopWithBottom(puzzles[i], puzzles[j]) > topMax) {
                    puzzles[i].setPuzzleUp(puzzles[j]);
                    topMax = compareTopWithBottom(puzzles[i], puzzles[j]);
                }
            }
        }
        return puzzles;
    }

    /**
     * Sorts the puzzle pieces in the correct order.
     * <p>
     * The method uses a heuristic approach to determine the correct placement of puzzle pieces.
     * <p>
     * It starts by finding the top-left corner piece and then iteratively places the adjacent pieces
     * <p>
     * based on their connections until all the pieces are sorted.
     *
     * @param puzzles an array of Puzzle objects representing the puzzle pieces
     * @return a sorted array of Puzzle objects representing the puzzle pieces in the correct order
     */
    public static Puzzle[] sortPuzzle(Puzzle[] puzzles) {
        Puzzle[] sortedPuzzle = new Puzzle[puzzles.length];
        for (int i = 0; i < sortedPuzzle.length; i++) {
            if (puzzles[i].getPuzzleUp() == null && puzzles[i].getPuzzleLeft() == null) {
                sortedPuzzle[0] = puzzles[i];
                break;
            }
        }
        if (sortedPuzzle[0] == null) {
            for (int i = 0; i < sortedPuzzle.length; i++) {
                if (puzzles[i].getPuzzleLeft() == null && puzzles[i].getPuzzleDown() == null) {
                    sortedPuzzle[0] = puzzles[i].getPuzzleUp().getPuzzleUp().getPuzzleUp();
                    break;
                }
            }
        }
        int indicator = 1;
        for (int i = 1; i < puzzles.length; i++) {
            if (indicator < 4) {
                if (sortedPuzzle[i - 1].getPuzzleRight() != null) {
                    sortedPuzzle[i] = sortedPuzzle[i - 1].getPuzzleRight();
                    indicator++;
                } else if (i > 4) {
                    if (sortedPuzzle[i - 4].getPuzzleDown() != null) {
                        sortedPuzzle[i] = sortedPuzzle[i - 4].getPuzzleDown();
                        indicator++;
                    } else {
                        sortedPuzzle[i] = sortedPuzzle[i - 3].getPuzzleDown();
                        indicator++;
                    }
                } else {
                    sortedPuzzle[i] = sortedPuzzle[i - 1].getPuzzleDown().getPuzzleRight().getPuzzleUp();
                    indicator++;
                }
            } else {
                indicator = 1;
                if (sortedPuzzle[i - 4].getPuzzleDown() != null) {
                    sortedPuzzle[i] = sortedPuzzle[i - 4].getPuzzleDown();
                } else if (sortedPuzzle[i - 3].getPuzzleDown() != null && sortedPuzzle[i - 3].getPuzzleDown().getPuzzleLeft() != null) {
                    sortedPuzzle[i] = sortedPuzzle[i - 3].getPuzzleDown().getPuzzleLeft();

                } else {
                    sortedPuzzle[i] = sortedPuzzle[i - 2].getPuzzleLeft().getPuzzleLeft();
                }
            }
        }
        return sortedPuzzle;
    }

    /**
     * Compares two BufferedImage objects.
     * <p>
     * The comparison takes place in two stages: size comparison and pixel comparison.
     *
     * @param image1 the first BufferedImage object
     * @param image2 the second BufferedImage object
     * @return true if the images are equal in size and pixel values, false otherwise
     */
    public static boolean compareImages(BufferedImage image1, BufferedImage image2) {
        if (image1.getWidth() != image2.getWidth() || image1.getHeight() != image2.getHeight()) {
            return false;
        }
        for (int y = 0; y < image1.getHeight(); y++) {
            for (int x = 0; x < image1.getWidth(); x++) {
                int rgb1 = image1.getRGB(x, y);
                int rgb2 = image2.getRGB(x, y);

                if (rgb1 != rgb2) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Collects images from an array of Puzzle objects and combines them into a single image.
     * <p>
     * The method arranges the images in a grid pattern, with 4 images per row, and combines them
     * <p>
     * into a larger image.
     *
     * @param puzzles an array of Puzzle objects representing the individual images to be collected
     * @return a BufferedImage object containing the combined image of all the input puzzles
     */
    public static BufferedImage collectImages(Puzzle[] puzzles) {
        BufferedImage[] images = new BufferedImage[16];
        for (int i = 0; i < images.length; i++) {
            images[i] = puzzles[i].getImage();
        }
        int width = images[0].getWidth() * 4;
        int height = images[0].getHeight() * 4;

        BufferedImage combinedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = combinedImage.getGraphics();
        int x = 0;
        int y = 0;
        for (int i = 0; i < images.length; i++) {
            g.drawImage(images[i], x, y, null);
            x += images[i].getWidth();
            if ((i + 1) % 4 == 0) {
                x = 0;
                y += images[i].getHeight();
            }
        }
        return combinedImage;
    }
}
