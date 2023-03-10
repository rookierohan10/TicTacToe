package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

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
    ImageView winningLines[] = new ImageView[16];

    public void setTilesInactive(){
        boolean flag=playRestartFlag;
        for( int i=1;i<=9;i++ ){
            String buttonName = "boardTile"+(i);
            int id = getResources().getIdentifier(buttonName,"id",getPackageName());
            findViewById(id).setClickable(flag);
        }
    }


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
                winningLines[lineNumber].animate().alpha(1);
            else
                winningLines[8+lineNumber].animate().alpha(1);
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

    public void playButtonAction(){

        TableLayout gameGrid = findViewById(R.id.gameGrid);
        ImageView playButton = findViewById(R.id.playButton);
        ImageView homeButton = findViewById(R.id.restartButton);
        ImageView logo = findViewById(R.id.gameLogo);

        logo.animate().setDuration(1000).translationYBy(-650).scaleY(0.7f).scaleX(0.7f);
        playButton.animate().setDuration(500).rotation(-360).translationXBy(-250);
        playButton.setImageResource(R.drawable.restartbutton);
        homeButton.animate().setDuration(500).rotation(360).translationXBy(250).alpha(1);
        gameGrid.animate().alpha(1).setDuration(1500).setStartDelay(550);
        homeButton.setClickable(true);
    }

    public void resetGame(){
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
        isWinnerDecided = false;
        winningIndex1=-1;
        winningIndex2=-1;
        winningIndex3=-1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);
        for( int i=0;i<9;i++ ) filledPositions[i] = '_';
        winningIndex1 = -1;
        winningIndex2 = -1;
        winningIndex3 = -1;

        isWinnerDecided = false;

        setTilesInactive();
        winningLines[0] = findViewById(R.id.diagonalBlue3);
        winningLines[1] = findViewById(R.id.diagonalOppositeBlue3);
        winningLines[2] = findViewById(R.id.upperLineBlue3);
        winningLines[3] = findViewById(R.id.middleLineBlue3);
        winningLines[4] = findViewById(R.id.lowerLineBlue3);
        winningLines[5] = findViewById(R.id.horizontalLeftLineBlue3);
        winningLines[6] = findViewById(R.id.horizontalMiddleLineBlue3);
        winningLines[7] = findViewById(R.id.horizontalRightLineBlue3);
        winningLines[8+0] = findViewById(R.id.diagonalRed3);
        winningLines[8+1] = findViewById(R.id.diagonalOppositeRed3);
        winningLines[8+2] = findViewById(R.id.upperLineRed3);
        winningLines[8+3] = findViewById(R.id.middleLineRed3);
        winningLines[8+4] = findViewById(R.id.lowerLineRed3);
        winningLines[8+5] = findViewById(R.id.horizontalLeftLineRed3);
        winningLines[8+6] = findViewById(R.id.horizontalMiddleLineRed3);
        winningLines[8+7] = findViewById(R.id.horizontalRightLineRed3);
    }
}