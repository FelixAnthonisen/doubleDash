package no.uib.inf101.sem2;

import no.uib.inf101.sem2.model.GameModel;
import no.uib.inf101.sem2.view.GameView;
import no.uib.inf101.sem2.controller.Controller;

import javax.swing.JFrame;

public class Main {

  public static final int WIDTH = 630, HEIGHT = 840;

  public static void main(String[] args) {
    GameModel model = new GameModel();
    GameView view = new GameView(WIDTH, HEIGHT, model);
    @SuppressWarnings("unused")
    Controller controller = new Controller(model, view);

    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setTitle("Platformer");
    frame.setContentPane(view);
    frame.pack();
    frame.setVisible(true);
  }
}
