import processing.core.*;

public class PaddleMatchY extends Paddle {

  int count;
  int upORdown;

  PaddleMatchY( PApplet parent, 
    boolean playLeft, 
    int maxX, int maxY, 
    int sizeX, int sizeY,
    int speed
    ) {
    super( parent, playLeft, maxX, maxY, sizeX, sizeY, speed );  //initialize superclass member
    reset();
  }  //  end paddle ctor

  // reset the position
  public void reset() {
    super.reset();
    count = 0;
    upORdown = 1;
  }

  // tell paddle where the ball is, update paddle position
  public void update ( int ballX, int ballY ) {

    if ( y == ballY ) {
      upORdown = 0;
    } else if ( y < ballY ) {
      upORdown = 1;
    } else {
      upORdown = -1;
    }
    //move paddle
    super.move( upORdown );
//    System.out.println( " PaddleMatchY  " + upORdown );
  }  //  end update

}  //  end class PaddleMatchY