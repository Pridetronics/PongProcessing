////////////////////////////////////////////////////////////////////////////// //<>// //<>//

final int MaxX = 640;               // table width //<>// //<>// //<>// //<>//
final int MaxY = MaxX/2;            // table height

final int GameOver = 7;             // winner is first to this score

PShape ball;
int bX, bY, dirX, dirY;
int serveLeft, serveRight;
int tableCrossCount;

// size the paddles individually; as the score goes up, shrink them
final int PaddleWidthLeft = 8;
final int PaddleHeightLeft = 32;
final int PaddleWidthRight = 8;
final int PaddleHeightRight = 32;

// angle for the ball launch. Constrained to +- AngleRange/2
float angle;
final float AngleRange = 150.0;
final float AngleOffset = -AngleRange/2.0;

int wait;
double speed;

Paddle leftPaddle;
Paddle rightPaddle;

////////////////////////////////////////////////////////////
void settings() {
  size( MaxX, MaxY );
}

////////////////////////////////////////////////////////////
void setup() {
  frameRate( 40 );  // update rate, frames per second

  ball = createShape( ELLIPSE, 0, 0, 10, 10 );
  ball.beginShape();
  ball.fill( 255, 255, 255 );
  ball.endShape(CLOSE);

  bX = -10; 
  bY = 155; 
  dirX = 1; 
  dirY = 1;
  serveLeft = 0;
  serveRight = 0;
  wait = 10; 
  speed = 20;
  angle = newAngle();

//  rightPaddle = new PaddleStupid( this, false, MaxX, MaxY, PaddleWidthRight, PaddleHeightRight, 6 );
//  rightPaddle = new PaddleRandom( this, false, MaxX, MaxY, PaddleWidthRight, PaddleHeightRight, 6 );
  rightPaddle = new PaddleMatchY( this, false, MaxX, MaxY, PaddleWidthRight, PaddleHeightRight, 8 );
//  rightPaddle = new Paddle1( this, false, MaxX, MaxY, PaddleWidthRight, PaddleHeightRight, 8 );
//  leftPaddle = new PaddleStupid( this, true, MaxX, MaxY, PaddleWidthLeft, PaddleHeightLeft, 8 );
//  leftPaddle = new PaddleRandom( this, true, MaxX, MaxY, PaddleWidthLeft, PaddleHeightLeft, 8 );
//  leftPaddle = new PaddleMatchY( this, true, MaxX, MaxY, PaddleWidthLeft, PaddleHeightLeft, 8 );
  leftPaddle = new Paddle1( this, true, MaxX, MaxY, PaddleWidthLeft, PaddleHeightLeft, 8 );
}

////////////////////////////////////////////////////////////
void draw() {
  if ( leftPaddle.getScore() >= GameOver ) {
    textSize( 64 );
    text( "LEFT WINS", Paddle.half(Paddle.half(MaxX)), Paddle.half(MaxY) );
    // post the score to the field
    postScore( 32, Paddle.half(MaxY)+Paddle.half(Paddle.half(MaxY)) );
    return;
  } else if ( rightPaddle.getScore() >= GameOver ) {
    textSize( 64 );
    text( "RIGHT WINS", Paddle.half(Paddle.half(MaxX)), Paddle.half(MaxY) );
    // post the score to the field
    postScore( 32, Paddle.half(MaxY)+Paddle.half(Paddle.half(MaxY)) );
    return;
  }

  background( 85, 167, 103 );
  wait++;
  if ( wait > 25 ) {
    if ( moveBall() || tableCrossCount > 10 ) {
      // move Ball returns true when the ball gets past a paddle
      // reset the game, pick a new angle
      wait = 0;
      tableCrossCount = 0;
      angle = newAngle();
      println( "angle = ", degrees(angle) );
      leftPaddle.reset();
      rightPaddle.reset();
    }
    rightPaddle.update( bX, bY );
    leftPaddle.update( bX, bY );
  }
  leftPaddle.display();
  rightPaddle.display();
  //  System.out.println( "left " + leftPaddle.getScore()
  //    + "  right " + rightPaddle.getScore() );

  // post the score to the field
  postScore( 32, Paddle.half(MaxY) );
}  //  end draw

////////////////////////////////////////////////////////////
boolean moveBall () {
  boolean scored = false;
  int hrp=0, hlp=0;
  double dX = speed*dirX*Math.abs(Math.cos(angle));
  bX += dX;
  double dY = speed*dirY*Math.abs(Math.sin(angle));
  bY += dY;
  //  println( angle, "  X", bX, "  ", dX, "  Y ", bY, " ", dY );
  if ( (hrp = rightPaddle.hitPaddle( bX, bY, dirX )) == -1 ) {
    // right paddle missed, left scores
    leftPaddle.score();
    scored = true;
    rightPaddle.resetServe();
    bX = 0;
    dirX = 1;
  } else if ( (hlp = leftPaddle.hitPaddle( bX, bY, dirX )) == -1 ) {
    // left paddle missed, right scores
    rightPaddle.score();
    scored = true;
    leftPaddle.resetServe();
    bX = MaxX;
    dirX = -1;
  } else {
    if ( hrp != 0 ) {
      bX = MaxX;
      dirX = -1;
      tableCrossCount++;
    } else if ( hlp != 0 ) {
      bX = 0;
      dirX = 1;
      tableCrossCount++;
    }
  }

  // bounce the ball off of the sides
  if ( bY <= 0 ) {
    bY = 0; 
    dirY = 1;
  } else if ( bY >= MaxY ) {
    bY = MaxY; 
    dirY = -1;
  }
  fill( 255, 255, 255 );
  ellipse( bX, bY, 10, 10 );
  return scored;
}  // end moveBall

////////////////////////////////////////////////////////////
float newAngle() {
  return radians((float)Math.random()*AngleRange + AngleOffset);
}

////////////////////////////////////////////////////////////
void postScore ( int textSize, int y ) {
  textSize(32);
  fill( 0, 0, 255 );
  text(
    Integer.toString(leftPaddle.getScore()), 
    Paddle.half(Paddle.half(MaxX)), 
    y
    );
  text(
    Integer.toString(rightPaddle.getScore()), 
    Paddle.half(Paddle.half(MaxX))+Paddle.half(MaxX), 
    y
    );
}  // end postScore