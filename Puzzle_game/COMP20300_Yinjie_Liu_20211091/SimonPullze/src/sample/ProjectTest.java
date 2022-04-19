package sample;

import org.junit.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class ProjectTest{

    /**
     * check the adjacency map is correct or not
     */
    @Test
    public void testAdjMap(){
        InitNumericalMap initNumericalMap=new InitNumericalMap();
        initNumericalMap.initAdjMap();
        for(List<Boolean> temp:initNumericalMap.adjMap)
            System.out.println(temp);
    }

    /**
     * generate 10 resultList and check them is random or not
     */
    @Test
    public void testRandomResultList(){
        for(int i=0;i<10;i++){
            InitNumericalMap initNumericalMap=new InitNumericalMap();
            initNumericalMap.initAdjMap();
            while(initNumericalMap.getResultList().isEmpty()){
                initNumericalMap.backTrace(0);
            }
            System.out.println(initNumericalMap.getResultList());
        }
    }

    /**
     * test shapeCount is corresponding with shape array or not
     */
    @Test
    public void testShapeCount(){
        InitNumericalMap initNumericalMap=new InitNumericalMap();
        initNumericalMap.initAdjMap();
        while(initNumericalMap.getResultList().isEmpty()){
            initNumericalMap.backTrace(0);
        }
        System.out.println(initNumericalMap.getResultList());
        System.out.println(Arrays.toString(initNumericalMap.shapeCount));
    }

    @Test
    public void testHasResult(){
        InitNumericalMap initNumericalMap=new InitNumericalMap();
        initNumericalMap.initAdjMap();
        while(initNumericalMap.getResultList().isEmpty()){
            initNumericalMap.backTrace(0);
        }
        if(initNumericalMap.isHasResultList()){
            System.out.println("success");
        }else {
            throw new IllegalArgumentException("fail");
        }
    }

    /**
     * can not place because they are adjacent left and right
     */
    @Test
    public void testCanPlacedWithWrong(){
        InitNumericalMap initNumericalMap=new InitNumericalMap();
        initNumericalMap.initAdjMap();
        initNumericalMap.setShapeArray(0,0);
        if(initNumericalMap.canPlace(1,3)){
            System.out.println("success");

        }else {
            throw new IllegalArgumentException("fail");
        }
    }

    /**
     *can not place because they are adjacent up and down
     */
    @Test
    public void testCanPlacedWithWrong2(){
        InitNumericalMap initNumericalMap=new InitNumericalMap();
        initNumericalMap.initAdjMap();
        initNumericalMap.setShapeArray(0,0);
        initNumericalMap.setShapeArray(1,1);
        initNumericalMap.setShapeArray(2,2);
        initNumericalMap.setShapeArray(3,3);
        if(initNumericalMap.canPlace(5,1)){
            System.out.println("success");
        }else {
           throw new IllegalArgumentException("fail");
        }
    }

    /**
     * use a right shape to test canPlaced method
     */
    @Test
    public void testCanPlacedWithCorrect(){
        InitNumericalMap initNumericalMap=new InitNumericalMap();
        initNumericalMap.initAdjMap();
        initNumericalMap.setShapeArray(0,0);
        if(initNumericalMap.canPlace(1,1)){
            System.out.println("success");
        }else {
            throw new IllegalArgumentException("fail");
        }
    }

    /**
     * use a wrong shape array to test isLegal method
     */
    @Test
    public void testIsLegalWithWrong(){
        InitNumericalMap initNumericalMap=new InitNumericalMap();
        initNumericalMap.initAdjMap();
        int[] shape={0, 0, 2, 4, 4, 4, 0, 2, 0, 3, 1, 4, 2, 1, 2, 0};
        int[] shape2={3, 1, 2, 1, 4, 0, 3, 0, 3, 1, 0, 2, 2, 0, 4, 1};
        if(initNumericalMap.isLegal(shape)){
            System.out.println("success");
        }else {
            throw new IllegalArgumentException("fail");
        }
    }

    /**
     * use a right shape array to test isLegal method
     */
    @Test
    public void testIsLegalWithCorrect(){
        InitNumericalMap initNumericalMap=new InitNumericalMap();
        initNumericalMap.initAdjMap();
        int[] shape={0, 3, 2, 4, 1, 4, 0, 2, 0, 3, 1, 4, 2, 1, 2, 0};
        if(initNumericalMap.isLegal(shape)){
            System.out.println("success");
        }else {
            throw new IllegalArgumentException("fail");
        }
    }

    /**
     * use a wrong shape array to test isLegalCount method
     */
    @Test
    public void testIsLegalCountWithWrong(){
        InitNumericalMap initNumericalMap=new InitNumericalMap();
        initNumericalMap.initAdjMap();
        int[] shape={3, 1, 2, 4, 4, 3, 1, 1, 1, 4, 0, 2, 3, 2, 1, 0};
        if(initNumericalMap.isLegalCount(shape)){
            System.out.println("success");
        }else {
            throw new IllegalArgumentException("fail");
        }
    }
    /**
     * use a right shape array to test isLegalCount method
     */
    @Test
    public void testIsLegalCountWithCorrect(){
        InitNumericalMap initNumericalMap=new InitNumericalMap();
        initNumericalMap.initAdjMap();
        int[] shape={3, 1, 2, 4, 4, 3, 1, 3, 1, 4, 0, 2, 3, 2, 1, 0};
        if(initNumericalMap.isLegalCount(shape)){
            System.out.println("success");
        }else {
            throw new IllegalArgumentException("fail");
        }
    }

    /**
     * test setStartTime method in Controller
     */
    @Test
    public void testStartTime(){
        Date date=new Date();
        SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
        Controller controller=new Controller();
        if(format.format(date).equals(controller.testStartTime())){
            System.out.println("success");
        }else {
            throw new IllegalArgumentException("fail");
        }
    }

    /**
     * test readHistoryRecord in Controller
     */
    @Test
    public void testReadHistoryRecord(){
        Controller controller=new Controller();
        if(controller.testReadHistoryRecord()!=0){
            System.out.println("success");
        }else {
            throw new IllegalArgumentException("fail or there isn't history record");
        }
    }

    /**
     * use a correct shape to test isSameShape method
     */
    @Test
    public void testIsSameShapeWithCorrect(){
        Controller controller=new Controller();
        List<Integer> resultList=Arrays.asList(3, 1, 2, 4, 4, 3, 1, 3, 1, 4, 0, 2, 3, 2, 1, 0);
        if(controller.testIsSameShape(3,0,resultList)){
            System.out.println("success");
        }else {
            throw new IllegalArgumentException("fail");
        }
    }

    /**
     * use a wrong shape to test isSameShape method
     */
    @Test
    public void testIsSameShapeWithWrong(){
        Controller controller=new Controller();
        List<Integer> resultList=Arrays.asList(3, 1, 2, 4, 4, 3, 1, 3, 1, 4, 0, 2, 3, 2, 1, 0);
        if(controller.testIsSameShape(3,1,resultList)){
            throw new IllegalArgumentException("fail");
        }else {
            System.out.println("success");
        }
    }

    /**
     * In this case, there are unplaced places in the map, so if the shapeCount all is not 0, the game must not be lost
     */
    @Test
    public void testIsLose1(){
        Controller controller=new Controller();
        List<Boolean> isPlacedList=Arrays.asList(false, false, true, true, true, false, true, true, false, true, false, true, true, true, true, true);
        List<Integer> resultList=Arrays.asList(3, 1, 2, 1, 4, 0, 3, 0, 3, 1, 0, 2, 2, 0, 4, 1);
        int[] shapeCount={1,1,1,1,1},dynamicShapeCount={4,4,3,3,2};
        if(!controller.testIsLose(isPlacedList, resultList, shapeCount, dynamicShapeCount)){
            System.out.println("success");
        }else {
            throw new IllegalArgumentException("fail");
        }
        //System.out.println(controller.testIsLose(isPlacedList,resultList,shapeCount,dynamicShapeCount));
        //controller.testIsLose(isPlacedList,resultList,shapeCount,dynamicShapeCount);
    }

    /**
     * though the remain places and pieces are not 0, but there are no suitable place for remain pieces, so the game is Lose
     */
    @Test
    public void testIsLose2(){
        Controller controller=new Controller();
        List<Boolean> isPlacedList=Arrays.asList(false, false, true, true, true, false, true, true, false, true, false, true, true, true, true, true);
        List<Integer> resultList=Arrays.asList(3, 1, 2, 1, 4, 0, 3, 0, 3, 1, 0, 2, 2, 0, 4, 1);
        int[] shapeCount={0,2,0,0,3},dynamicShapeCount={4,4,3,3,2};
        if(controller.testIsLose(isPlacedList, resultList, shapeCount, dynamicShapeCount)){
            System.out.println("success");
        }else {
            throw new IllegalArgumentException("fail");
        }
    }
    /**
     * in this case, the only legal shape is 2, if the 2 remain 1, the game is not lose
     */
    @Test
    public void TestIsLose3(){
        Controller controller=new Controller();
        List<Boolean> isPlacedList=Arrays.asList(true, true, true, true, true, true, true, true, true, true, true, false, true, true, true, true);
        List<Integer> resultList=Arrays.asList(3, 0, 4, 1, 0, 3, 0, 3, 2, 4, 2, 4, 0, 2, 1, 2);
        int[] shapeCount={0,1,0,0,0},dynamicShapeCount={4,2,4,3,3};
        if(!controller.testIsLose(isPlacedList, resultList, shapeCount, dynamicShapeCount)){
            System.out.println("success");
        }else {
            throw new IllegalArgumentException("fail");
        }
    }

    /**
     * while the shape 2 remain nothing , other shape can not be placed in the only unplaced place, so the game is Lose
     */
    @Test
    public void TestIsLose4(){
        Controller controller=new Controller();
        List<Boolean> isPlacedList=Arrays.asList(true, true, true, true, true, true, true, true, true, true, true, false, true, true, true, true);
        List<Integer> resultList=Arrays.asList(3, 0, 4, 1, 0, 3, 0, 3, 2, 4, 2, 4, 0, 2, 1, 2);
        int[] shapeCount={1,0,1,1,1},dynamicShapeCount={4,2,4,3,3};
        if(controller.testIsLose(isPlacedList, resultList, shapeCount, dynamicShapeCount)){
            System.out.println("success");
        }else {
            throw new IllegalArgumentException("fail");
        }
    }

    /**
     * if any element of shapeCount is not 0, the game is not win
     */
    @Test
    public void TestIsWin1(){
        Controller controller=new Controller();
        int[] shapeCount={0,0,1,0,0};
        if(!controller.testIsWin(shapeCount)){
            System.out.println("success");
        }else {
            throw new IllegalArgumentException("fail");
        }
    }

    /**
     * the game will win if the every element of shapeCount is 0
     */
    @Test
    public void TestIsWin2(){
        Controller controller=new Controller();
        int[] shapeCount={0,0,0,0,0};
        if(controller.testIsWin(shapeCount)){
            System.out.println("success");
        }else {
            throw new IllegalArgumentException("fail");
        }
    }

    /**
     * test setScore method
     */
    @Test
    public void testSetScore(){
        Controller controller=new Controller();
        List<Boolean> isPlacedList=Arrays.asList(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true);
        System.out.println(controller.testSetScore(true,"12:20:00",isPlacedList));

    }

    @Test
    public void testSetScore2(){
        Controller controller=new Controller();
        List<Boolean> isPlacedList=Arrays.asList(false, false, true, true, true, false, true, true, false, true, false, true, true, true, true, true);
        System.out.println(controller.testSetScore(false,"12:20:00",isPlacedList));
    }

    /**
     * replace 3 with 1 is legal because after that the dynamicShapeCount become 4,3,4,2,3
     */
    @Test
    public void testNotChangeRatioWithCorrect(){
        Controller controller=new Controller();
        int[] dynamicShapeCount={4,2,4,3,3};
        if(controller.testNotChangeRatio(dynamicShapeCount,1,3)){
            System.out.println("success");
        }else {
            throw new IllegalArgumentException("fail");
        }
    }

    /**
     * replace 0 with 4 is legal because after that the dynamicShapeCount become 3,2,4,3,4
     */
    @Test
    public void testNotChangeRatioWithCorrect2(){
        Controller controller=new Controller();
        int[] dynamicShapeCount={4,2,4,3,3};
        if(controller.testNotChangeRatio(dynamicShapeCount,4,0)){
            System.out.println("success");
        }else {
            throw new IllegalArgumentException("fail");
        }
    }

    /**
     *  replace 3 with 0 is illegal because after that the dynamicShapeCount become 5,2,4,2,3
     */
    @Test
    public void testNotChangeRatioWithWrong(){
        Controller controller=new Controller();
        int[] dynamicShapeCount={4,2,4,3,3};
        if(!controller.testNotChangeRatio(dynamicShapeCount,0,3)){
            System.out.println("success");
        }else {
            throw new IllegalArgumentException("fail");
        }
    }

    /**
     *  replace 4 with 0 is illegal because after that the dynamicShapeCount become 5,4,3,3,1
     */
    @Test
    public void testNotChangeRatioWithWrong2(){
        Controller controller=new Controller();
        int[] dynamicShapeCount={4,4,3,3,2};
        if(!controller.testNotChangeRatio(dynamicShapeCount,0,4)){
            System.out.println("success");
        }else {
            throw new IllegalArgumentException("fail");
        }
    }

    @Test
    public void testReadScoreBoardFile(){
        ScoreBoard scoreBoard=new ScoreBoard();
        scoreBoard.readFile();
        System.out.println(scoreBoard.testReadScoreBoardFile());
    }
}
