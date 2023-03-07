package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity {

    boolean flag = false;
    char filledPositions[] = new char[9];
    int winningIndex1,winningIndex2,winningIndex3;
    boolean isWinnerDecided;
    ImageView winningLines[] = new ImageView[16];

    public void fillBoard(String move){
        int moveIndex = (move.charAt(move.length()-1)-1-48);
        Log.i("position","position: "+ moveIndex);
        if( flag == false)
            filledPositions[moveIndex] = 'X';
        else filledPositions[moveIndex] = 'O';
    }

    public void checkResult(){
        char turn = 'X',winner='_';
        int lineNumber = -1;
        if(flag) turn='O';

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

        /*
        if(winner=='_') Log.i("game status","winner undecided");
        else Log.i("game status","winner: "+winner);
        */

        if(winner != '_'){
            isWinnerDecided=true;
            String buttonName;
            Log.i("winning index",winningIndex1+" "+winningIndex2+" "+winningIndex3);
            for( int i=0;i<9;i++ ){
                buttonName = "boardTile"+(i+1);
                int id = getResources().getIdentifier(buttonName,"id",getPackageName());
                ImageView image = findViewById(id);
                image.setClickable(false);
                if( i != winningIndex1 && i != winningIndex2 && i != winningIndex3) {
                    image.animate().alpha(0.2F).scaleXBy(-0.1F).scaleYBy(-0.1F).setDuration(200);
                }
                //Log.i("ids: tile"+i,Integer.toString(id));
            }
            if(turn == 'X')
                winningLines[lineNumber].animate().alpha(1).setDuration(250);
            else
                winningLines[8+lineNumber].animate().alpha(1).setDuration(250);
        }

    }
    public void boardEntry(View view){

        ImageView image = (ImageView) view;
        view.setClickable(false);
        if( flag==false ){
            image.setImageResource(R.drawable.xmark);
            fillBoard(getResources().getResourceName(view.getId()));
            flag = true;
        }
        else{
            image.setImageResource(R.drawable.omark);
            fillBoard(getResources().getResourceName(view.getId()));
            flag = false;
        }


        checkResult();
    }

    public void resetGame(View view){
        for( int i=0;i<9;i++ ) {
            String buttonName = "boardTile" + (i + 1);
            int id = getResources().getIdentifier(buttonName, "id", getPackageName());
            ImageView image = findViewById(id);
            image.setImageResource(0);
            image.setClickable(true);
            if( i != winningIndex1 && i != winningIndex2 && i != winningIndex3 && isWinnerDecided==true) {
                image.animate().alpha(0.2F).scaleXBy(0.1F).scaleYBy(0.1F);
            }
            image.animate().alpha(1);
        }
        for( int i=0;i<16;i++ )
            winningLines[i].animate().alpha(0);
        for( int i=0;i<9;i++ ) filledPositions[i] = '_';
        flag = false;
        isWinnerDecided = true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for( int i=0;i<9;i++ ) filledPositions[i] = '_';
        winningIndex1 = -1;
        winningIndex2 = -1;
        winningIndex3 = -1;
        TableLayout gameGrid = findViewById(R.id.gameGrid);
        ImageView resetButton = findViewById(R.id.restartButton);
        //gameGrid.animate().alpha(0);
        //resetButton.animate().alpha(0);
        ImageView logo = findViewById(R.id.gameLogo);
        logo.animate().rotation(1080).setDuration(1000).alphaBy(-1F);
        gameGrid.animate().alpha(1).setDuration(3000);
        isWinnerDecided = false;
        winningLines[0] = findViewById(R.id.diagonalBlue);
        winningLines[1] = findViewById(R.id.diagonalOppositeBlue);
        winningLines[2] = findViewById(R.id.upperLineBlue);
        winningLines[3] = findViewById(R.id.middleLineBlue);
        winningLines[4] = findViewById(R.id.lowerLineBlue);
        winningLines[5] = findViewById(R.id.horizontalLeftLineBlue);
        winningLines[6] = findViewById(R.id.horizontalMiddleLineBlue);
        winningLines[7] = findViewById(R.id.horizontalRightLineBlue);
        winningLines[8+0] = findViewById(R.id.diagonalRed);
        winningLines[8+1] = findViewById(R.id.diagonalOppositeRed);
        winningLines[8+2] = findViewById(R.id.upperLineRed);
        winningLines[8+3] = findViewById(R.id.middleLineRed);
        winningLines[8+4] = findViewById(R.id.lowerLineRed);
        winningLines[8+5] = findViewById(R.id.horizontalLeftLineRed);
        winningLines[8+6] = findViewById(R.id.horizontalMiddleLineRed);
        winningLines[8+7] = findViewById(R.id.horizontalRightLineRed);
    }
}