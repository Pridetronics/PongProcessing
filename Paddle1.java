import processing.core.*;

public class Paddle1 extends Paddle {

  private int lastPaddleY;
  private int lastBallX, lastBallY;

  Paddle1( PApplet parent, 
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
    lastPaddleY = super.y;
    lastBallX = -1;
    lastBallY = -1;
  }
  
  // tell paddle where the ball is, update paddle position
  public void update ( int ballX, int ballY ) {
    int upORdown = 0;  // direction to move

    if ( lastBallX > -1 ) {  // skip first sample

      // how has the ball moved?  
      int dX = lastBallX - ballX;
      int dY = lastBallY - ballY;
    
      if ( ( ballX > x && dX > 0 ) ||
           ( ballX < x && dX < 0 ) ) {
        // calculate Y intercept
        // Y = m*X + b; m = dY / dX;
        int b;
        b = ballY - (dY/dX)*ballX;

        // will ball hit paddle?
        int diffY = b - super.y;
        System.out.println( "diffY " + diffY + " " + super.half(sizeY) );
        if ( Math.abs(diffY) < super.half(sizeY) ) {
          upORdown = 0;  // should hit paddle
        } else if ( diffY > 0 ) {
          upORdown = 1;  // no, move up
        } else {
          upORdown = -1;  // no. move down
        }

        //move paddle
        super.move( upORdown );
        System.out.println( ballY + " " + y + " " + upORdown + " " + b );
      }
    }
    lastBallX = ballX;
    lastBallY = ballY;
    move( upORdown );
  }  //  end update

}  //  end class Paddle1