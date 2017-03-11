package com.example.nicholas.app;

/**
 * 
 * @author william hoffman
 * @version 2-28-2017
 */
public class TransferCode {
    private int matchNumber = 1;//7 bits: bits0-6; 8 bits
    private int baseLine = 0;//1 bit short baseLine = 0; //1 bit
    private int autoGearsSuccess = 0; //2 bits, cnt 0-3
    
    private int teamNumber = 0; //14bits ; 16 bits
    private int autoGearsFail = 0; //1 bits, cnt 

    private int autoHighGoalAccuracy = 0;//2
    private int autoLowGoalAccuracy = 0;//2
    private int autoHopper = 0;//1 bit

    
    private int teleOpHighGoalAccuracy = 0;//2
    private int teleOpLowGoalAccuracy = 0;//2
    private int teleOpGearShoot = 0;//1
    
    private int teleOpGearsCnt = 0;//4 bits 
    private int teleOpGearGround = 0;//1
    
    private int teleOpGearFailedCnt = 0;//4 bits
    private int teleOpFuelHopper = 0; //1 bit
    
    private int teleOpFuelGround = 0;//1
    private int climbStatus =0; //1 bit
    private int canClimb = 0; //1 bits 
    private int comments = 0; //2 bit
    private int robotStoppedWorking = 0;
    private int defensiveRobot = 0;
    private int robotDidNotStart = 0;
    private int lowGoalCycles=0;//added 2-21-17
    private int highGoalCycles=0;//added 2-21-17
    
    String[] map = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "O", "P", "R", "S", "T", "U", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	int bitmask1 = 0x0001;
	int bitmask2 = 0x0003;
	int bitmask3 = 0x0007;
	int bitmask4 = 0x000F;
	int bitmask5 = 0x001F;
	int bitmask45 = 0x0018;
	int bitmask23 = 0x006;
	int bitmaskonly3= 0x0008;
    public TransferCode()
    {}
    
    //added 2-21-17
	/**
	 * 
	 * @param c How many times the robot loaded and shot for the low goal. range 0-15
	 */
    public void setLowGoalCycles(int c)
    {
        int c1 = rangeCheck(0, 15, c);
        lowGoalCycles = c1;
    }
    public int getLowGoalCycles()
    {
        return lowGoalCycles;
    }
	/**
	 * 
	 * @param c How many times the robot loaded and shot for the high goal. range 0-15
	 */
    public void setHighGoalCycles(int c)
    {
        int c1 = rangeCheck(0, 15, c);
        highGoalCycles = c1;
    }
    public int getHighGoalCycles()
    {
        return highGoalCycles;
    }
    // done - added 2-21-17
    private int getPos(String s)
    {
    	int pos=0;
    	for (int i=0; i<32; i++)
    	{
    		if (s.equals(map[i]))
    		{
    			pos=i;
    			return pos;
    		}
    	}
    	return pos;
    }
    public TransferCode Decode(String code)
    {
    	int pos=0;
    	TransferCode newTC = new TransferCode();
    	try {
    		pos = getPos(code.substring(0,  1));
    		newTC.matchNumber = (pos <<2);
    		pos = getPos(code.substring(1,2));
    		newTC.matchNumber = newTC.matchNumber | ((pos >> 3 )& bitmask2);
    		newTC.baseLine = (pos >>2) & bitmask1;
    		newTC.autoGearsSuccess = pos & bitmask2;
    		
    		pos = getPos(code.substring(2,3));
    		newTC.teamNumber = (pos << 9);
    		pos = getPos(code.substring(3,4));
    		newTC.teamNumber = newTC.teamNumber | (pos << 4);
    		pos = getPos(code.substring(4,5));
    		newTC.teamNumber = newTC.teamNumber | (pos >> 1);
    		newTC.autoGearsFail = pos & bitmask1;
    		
    		pos = getPos(code.substring(5,6));
    		newTC.autoHighGoalAccuracy = pos >>3;
    		newTC.autoLowGoalAccuracy = (pos & bitmask23) >>1;
    		newTC.autoHopper = pos & bitmask1;
    		
    		pos = getPos(code.substring(6,7));
    		newTC.teleOpHighGoalAccuracy = pos >>3;
    		newTC.teleOpLowGoalAccuracy = (pos >>1) & bitmask2;
    		newTC.teleOpGearShoot = pos & bitmask1;
    		
    		pos = getPos(code.substring(7,8));
    		newTC.teleOpGearsCnt = (pos >>1);
    		newTC.teleOpGearGround = pos & bitmask1;
    		
    		pos = getPos(code.substring(8,9));
    		newTC.teleOpGearFailedCnt = pos >>1;
    		newTC.teleOpFuelHopper = pos & bitmask1;
    		
    		pos = getPos(code.substring(9,10));
    		newTC.teleOpFuelGround =pos >>4;
    		newTC.climbStatus= (pos >>3) & bitmask1;
    		newTC.canClimb = (pos >>2) & bitmask1;
    		newTC.comments = pos & bitmask2;
            if (newTC.comments == 1)
                newTC.defensiveRobot++;
            if (newTC.comments == 2)
                newTC.robotStoppedWorking++;
            if (newTC.comments == 3)
                newTC.robotDidNotStart++;
    		pos = getPos(code.substring(10,11));
    		newTC.highGoalCycles = (pos & bitmask4);
    		pos = getPos(code.substring(11,12));
    		newTC.lowGoalCycles = pos & bitmask4;

    	}
    	catch (Exception e)
    	{
    		
    	}
    	finally {
    		
    	}
    	return newTC;
    }
    public String GenerateCode()
    {
    	int index=0;
    	String ret="";
    	try {
    	index = (matchNumber >> 2);
    	ret = map[index];
    	index = ((matchNumber & bitmask2)<< 3)| ((baseLine & bitmask1) << 2) | ((autoGearsSuccess & bitmask2));
    	ret += map[index];
    	index = ((teamNumber >> 9) & bitmask5);
    	ret+= map[index];
    	index = ((teamNumber >> 4) & bitmask5);
    	ret+=map[index];
    	index= ((teamNumber & bitmask4)<<1) | ((autoGearsFail & bitmask1));
    	ret+=map[index];
    	index = ((autoHighGoalAccuracy & bitmask2) <<3) | ((autoLowGoalAccuracy & bitmask2) << 1)| ((autoHopper & bitmask1));
    	   	
    	ret +=map[index];
    	index = ((teleOpHighGoalAccuracy & bitmask2)<<3) | ((teleOpLowGoalAccuracy & bitmask2) << 1) | ((teleOpGearShoot * bitmask1));
    	  	
    	ret+=map[index];
    	index = ((teleOpGearsCnt & bitmask4)<<1) | (teleOpGearGround & bitmask1);
    	ret +=map[index];
    	index = ((teleOpGearFailedCnt & bitmask4)<<1) | (teleOpFuelHopper& bitmask1);
    	ret +=map[index];
    	
    	index = ((teleOpFuelGround & bitmask1) <<4) | ((climbStatus & bitmask1) << 3) | ((canClimb & bitmask1) << 2) | ((comments & bitmask2));
    	ret+=map[index];
    	index = (highGoalCycles) ;
    	ret += map[index];
    	index = (lowGoalCycles);
    	ret += map[index];
    	}
    	catch (Exception e)
    	{
    		throw e;
    	}
    	return ret;
    }
	public static void main(String [] args)
	{
		TransferCode t = new TransferCode();
		t.setAutoGearsFail(1);
		t.setAutoGearsSuccess(2);
		t.setAutoHighGoalAccuracy(3);
		t.setAutoHopper(0);
		t.setAutoLowGoalAccuracy(1);
		t.setBaseLine(1);
		t.setCanClimb(1);
		t.setClimbStatus(1);
		t.setDefensiveRobot(1);
		t.setMatchNumber(34);
		t.setTeamNumber(2134);
		t.setTeleOpFuelGround(1);
		t.setTeleOpGearsCnt(7);
		t.setTeleOpHighGoalAccuracy(3);
		t.setTeleOpLowGoalAccuracy(2);
		t.setTeleOpFuelHopper(1);
		t.setTeleOpGearGround(1);
		t.setTeleOpGearShoot(1);
		t.setHighGoalCycles(6);
		t.setLowGoalCycles(3);
		String s = t.GenerateCode();
		TransferCode tc2 = t.Decode(s);
		boolean b = t.isSame(tc2);
		if (b)
			System.out.println("They are the same");
		System.out.println(s);
	}

	public boolean isSame(TransferCode tc)
	{
		boolean ret=true;
	    if ( matchNumber != tc.getMatchNumber())
	    	ret=false;
	    if (baseLine != tc.getBaseLine())
	    	ret=false;
	    if (autoGearsSuccess!= tc.getAutoGearsSuccess())
	    	ret=false;
	    
	    if (teamNumber != tc.getTeamNumber())
	    	ret=false;
	    if (autoGearsFail != tc.getAutoGearsFail())
	    	ret=false;

	    if (autoHighGoalAccuracy != tc.getAutoHighGoalAccuracy())
	    	ret=false;
	    if (autoLowGoalAccuracy != tc.getAutoLowGoalAccuracy())
	    	ret=false;
	    if (autoHopper != tc.getAutoHopper())
	    	ret=false;

	    
	    if (teleOpHighGoalAccuracy != tc.getTeleOpHighGoalAccuracy())
	    	ret=false;
	    if (teleOpLowGoalAccuracy != tc.getTeleOpLowGoalAccuracy())
	    	ret=false;
	    if (teleOpGearShoot != tc.getTeleOpGearShoot())
	    	ret=false;
	    
	    if (teleOpGearsCnt != tc.getTeleOpGearsCnt())
	    	ret=false;
	    if (teleOpGearGround != tc.getTeleOpGearGround())
	    	ret=false;
	    
	    if (teleOpGearFailedCnt != tc.getTeleOpGearFailedCnt())
	    	ret=false;
	    if ( teleOpFuelHopper != tc.getTeleOpFuelHopper())
	    	ret=false;
	    
	    if (teleOpFuelGround != tc.getTeleOpFuelGround())
	    	ret=false;
	    if (climbStatus != tc.getClimbStatus())
	    	ret=false;
	    if (canClimb != tc.getCanClimb())
	    	ret=false;
	    if ( comments != tc.getComments())
	    	ret=false;
	    if (lowGoalCycles != tc.getLowGoalCycles())
	    	ret=false;
	    if (highGoalCycles != tc.getHighGoalCycles())
	    	ret = false;
	    return ret;
	}
	private int rangeCheck(int low, int high, int val)
	{
		if (val>high)
			val=  high;
		if ((val<low))
			val= low;
		return val;
	}
	
	public int getClimbStatus() {
		return climbStatus;
	}

	/**
	 * 
	 * @param climbStatus - true if the robot successfully climbed the rope
	 */
	public void setClimbStatus(boolean climbStatus) {
		this.climbStatus=0;
		if (climbStatus)
		this.climbStatus = 1;
	}
	/**
	 * 
	 * @param climbStatus 1 if the robot successfully climbed the rope
	 */
	public void setClimbStatus(int climbStatus) {
		climbStatus = rangeCheck(0,1,climbStatus);
		this.climbStatus = climbStatus;
	}
	public int getCanClimb() {
		return canClimb;
	}

	/**
	 * 
	 * @param canClimb true if the robot has the ability to climb the rope
	 */
	public void setCanClimb(boolean canClimb) {
		this.canClimb=0;
		if (canClimb)
			this.canClimb = 1;
	}
	/**
	 * 
	 * @param canClimb 1 if the robot has the ability to clime the rope
	 */
	public void setCanClimb(int canClimb) {
		canClimb=rangeCheck(0,1,canClimb);
		this.canClimb = canClimb;
	}
	
	
	public int getRobotStoppedWorking() {
		return robotStoppedWorking;
	}

	/**
	 * 
	 * @param robotStoppedWorking true if the robot stopped working during the match
	 */
	public void setRobotStoppedWorking(boolean robotStoppedWorking) {
		this.robotStoppedWorking=0;
		if (robotStoppedWorking)
		{
			this.robotStoppedWorking = 1;
			if (this.comments<2)
				this.comments = 2;
		}
	}
	/**
	 * 
	 * @param robotStoppedWorking 1 if the robot stopped working during the match
	 */
	public void setRobotStoppedWorking(int robotStoppedWorking) {
		robotStoppedWorking = rangeCheck(0,1,robotStoppedWorking);
		this.robotStoppedWorking = robotStoppedWorking;
		if (robotStoppedWorking==1)
		{
			if (this.comments<2)
				this.comments = 2;
		}
	}
	public int getDefensiveRobot() {
		return defensiveRobot;
	}

	/**
	 * 
	 * @param defensiveRobot true if the robot was a defensive robot
	 */
	public void setDefensiveRobot(boolean defensiveRobot) {
		this.defensiveRobot =0;
		if (defensiveRobot)
		{
			this.defensiveRobot = 1;
			if (this.comments<1)
				this.comments = 1;
		}
	}
	/**
	 * 
	 * @param defensiveRobot 1 if the robot was a defensive robot
	 */
	public void setDefensiveRobot(int defensiveRobot) {
		defensiveRobot = rangeCheck(0,1,defensiveRobot);
		this.defensiveRobot = defensiveRobot;
		if (defensiveRobot==1)
		{
			if (this.comments<1)
				this.comments = 1;
		}
	}
	public int getRobotDidNotStart() {
		return robotDidNotStart;
	}

	/**
	 * 
	 * @param robotDidNotStart true if the robot was unable to start the match
	 */
	public void setRobotDidNotStart(boolean robotDidNotStart) {
		this.robotDidNotStart=0;
		if (robotDidNotStart)
		{
			this.robotDidNotStart = 1;
			if (this.comments<3)
				this.comments=3;
		}
	}
	/**
	 * 
	 * @param robotDidNotStart 1 if the robot was unable to start the match, 0 otherwise
	 */
	public void setRobotDidNotStart(int robotDidNotStart) {
		robotDidNotStart = rangeCheck(0,1,robotDidNotStart);
		this.robotDidNotStart = robotDidNotStart;
		if (robotDidNotStart==1)
		{
			if (this.comments<3)
				this.comments=3;
		}
	}
	public int getMatchNumber() {
		return matchNumber;
	}

	public void setMatchNumber(int matchNumber) {
		if ((matchNumber >127) || (matchNumber<1))
				matchNumber=0;
		this.matchNumber = matchNumber;
	}

	public int getBaseLine() {
		return baseLine;
	}

	/**
	 * 
	 * @param baseLine 0 if the robot did not cross the baseline during autonomous mode, 1 if it did
	 */
	public void setBaseLine(int baseLine) {
		rangeCheck(0,1,baseLine);
		this.baseLine = baseLine;
	}

	public int getAutoGearsSuccess() {
		return autoGearsSuccess;
	}

	/**
	 * 
	 * @param autoGearsSuccess count of how many gears the robot successfully placed during autonmous mode range 0-3
	 */
	public void setAutoGearsSuccess(int autoGearsSuccess) {
		rangeCheck(0,3,autoGearsSuccess);
		this.autoGearsSuccess = autoGearsSuccess;
	}

	public int getTeamNumber() {
		return teamNumber;
	}

	public void setTeamNumber(int teamNumber) {
		rangeCheck(0,9999,teamNumber);
		this.teamNumber = teamNumber;
	}

	public int getAutoGearsFail() {
		return autoGearsFail;
	}

	/**
	 * 
	 * @param autoGearsFail count of how many gears the robot failed to place during the autonomous mode range 0-1
	 */
	public void setAutoGearsFail(int autoGearsFail) {
		rangeCheck(0,1,autoGearsFail);
		this.autoGearsFail = autoGearsFail;
	}

	public int getTeleOpFuelGround() {
		return teleOpFuelGround;
	}

	/**
	 * 
	 * @param teleOpFuelGround 0 the robot cant pickup fuel from the ground, 1 it can
	 */
	public void setTeleOpFuelGround(int teleOpFuelGround) {
		rangeCheck(0,1,teleOpFuelGround);
		this.teleOpFuelGround = teleOpFuelGround;
	}

	public int getTeleOpGearGround() {
		return teleOpGearGround;
	}

	/**
	 * 
	 * @param teleOpGearGround 0 the robot cannot pickup a gear from the ground, 1 it can
	 */
	
	public void setTeleOpGearGround(int teleOpGearGround) {
		teleOpGearGround = rangeCheck(0,1,teleOpGearGround);
		this.teleOpGearGround = teleOpGearGround;
	}

	public int getTeleOpGearShoot() {
		return teleOpGearShoot;
	}

	/**
	 * 
	 * @param teleOpGearShoot 0 - the robot cant load a gear from the wall shoot, 1 it can
	 */
	public void setTeleOpGearShoot(int teleOpGearShoot) {
		rangeCheck(0,1,teleOpGearShoot);
		this.teleOpGearShoot = teleOpGearShoot;
	}

	public int getTeleOpFuelHopper() {
		return teleOpFuelHopper;
	}

	/**
	 * 
	 * @param teleOpFuelHopper 0 - Robot cant pickup fuel from hopper, 1 it can
	 */
	public void setTeleOpFuelHopper(int teleOpFuelHopper) {
		teleOpFuelHopper = rangeCheck(0,1,teleOpFuelHopper);
		this.teleOpFuelHopper = teleOpFuelHopper;
	}

	public int getTeleOpHighGoalAccuracy() {
		return teleOpHighGoalAccuracy;
	}

	/**
	 * 
	 * @param teleOpHighGoalAccuracy 0-Did Not Shoot, 1 Low Accuracy, 2 Medium Accuracy, 3 HighAccuracy
	 */
	public void setTeleOpHighGoalAccuracy(int teleOpHighGoalAccuracy) {
		teleOpHighGoalAccuracy = rangeCheck(0,3,teleOpHighGoalAccuracy);
		this.teleOpHighGoalAccuracy = teleOpHighGoalAccuracy;
	}

	public int getTeleOpLowGoalAccuracy() {
		return teleOpLowGoalAccuracy;
	}

	/**
	 * 
	 * @param teleOpLowGoalAccuracy 0-Did Not Shoot, 1 Low Accuracy, 2 Medium Accuracy, 3 HighAccuracy
	 */
	public void setTeleOpLowGoalAccuracy(int teleOpLowGoalAccuracy) {
		teleOpLowGoalAccuracy = rangeCheck(0,3,teleOpLowGoalAccuracy);
		this.teleOpLowGoalAccuracy = teleOpLowGoalAccuracy;
	}

	public int getTeleOpGearsCnt() {
		return teleOpGearsCnt;
	}

	/**
	 * 
	 * @param teleOpGearsCnt How many gears the robot placed successfully on the tower during teleop mode. range 0-15
	 */
	public void setTeleOpGearsCnt(int teleOpGearsCnt) {
		teleOpGearsCnt= rangeCheck(0,15, teleOpGearsCnt);
		this.teleOpGearsCnt = teleOpGearsCnt;
	}

	public int getAutoHopper() {
		return autoHopper;
	}

	/**
	 * 
	 * @param autoHopper 0 false - robot cannot pickup from hopper during autonomous mode, 1 it can pickup from hopper
	 */
	public void setAutoHopper(int autoHopper) {
		autoHopper= rangeCheck(0,1,autoHopper);
		this.autoHopper = autoHopper;}

	public int getComments() {
		return comments;
	}

	public int getAutoHighGoalAccuracy() {
		return autoHighGoalAccuracy;
	}

	/**
	 * 
	 * @param autoHighGoalAccuracy 0-Did Not Shoot, 1 Low Accuracy, 2 Medium Accuracy, 3 HighAccuracy
	 */
	public void setAutoHighGoalAccuracy(int autoHighGoalAccuracy) {
		autoHighGoalAccuracy = rangeCheck(0,3, autoHighGoalAccuracy);
		this.autoHighGoalAccuracy = autoHighGoalAccuracy;
	}

	public int getAutoLowGoalAccuracy() {
		return autoLowGoalAccuracy;
	}

	/**
	 * 
	 * @param autoLowGoalAccuracy range 0-3
	 */
	public void setAutoLowGoalAccuracy(int autoLowGoalAccuracy) {
		autoLowGoalAccuracy = rangeCheck(0,3, autoLowGoalAccuracy);
		this.autoLowGoalAccuracy = autoLowGoalAccuracy;
	}

	public int getTeleOpGearFailedCnt() {
		return teleOpGearFailedCnt;
	}

	/**
	 * 
	 * @param teleOpGearFailedCnt  range 0-15
	 */
	public void setTeleOpGearFailedCnt(int teleOpGearFailedCnt) {
		teleOpGearFailedCnt= rangeCheck(0,15,teleOpGearFailedCnt);
		this.teleOpGearFailedCnt = teleOpGearFailedCnt;
	}

	public Match toMatch(TransferCode t){
	Match m = new Match();
    m.setGearFailNbr(t.getAutoGearsFail());
    m.setGearSuccessNbr(t.getAutoGearsSuccess());

    m.setFuelCollectionGround(t.getTeleOpFuelGround()==1);
    m.setFuelCollectionHopper(t.getTeleOpFuelHopper()==1);

    m.setAutonomousBaseline(t.getBaseLine()==1);
    m.setAutonomousHopper(t.getAutoHopper()==1);

    m.setTGearFail(t.getTeleOpGearFailedCnt());
    m.setTGearSuccess(t.getTeleOpGearsCnt());

        m.setTHighCycles(t.getHighGoalCycles());
    m.setTLowCycles(t.getLowGoalCycles());

         m.setHighGoalScore(t.getAutoHighGoalAccuracy());
        m.setLowGoalScore(t.getAutoLowGoalAccuracy());
       m.setTHighGoalScore(t.getTeleOpHighGoalAccuracy());
        m.setTLowGoalScore(t.getTeleOpLowGoalAccuracy());

        m.setDidClimb(t.getCanClimb()==1);
        m.setClimbSuccessful(t.getClimbStatus()==1);

        m.setDefensive(t.getDefensiveRobot()==1);
        m.setDidFunction(t.getRobotDidNotStart()==1);
        m.setStopFunction(t.getRobotStoppedWorking()==1);

    m.setMatchNumber(t.getMatchNumber());
    m.setTeamNumber(t.getTeamNumber());

    return m;
	}


}
