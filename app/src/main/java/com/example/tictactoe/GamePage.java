package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;


public class GamePage extends AppCompatActivity {


    boolean flag = false;
    boolean playRestartFlag = false;
    //false: play, true: restart
    char filledPositions[] = new char[9];
    int winningIndex1,winningIndex2,winningIndex3;
    boolean isWinnerDecided;
    int moveCounter;
    ImageView winningLines[] = new ImageView[16];


    public void initialize(){
        moveCounter = 0;
        for( int i=0;i<9;i++ ) filledPositions[i] = '_';
        winningIndex1 = -1;
        winningIndex2 = -1;
        winningIndex3 = -1;
        isWinnerDecided = false;

        winningLines[2]=findViewById(R.id.horizontalUpperLineBlue);
        winningLines[3]=findViewById(R.id.horizontalMiddleLineBlue);
        winningLines[4]=findViewById(R.id.horizontalLowerLineBlue);
        winningLines[5]=findViewById(R.id.verticalLeftLineBlue);
        winningLines[6]=findViewById(R.id.verticalMiddleLineBlue);
        winningLines[7]=findViewById(R.id.verticalRightLineBlue);
        winningLines[0]=findViewById(R.id.diagonalLineBlue);
        winningLines[1]=findViewById(R.id.reverseDiagonalLineBlue);

        for( int i=0;i<8;i++) winningLines[i].animate().alpha(0);

        ImageView drawMessage = findViewById(R.id.drawMessage);
        drawMessage.animate().alpha(0);
    }

    public void fillBoard(char ch){
        int moveIndex = (int)ch - 48-1;
        if( flag == false)
            filledPositions[moveIndex] = 'X';
        else filledPositions[moveIndex] = 'O';
        String display = "";
        for( char x: filledPositions)
            display += x;
        Log.i("filled positions",display);
    }


    public void checkResult(){
        char turn = 'X',winner='_';
        int lineNumber = -1;
        if(flag) turn='O';
        ++moveCounter;

        winningIndex1 = -1;
        winningIndex2 = -1;
        winningIndex3 = -1;

        if( filledPositions[0]!='_' && filledPositions[0]==filledPositions[4] && filledPositions[4]==filledPositions[8]) {
            winner = filledPositions[0];
            winningIndex1 = 0;
            winningIndex2 = 4;
            winningIndex3 = 8;
            lineNumber=0;
        }
        else if( filledPositions[2]!='_' && filledPositions[2]==filledPositions[4] && filledPositions[4]==filledPositions[6]) {
            winner = filledPositions[2];
            winningIndex1 = 2;
            winningIndex2 = 4;
            winningIndex3 = 6;
            lineNumber=1;
        }
        else{
            lineNumber=2;
            for( int i=0;i<=6;i+=3){
                if( filledPositions[i]!='_' && filledPositions[i]==filledPositions[i+1] && filledPositions[i+1]==filledPositions[i+2]) {
                    winner = filledPositions[i];
                    winningIndex1 = i;
                    winningIndex2 = i+1;
                    winningIndex3 = i+2;
                    break;
                }
                ++lineNumber;
            }
            if( lineNumber == 5 ){
                lineNumber = 5;
                for( int i=0;i<=2;i++){
                    if( filledPositions[i]!='_' && filledPositions[i]==filledPositions[i+3] && filledPositions[i+3]==filledPositions[i+6]) {
                        winner = filledPositions[i];
                        winningIndex1 = i;
                        winningIndex2 = i+3;
                        winningIndex3 = i+6;
                        break;
                    }
                    ++lineNumber;
                }
            }
        }

        if(winner != '_'){
            isWinnerDecided=true;
            String buttonName;
            for( int i=0;i<9;i++ ){
                buttonName = "boardTile"+(i+1);
                int id = getResources().getIdentifier(buttonName,"id",getPackageName());
                ImageView image = findViewById(id);
                image.setClickable(false);
                if( i != winningIndex1 && i != winningIndex2 && i != winningIndex3) {
                    image.animate().alpha(0.2F).setDuration(200);
                }
            }
            winningLines[lineNumber].animate().alpha(1);
        }
        if(winner == '_' && moveCounter==9){
            String buttonName;
            findViewById(R.id.gameGrid).animate().alpha(0.2F);
            ImageView drawMessage = findViewById(R.id.drawMessage);
            drawMessage.animate().alpha(1).setDuration(250);
        }
    }


    public void boardEntry(View view){

        ImageView image = (ImageView) view;
        String tag = String.valueOf(image.getTag());
        //Log.i("Tile Number",tag.charAt(tag.length()-1)+" ");
        fillBoard(tag.charAt(tag.length()-1));
        view.setClickable(false);
        if(!flag){
            image.setImageResource(R.drawable.xmark);
            flag = true;
        }
        else{
            image.setImageResource(R.drawable.omark);
            flag = false;
        }
        checkResult();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        initialize();

        for( int i=1;i<=9;i++){
            String buttonName = "boardTile" + i;
            int id = getResources().getIdentifier(buttonName, "id", getPackageName());
            ImageView image = findViewById(id);
            image.setImageResource(0);
            image.animate().alpha(1);
        }

        ImageView homeButton = findViewById(R.id.homeButton);
        ImageView resetGame = findViewById(R.id.resetButton);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(GamePage.this,MainActivity.class);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                startActivity(intent);
            }
        });

        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for( int i=0;i<9;i++ ) {
                    String buttonName = "boardTile" + (i + 1);
                    int id = getResources().getIdentifier(buttonName, "id", getPackageName());
                    ImageView image = findViewById(id);
                    image.setImageResource(0);
                    image.setClickable(true);
                    image.animate().alpha(1);
                }
                    for( int i=0;i<8;i++ )
                        winningLines[i].animate().alpha(0);
                    for( int i=0;i<9;i++ ) filledPositions[i] = '_';
                    flag = false;
                    isWinnerDecided = false;
                    winningIndex1=-1;
                    winningIndex2=-1;
                    winningIndex3=-1;
                    moveCounter=0;
                    findViewById(R.id.drawMessage).animate().alpha(0);
                    findViewById(R.id.gameGrid).animate().alpha(1);
            }
        });

    }
}