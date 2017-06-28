import processing.core.*; //<>// //<>// //<>// //<>//

public abstract class Paddle {

  // superclass for Pong paddles

  // this enables Paddle class objects to play nice in Processing
  protected PApplet parent;

  protected int x, y; // current paddle position
  protected int maxX, maxY;  // size of table
  protected int sizeX, sizeY; // size of paddle
  protected boolean leftSide;  // true play left paddle, false play right
  private int speed;
  private int score;
  private int serves;  // keep track of consequitive serves
  
  ////////////////////////////////////////////////////////////
  Paddle( PApplet parent, 
    boolean playLeft, 
    int maxX, int maxY, 
    int sizeX, int sizeY, 
    int speed
    ) {
    this.parent = parent;
    this.maxX = maxX;
    this.maxY = maxY;
    this.sizeX = sizeX;
    this.sizeY = sizeY;
    this.speed = speed;
    leftSide = playLeft;
    score = 0;
    serves = 0;
    reset();
  }  //  end paddle ctor

  ////////////////////////////////////////////////////////////
  // reset the position
  public void reset() {
    if ( leftSide ) {
      x = 0 + half(sizeX);
    } else {
      x = maxX - half(sizeX);
    }
    y = half(maxY);
  }

  // tell paddle where the ball is, update paddle position
  public abstract void update ( int ballX, int ballY );

  ////////////////////////////////////////////////////////////
  // draw the paddle at the current position
  public void display() {
    parent.rectMode( parent.CENTER );
    parent.fill( 255, 0, 0 );
    parent.rect( x, y, sizeX, sizeY );
    System.out.println( "display " + x + " " + y + " " + sizeX + " " + sizeY );
  }  //  end display

  ////////////////////////////////////////////////////////////
  public void move( int upORdown ) {
    y = y + speed * upORdown;
    if ( y < half(sizeY) ) {
      y = half(sizeY);
    } else if ( y > maxY - half(sizeY) ) {
      y = maxY - half(sizeY);
    }
  }  //  end move

  ////////////////////////////////////////////////////////////
  // where's the paddle?
  public boolean inPaddleYRange( int ballY ) {
    int deltaY = ballY - y;
    System.out.println( "IPR  y " + y + "  ballY " + ballY);
    if ( Math.abs(deltaY) <= half(sizeY) ) {
      return true;
    }
    return false;
  }  //  end inPaddleRange

  ////////////////////////////////////////////////////////////
  public int hitPaddle( int ballX, int ballY, int dirX ) {
    // need the ball direction (dirX) to not screw up serves
    if ( x < half(maxX) ) {
      if ( ballX <= sizeX && dirX == -1 ) {
        System.out.println("hitPaddle left " + y +"  " + ballY);
        if ( inPaddleYRange( ballY ) ) {
          return 1;
        } else {
          return -1;
        }
      }
    } else {
      if ( ballX >= maxX - sizeX && dirX == 1 ) {
        System.out.println("hitPaddle right " + y +"  " + ballY);
        if ( inPaddleYRange( ballY ) ) {
          return 1;
        } else {
          return -1;
        }
      }
    }
    return 0;
  }  //  end hitPaddle


  ////////////////////////////////////////////////////////////
  // discrete math helper
  public static int half( int z ) {
    return (int) ((float)z/2.0 + 0.5);
  }

  ////////////////////////////////////////////////////////////
  public int score() {
    ++serves;  // count serves
    if ( serves > 1 ) {
      ++score;
    }
    return score; //<>//
  }
  public int getScore() {
    return score;
  }
  public void resetServe() {
    serves = 0;
  }
}  //  end class Paddle