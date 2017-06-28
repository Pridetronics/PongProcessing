import processing.core.*;

public class PaddleRandom extends Paddle {

  int count;
  int upORdown;

  PaddleRandom( PApplet parent, 
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
    upORdown = 0;
  }

  // tell paddle where the ball is, update paddle position
  public void update ( int ballX, int ballY ) {

    if ( count++ % 10 == 0 ) {
      double d = Math.random();
      if ( d < 0.4 ) {
        upORdown = -1;
      } else if ( d > 0.6 ) {
        upORdown = 1;
      } else {
        upORdown = 0;
      }
    }

    //move paddle
    super.move( upORdown );
//    System.out.println( " PaddleRandom  " + upORdown );
  }  //  end update

}  //  end class PaddleRandom