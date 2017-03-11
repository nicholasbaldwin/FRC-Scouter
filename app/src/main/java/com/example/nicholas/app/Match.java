package com.example.nicholas.app;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;

public class Match implements Serializable
{

/*Written By Nicholas Baldwin

General Method Information:

Methods will have default variables, sets, and gets. Objects based on team number will be created/deleted on user's will

Other methods: Match will have a method which will COMPRESS the variable contents of the object into a short String meant for communication purposes. String Data will be placed in the XML
*/



/*INSTANCE VARIABLES*/
 boolean fuelCollectionGround;
 boolean fuelCollectionHopper;
boolean climbSuccessful;

    public void setClimbSuccessful(boolean climbSuccessful) {
        this.climbSuccessful = climbSuccessful;
    }

    public boolean getClimbSuccessful(){
       return climbSuccessful;
    }

    public void setFuelCollectionGround(boolean fuelCollectionGround) {
        this.fuelCollectionGround = fuelCollectionGround;
    }

    public void setFuelCollectionHopper(boolean fuelCollectionHopper) {
        this.fuelCollectionHopper = fuelCollectionHopper;
    }
    public boolean getFuelCollectionGround(){
        return fuelCollectionGround;
    }
    public boolean getFuelCollectionHopper(){
        return fuelCollectionHopper;
    }
    boolean autonomousHopper;
boolean autonomousBaseline;
int tGearSuccess;
int tGearFail;
int tHighGoalScore;
int tLowGoalScore;
int tHighCycles;
int tLowCycles;

int teamNumber = 0;
 int matchNumber=0;
boolean defensive;

    public int getTGearSuccess() {
        return tGearSuccess;
    }
    public int getTGearFail()
    {
        return tGearFail;
    }
    public int getTHighGoalScore()
    {
        return tHighGoalScore;
    }
    public int getTLowGoalScore()
    {
        return tLowGoalScore;
    }
    public int getTHighCycles()
    {
      return tHighCycles;
    }
    public int getTLowCycles()
    {
      return tLowCycles;
    }
    public void setTGearSuccess(int tGearSuccess){this.tGearSuccess= tGearSuccess;}

    public void setTGearFail(int tGearFail) {
        this.tGearFail = tGearFail;
    }

    public void setTHighGoalScore(int tHighGoalScore) {
        this.tHighGoalScore = tHighGoalScore;
    }

    public void setTLowGoalScore(int tLowGoalScore) {
        this.tLowGoalScore = tLowGoalScore;
    }

    public void setTHighCycles(int tHighCycles) {
        this.tHighCycles = tHighCycles;
    }

    public void setTLowCycles(int tLowCycles) {
        this.tLowCycles = tLowCycles;
    }

    public void setMatchNumber(int matchNumber) {
        this.matchNumber = matchNumber;
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }
    public int getTeamNumber(){return teamNumber;}
int highGoalScore;

    public int getHighGoalScore() {
        if(highGoalScore>3){highGoalScore=3;}
        return highGoalScore;
    }

    int lowGoalScore;

    public int getLowGoalScore() {  if(lowGoalScore>3){lowGoalScore=3;}
        return lowGoalScore;
    }

    int gearSucessNbr;

    public int getGearSuccessNbr() {
        return gearSucessNbr;
    }

    int gearFailNbr;

    public int getGearFailNbr() {
        return gearFailNbr;
    }

    int highGoalAccuracyScore;

    public int getHighGoalAccuracyScore() {
        return highGoalAccuracyScore;
    }

    int lowGoalAccuracyScore;

    public int getLowGoalAccuracyScore() {
        return lowGoalAccuracyScore;
    }

    boolean didClimb;

boolean didFunction;
    public boolean getDidFunction(boolean didFunction)
    {return didFunction;}
boolean didShow;
    public boolean getDidShow(boolean didShow)
    {return didShow;}
boolean stopFunction;

    public boolean getStopFunction(boolean stopFunction)
    {return stopFunction;}
String notes;
    public void setNotes(String notes){this.notes = notes;}
    public String getNotes() {
        return notes;
    }
/*SETS AND GETS*/


    public void setAutonomousHopper(boolean autonomousHopper) {
        this.autonomousHopper = autonomousHopper;
    }
    public void setAutonomousBaseline(boolean autonomousBaseline) {
        this.autonomousBaseline = autonomousBaseline;
    }
    public boolean getAutonomousHopper(){return autonomousHopper;}

    public boolean getAutonomousBaseline(){return autonomousBaseline;}
    public void setHighGoalScore(int newScore) {
        highGoalScore = newScore;
    }

    public void setLowGoalScore(int newScore) {
        lowGoalScore = newScore;
    }

    public void setGearSuccessNbr(int newScore) {
        gearSucessNbr = newScore;
    }

    public void setGearFailNbr(int newScore) {
        gearFailNbr = newScore;
    }

    public void setHighGoalAccuracyScore(int newScore){highGoalAccuracyScore = newScore;}

    public void setLowGoalAccuracyScore(int newScore){lowGoalAccuracyScore = newScore;}

    public void setDidClimb(boolean newBool){didClimb = newBool;}
    public boolean getDidClimb(){return didClimb;}
    public boolean getDefensive(){return defensive; }
    public void setDefensive(boolean defensive){this.defensive = defensive;}
    public void setDidFunction(boolean newBool){didFunction = newBool;}
    public boolean getDidFunction()
    {return didFunction;}
  public boolean getStopFunction(){return stopFunction;}
    public void setDidShow(boolean newBool){didShow = newBool;}

    public void setStopFunction(boolean newBool){stopFunction = newBool;}

@Override
    public String toString()
     {
     TransferCode t = new TransferCode();

     //Autonomous Gears Fail/Success
     t.setAutoGearsFail(getGearFailNbr());
     t.setAutoGearsSuccess(getGearSuccessNbr());

     //TOP and A Booleans
     t.setTeleOpFuelGround(getFuelCollectionGround() ? 1 : 0);
     t.setTeleOpFuelHopper(getFuelCollectionHopper() ? 1 : 0);
     t.setAutoHopper(getAutonomousHopper() ? 1:0);
     t.setBaseLine(getAutonomousBaseline() ? 1:0);

     //Tele-Op Gears Failures/Successes
     t.setTeleOpGearFailedCnt(getTGearFail());
     t.setTeleOpGearsCnt(getTGearSuccess());

     //High/Low Cycles
     t.setHighGoalCycles(getTHighCycles());
     t.setLowGoalCycles(getTLowCycles());

     //Accuracies
     t.setAutoHighGoalAccuracy(getHighGoalScore());
     t.setAutoLowGoalAccuracy(getLowGoalScore());
     t.setTeleOpHighGoalAccuracy(getTHighGoalScore());
     t.setTeleOpLowGoalAccuracy(getTLowGoalScore());

     //EndGameBooleans
     t.setCanClimb(getDidClimb());
     t.setClimbStatus(getClimbSuccessful());

     t.setDefensiveRobot(getDefensive());
     t.setRobotDidNotStart(getDidFunction());
     t.setRobotStoppedWorking(getStopFunction());

     //Basic Values
     t.setTeamNumber(getTeamNumber());
     t.setMatchNumber(getMatchNumber());

     String code = t.GenerateCode();
     return code;
     }

}
