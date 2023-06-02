package roman.andrusiak;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The PuzzleApp class represents a JFrame-based puzzle application.
 * It allows users to solve a puzzle by swapping puzzle pieces until the correct image is formed.
 */
public class PuzzleApp extends JFrame {
    private Puzzle[] puzzles;
    private JButton[] puzzleButtons;
    private int firstSelectedIndex = -1;
    private int secondSelectedIndex = -1;

    public PuzzleApp() throws IOException {
        setTitle("Puzzle App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String filename = "";
        JPanel filePanel = new JPanel();
        JLabel fileLabel = new JLabel("Enter image filename: ");
        JTextField fileTextField = new JTextField(20);
        filePanel.add(fileLabel);
        filePanel.add(fileTextField);
        while (!new File(filename).exists()) {
            int result = JOptionPane.showConfirmDialog(null, filePanel, "Start menu", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                filename = fileTextField.getText();
            } else {
                System.exit(0);
            }
        }

        puzzles = new Puzzle[16];
        puzzleButtons = new JButton[16];
        JPanel puzzlePanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        BufferedImage[] images = ImageManager.splitImage(ImageManager.resizeImage(filename));
        List<BufferedImage> imgList = Arrays.asList(images);
        Collections.shuffle(imgList);
        imgList.toArray(images);
        for (int i = 0; i < 16; i++) {
            puzzles[i] = new Puzzle(images[i]);
            puzzleButtons[i] = new JButton(new ImageIcon(images[i]));
            puzzleButtons[i].addActionListener(new PuzzleButtonListener(i));
            int imageWidth = images[i].getWidth();
            int imageHeight = images[i].getHeight();
            int buttonWidth = imageWidth + 10;
            int buttonHeight = imageHeight + 10;
            puzzleButtons[i].setPreferredSize(new Dimension(buttonWidth, buttonHeight));
            constraints.gridx = i % 4;
            constraints.gridy = i / 4;
            constraints.weightx = 1;
            constraints.weighty = 1;
            puzzlePanel.add(puzzleButtons[i], constraints);
        }
        JButton checkButton = new JButton("Check");
        checkButton.addActionListener(new CheckButtonListener());
        puzzlePanel.add(checkButton);
        add(puzzlePanel);
        puzzlePanel.setPreferredSize(new Dimension(1330, 1024));
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * ActionListener implementation for puzzle buttons.
     * Handles the puzzle button clicks and manages the selection and swapping of puzzles.
     */
    private class PuzzleButtonListener implements ActionListener {
        private int index;

        public PuzzleButtonListener(int index) {
            this.index = index;
        }

        /**
         * Handles the actionPerformed event when a puzzle button is clicked.
         * If no puzzle is selected, sets the current puzzle as the first selected puzzle and highlights it.
         * If one puzzle is already selected, sets the current puzzle as the second selected puzzle,
         * swaps the puzzles, clears the selection, and updates the button icons.
         *
         * @param e The ActionEvent representing the button click.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (firstSelectedIndex == -1) {
                firstSelectedIndex = index;
                puzzleButtons[firstSelectedIndex].setBorder(BorderFactory.createLineBorder(Color.RED, 3));
            } else if (secondSelectedIndex == -1) {
                secondSelectedIndex = index;
                puzzleButtons[secondSelectedIndex].setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                swapPuzzles();
                clearSelection();
            }
        }
    }

    /**
     * ActionListener implementation for puzzle buttons.
     * Handles the puzzle button clicks and manages the selection and swapping of puzzles.
     */
    private class CheckButtonListener implements ActionListener {
        /**
         * Handles the actionPerformed event when a puzzle button is clicked.
         * If no puzzle is selected, sets the current puzzle as the first selected puzzle and highlights it.
         * If one puzzle is already selected, sets the current puzzle as the second selected puzzle,
         * swaps the puzzles, clears the selection, and updates the button icons.
         *
         * @param e The ActionEvent representing the button click.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            BufferedImage ourImage = ImageManager.collectImages(puzzles);
            Puzzle[] puzzlesCopy = puzzles;
            puzzlesCopy = ImageManager.fillCompares(puzzles);
            try {
                puzzlesCopy = ImageManager.sortPuzzle(puzzlesCopy);
            } catch (NullPointerException exception) {
                JOptionPane.showMessageDialog(PuzzleApp.this, "Unfortunately, the algorithm cannot check this photo(\nNext time try to assemble another puzzle");
                dispose();
                System.exit(0);
            }

            BufferedImage collected = ImageManager.collectImages(puzzlesCopy);
            if (ImageManager.compareImages(ourImage, collected)) {
                JOptionPane.showMessageDialog(PuzzleApp.this, "Puzzles have been collected successfully!");
                System.exit(0);
            } else {
                JOptionPane.showMessageDialog(PuzzleApp.this, "The puzzles are assembled incorrectly(((\nContinue...");
            }
        }
    }

    /**
     * Swaps the positions of the first selected puzzle and the second selected puzzle.
     * Updates the puzzle array and button icons accordingly.
     */
    private void swapPuzzles() {
        Puzzle temp = puzzles[firstSelectedIndex];
        puzzles[firstSelectedIndex] = puzzles[secondSelectedIndex];
        puzzles[secondSelectedIndex] = temp;

        BufferedImage firstImage = puzzles[firstSelectedIndex].getImage();
        BufferedImage secondImage = puzzles[secondSelectedIndex].getImage();

        puzzleButtons[firstSelectedIndex].setIcon(new ImageIcon(firstImage));
        puzzleButtons[secondSelectedIndex].setIcon(new ImageIcon(secondImage));
    }

    private void clearSelection() {
        puzzleButtons[firstSelectedIndex].setBorder(null);
        puzzleButtons[secondSelectedIndex].setBorder(null);
        firstSelectedIndex = -1;
        secondSelectedIndex = -1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PuzzleApp app = null;
                try {
                    app = new PuzzleApp();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                app.setVisible(true);
            }
        });
    }
}
