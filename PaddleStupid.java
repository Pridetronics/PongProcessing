import processing.core.*;

public class PaddleStupid extends Paddle {

  int count;
  int upORdown;

  PaddleStupid( PApplet parent, 
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

    if ( count++ % 10 == 0 ) {
      if ( y <= half(sizeY) ) {
        upORdown = 1;
      } else if ( y >= maxY - sizeY ) {
        upORdown = -1;
      }
    }

    //move paddle
    super.move( upORdown );
//    System.out.println( " PaddleRandom  " + upORdown );
  }  //  end update

}  //  end class PaddleStupid